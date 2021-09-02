package top.j3dream.example.mqtt.feature

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber
import top.j3dream.example.mqtt.databinding.ActivityMainBinding
import top.j3dream.example.mqtt.mqtt.MqttClient
import top.j3dream.example.mqtt.mqtt.MqttConf
import top.j3dream.example.mqtt.mqtt.MqttTopic
import java.util.*

/**
 * - 文件描述: 应用程序活动入口
 * @author Jia Junjian
 * @since 2021/8/30 17:27
 */
class MainActivity : AppCompatActivity() {

    /**
     * 默认订阅的Topic
     *
     * Topic 订阅地址中: '#' 表示匹配多层地址, '+' 表示只匹配一层地址
     * 如下使用 'testtopic/#' 则会匹配如何主题
     * - testtopic/A
     * - testtopic/A/B/C
     */
    private val defSubscribeTopic: MutableList<MqttTopic> = arrayListOf(
        MqttTopic("testtopic/#", 2),
        MqttTopic("`$`SYS/brokers/+/clients/+/connected", 2)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // View 绑定.
        val viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        // 获取 AndroidId
        Timber.d("[Android ID]: %s", getApplicationAndroidId())
        // 开始连接
        viewBinding.btnMainMqttConnect.setOnClickListener {
            // broker 地址
            val serverUrl = Objects.toString(viewBinding.etMainMqttServerUrl.text)
            // mqtt config
            val mqttConfig = MqttConf(
                serverUrl, getApplicationAndroidId(), defSubscribeTopic
            )
            // 初始化框架
            MqttClient.get().init(this, mqttConfig)
            // 开始连接
            MqttClient.get().connect()
        }

        // 断开连接
        viewBinding.btnMainMqttDisconnect.setOnClickListener {

            MqttClient.get().disconnect()
        }
    }

    /**
     * 获取应用程序的 Android id
     * @return Android id
     */
    @SuppressLint("HardwareIds")
    private fun getApplicationAndroidId(): String =
        Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
}