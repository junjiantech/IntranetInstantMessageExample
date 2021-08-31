package top.j3dream.example.mqtt.feature

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber
import top.j3dream.example.mqtt.R
import top.j3dream.example.mqtt.mqtt.MqttClient
import top.j3dream.example.mqtt.mqtt.MqttConf
import top.j3dream.example.mqtt.mqtt.MqttTopic

/**
 * - 文件描述: 应用程序活动入口
 * @author Jia Junjian
 * @since 2021/8/30 17:27
 */
class MainActivity : AppCompatActivity() {

    companion object {

        private const val CONF_TEST_EMQX_SERVER_URL = "tcp://192.168.0.10:1883"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 初始化框架本身
        initMqttClientFramework()
        // 发起 Mqtt 连接
        MqttClient.get().connect()
    }

    private fun initMqttClientFramework() {
        // 获取 AndroidId
        Timber.d("[Android ID]: %s", getApplicationAndroidId())
        // mqtt config
        val mqttConfig = MqttConf(
            CONF_TEST_EMQX_SERVER_URL, getApplicationAndroidId(),
            // 订阅的主题.
            arrayListOf(MqttTopic("testtopic/#", 2))
        )

        // 初始化框架
        MqttClient.get().init(this, mqttConfig).also {
            lifecycle.addObserver(it)
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