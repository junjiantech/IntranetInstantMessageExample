package top.j3dream.example.mqtt.mqtt

import androidx.annotation.IntRange

/**
 * - 文件描述: mqtt 订阅的事件
 * @author Jia Junjian
 * @since 2021/8/30 14:01
 */
data class MqttTopic(
    /**
     * topic
     */
    val topic: String,
    /**
     * Qos 服务质量.
     * Qos.0 (At most once，至多一次)
     * Qos.1 (At least once，至少一次)
     * Qos.2 (Exactly once，确保只有一次)
     */
    @IntRange(from = 0, to = 2)
    val qos: Int
)