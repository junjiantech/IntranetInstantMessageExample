package top.j3dream.example.mqtt.mqtt

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import info.mqtt.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

/**
 * - 文件描述: Mqtt客户端
 * @author Jia Junjian
 * @since 2021/8/30 11:37
 */
class MqttClient private constructor() : MqttCallbackExtended, IMqttActionListener,
    IMqttMessageListener {

    /**
     * Mqtt Android 客户端.
     */
    private var mMqttAndroidClient: MqttAndroidClient? = null

    /**
     * Mqtt 连接选项
     */
    private var mMqttClientOption: MqttConnectOptions = MqttConnectOptions()

    /**
     * Mqtt 订阅的主题.
     */
    private val mMqttSubscribeTopics: MutableList<MqttTopic> = ArrayList()

    companion object {

        // 单例
        private val instance: MqttClient by lazy { MqttClient() }

        /**
         * 获取 MqttClient 单例子
         */
        fun get(): MqttClient = instance

        /**
         * 获取默认的AndroidMqtt通知, 提升MqttAndroidClient的优先级
         * @param context context
         * @return notification
         */
        private fun getDefaultMqttClientNotification(context: Context): Notification {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channelId = MqttClient.javaClass.simpleName
                Notification.Builder(
                    context,
                    createNotificationChannel(context, channelId, channelId, 0)
                )
            } else {
                Notification.Builder(context)
            }.build()
        }

        /**
         * 创建系统通知渠道
         * @param context context
         * @param channelID channelID
         * @param channelNAME channelNAME
         * @param level 等级
         * @return channelID
         */
        private fun createNotificationChannel(
            context: Context,
            channelID: String,
            channelNAME: String,
            level: Int
        ): String? {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val manager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                val channel = NotificationChannel(channelID, channelNAME, level)
                manager.createNotificationChannel(channel)
                channelID
            } else {
                null
            }
        }
    }

    /**
     * 初始化 Mqtt 客户端
     * @param context context
     * @param mqttConfig mqttConfig
     */
    fun init(
        context: Context,
        mqttConfig: MqttConf,
        notification: Notification = getDefaultMqttClientNotification(context)
    ) {

        mMqttAndroidClient =
            MqttAndroidClient(context, mqttConfig.serverUrl, mqttConfig.clientId).apply {

                // 设置 MqttAndroidClient 的相关回调.
                addCallback(this@MqttClient)

                // 设置Mqtt系统通知栏.
                setForegroundService(notification, MqttClient.hashCode())

                mMqttClientOption.apply {
                    // 是否自动清除 session
                    isCleanSession = mqttConfig.isCleanSession
                    // 是否自动重新连接
                    isAutomaticReconnect = mqttConfig.isAutomaticReconnect
                    // 连接超时
                    connectionTimeout = mqttConfig.connectionTimeout
                    // 保持连接的Ping-pong 间隔
                    keepAliveInterval = mqttConfig.keepAliveInterval
                }
            }

        // 将 options 中的 topic 添加到项目配置中.
        mMqttSubscribeTopics.addAll(mqttConfig.topics)
    }

    /**
     * 开始进行连接
     */
    fun connect() {
        // 获取 mqttClient
        val mqttClient = fetchCheckMqttAndroidClient()
        // 开始进行连接
        mqttClient.connect(mMqttClientOption, null, this)
    }

    /**
     * 关闭连接
     */
    fun disconnect() {
        // mqtt client
        val mqttClient = fetchCheckMqttAndroidClient()
        // 解除连接
        mqttClient.disconnect(null, this)
    }

    /**
     * 订阅默认的 topic.
     * 默认的消息接收器是由 [MqttCallbackExtended] 的 messageArrived 函数完成的.
     */
    private fun subscribeDefaultTopic() {
        // 订阅全部的topic
        val topics = mMqttSubscribeTopics.map { it.topic }.toTypedArray()
        val topicQos = mMqttSubscribeTopics.map { it.qos }.toIntArray()
        val listeners = mMqttSubscribeTopics.map { this as IMqttMessageListener }.toTypedArray()
        // 获取 Mqtt 客户端
        val mqttClient = fetchCheckMqttAndroidClient()
        // 订阅 topic
        mMqttSubscribeTopics.forEach {
            mqttClient.subscribe(it.topic, it.qos, null, null, this)
        }
        mqttClient.subscribe(topics, topicQos, null, null, listeners)
    }

    /**
     * 发起订阅.
     * @param topic topic
     * @param msgListener message listener
     */
    fun subscribe(topic: MqttTopic, msgListener: IMqttMessageListener) {
        // mqtt client
        val mqttClient = fetchCheckMqttAndroidClient()
        // 订阅话题
        mqttClient.subscribe(topic.topic, topic.qos, msgListener)
    }

    /**
     * 获取检查MqttAndroidClient. 如果获取失败则抛出异常
     */
    private fun fetchCheckMqttAndroidClient(): MqttAndroidClient {
        return mMqttAndroidClient ?: throw RuntimeException("please first init MqttClient!")
    }

    /**
     * 当 Mqtt 连接超时时触发
     * @param cause 消息连接超时时触发
     */
    override fun connectionLost(exception: Throwable?) {
        Timber.e(exception, "MQTT 连接超时....")
    }

    /**
     * 消息到达时触发。 消息最终在 CommonCallback 中的 deliverMessage 方法中分发.
     * MqttTopic.isMatched(topicFilter, topicName) 中的行为可以看到 topic 用于不为空. 则这里不能滥用空判断
     * @param topic 话题
     * @param message mqtt message
     */
    override fun messageArrived(topic: String, message: MqttMessage?) {
        Timber.d("来自(%s)的消息: %s", topic, message?.payload?.toString(Charsets.UTF_8))
    }

    /**
     * Qos 机制中回执发送完成后触发
     * @param token Qos 消息凭据
     */
    override fun deliveryComplete(token: IMqttDeliveryToken?) {

    }

    /**
     * 连接完成时触发
     * @param reconnect 是否时重新连接的
     * @param serverURI 服务器地址.
     */
    override fun connectComplete(reconnect: Boolean, serverURI: String?) {
        /// 当连接是否是首次连接的时候, 应该订阅一次话题.
        if (!reconnect) {
            subscribeDefaultTopic()
        } else {
            Timber.d("MQTT 重连完成....")
        }
    }

    /**
     * Mqtt 执行连接/解除连接成功的回调
     * @param asyncActionToken 会话令牌
     */
    override fun onSuccess(asyncActionToken: IMqttToken?) {
        // 连接成功
        Timber.d("MQTT 连接成功.... %s", Objects.toString(asyncActionToken?.messageId))
    }

    /**
     * Mqtt 执行连接/解除连接失败的回调
     * @param asyncActionToken 会话令牌
     * @param exception 连接中出现的异常
     */
    override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
        // 连接失败
        Timber.e(exception, "MQTT 连接失败.... %s", Objects.toString(asyncActionToken?.messageId))
    }
}