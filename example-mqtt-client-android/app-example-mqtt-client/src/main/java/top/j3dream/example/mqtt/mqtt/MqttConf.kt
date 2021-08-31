package top.j3dream.example.mqtt.mqtt

/**
 * - 文件描述:
 * @author Jia Junjian
 * @since 2021/8/30 11:40
 */
data class MqttConf(
    /**
     * server Url (Broker地址)
     */
    val serverUrl: String,
    /**
     * 客户端编号
     */
    val clientId: String,
    /**
     * 话题列表
     */
    val topics: MutableList<MqttTopic>,
    /**
     * 重新连接时是否记住状态. 请设置为 false 不然当设备在离线状态时 topic 上发布的消息， 客户端将无法接收到
     */
    val isCleanSession: Boolean = false,
    /**
     * 当网络环境不良时是否自动重新连接
     */
    val isAutomaticReconnect: Boolean = true,
    /**
     * 链接超时时间
     */
    val connectionTimeout: Int = 10,
    /**
     * 两次心跳之间的间隔
     */
    val keepAliveInterval: Int = 20
)