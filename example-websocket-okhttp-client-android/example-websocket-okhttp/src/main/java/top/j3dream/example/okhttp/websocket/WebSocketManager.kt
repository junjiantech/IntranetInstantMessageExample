package top.j3dream.example.okhttp.websocket

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import java.util.concurrent.TimeUnit

/**
 * - 文件描述: WebSocket 连接管理
 * @author Jia Junjian
 * @since 2021/8/31 09:52
 */
class WebSocketManager private constructor() {

    companion object {

        // WebSocket管理器
        private val instance: WebSocketManager by lazy { WebSocketManager() }
        fun get(): WebSocketManager = instance

        // 最大重试重连次数
        private const val CONF_RETRY_RECONNECT_MAX_NUM = 5

        // 重试重连的间隔时间
        private const val CONF_RECONNECT_INTERVAL = 8 * 1000

        // 心跳间隔时间
        private const val CONF_CONNECT_HEART_INTERVAL = 3 * 1000
    }

    // OkHttpClient
    private val mOkHttpClient: OkHttpClient by lazy {
        createWebSocketOkHttpClient()
    }

    // WebSocketListener
    private var mWebSocketListener: WebSocketListener? = null

    // WebSocket Request
    private var mWebSocketRequest: Request? = null

    // WebSocket 服务地址
    private var mWebSocketServiceUrl: String? = null

    // WebSocket 客户端
    private var mWebSocketClient: WebSocket? = null

    /**
     * WebSocket 连接的服务地址
     */
    fun init(serviceUrl: String) {
        this.mWebSocketServiceUrl = serviceUrl

        mWebSocketRequest = Request.Builder()
            .url(serviceUrl)
            .build()
    }

    /**
     * 发起连接.
     */
    fun connect() {
        if (mWebSocketRequest == null) {
            throw RuntimeException("please first init WebSocketManager.")
        }
        // WebSocket
        mWebSocketClient =
            mOkHttpClient.newWebSocket(mWebSocketRequest!!, WebSocketListener().also {
                mWebSocketListener = it
            })
    }

    /**
     * 关闭连接
     */
    fun disconnect() {
        // 关闭连接.
        mWebSocketClient?.let { webSocket ->
            webSocket.cancel()
            webSocket.close(1001, "client close connect!")
        }
        // 将 webSocketClient 滞空
        mWebSocketClient = null
        // 将 WebSocketListener 滞空
        mWebSocketListener = null
    }

    /**
     * 发送消息
     * @param message Message
     * @return if true { send success } else { send failure }
     */
    fun sendMessage(message: String): Boolean {
        if (!isConnecting()) return false
        return mWebSocketClient?.send(message) ?: false
    }

    /**
     * 是否正在连接中
     */
    private fun isConnecting() =
        mWebSocketClient != null && mWebSocketListener != null && mWebSocketListener!!.isConnecting()

    /**
     * create Web Socket connect okhttp client
     * @return OkHttpClient
     */
    private fun createWebSocketOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .callTimeout(90, TimeUnit.SECONDS)
            .build()
    }
}