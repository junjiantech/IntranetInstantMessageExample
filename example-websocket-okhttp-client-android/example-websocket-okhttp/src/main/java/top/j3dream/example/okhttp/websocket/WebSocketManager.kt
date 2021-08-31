package top.j3dream.example.okhttp.websocket

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import java.net.SocketException
import java.util.concurrent.TimeUnit

/**
 * - 文件描述: WebSocket 连接管理
 * @author Jia Junjian
 * @since 2021/8/31 09:52
 */
class WebSocketManager private constructor() {

    companion object {

        private const val TAG = "WebSocketManager"

        // WebSocket管理器
        private val instance: WebSocketManager by lazy { WebSocketManager() }
        fun get(): WebSocketManager = instance

        // 最大重试重连次数
        private const val CONF_RETRY_RECONNECT_MAX_NUM = 5

        // 重试重连的间隔时间
        private const val CONF_RECONNECT_INTERVAL = 8 * 1000L

        // 心跳间隔时间
        private const val CONF_CONNECT_HEART_INTERVAL = 3 * 1000
    }

    // OkHttpClient
    private val mOkHttpClient: OkHttpClient by lazy {
        createWebSocketOkHttpClient()
    }

    /**
     * 连接重试的计数器
     */
    private var mReconnectRetryCount = 0

    // WebSocketListener
    private var mWebSocketListener: CustomWebSocketListener? = null

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
        // 如果已经连接则直接返回
        if (isConnecting()) {
            return
        }
        // WebSocket
        mWebSocketClient = mOkHttpClient.newWebSocket(
            mWebSocketRequest!!,
            mWebSocketListener ?: CustomWebSocketListener().also { mWebSocketListener = it }
        )
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
     * 重新连接
     */
    @Synchronized
    private fun reconnect() {
        if (mReconnectRetryCount >= CONF_RETRY_RECONNECT_MAX_NUM) {
            Log.e(TAG, "网络重新连接失败....")
            // 如果重连任务失败后则将重试次数滞空
            mReconnectRetryCount = 0
            return
        }
        // 进行一定延迟后再进行重新尝试连接
        Thread.sleep(CONF_RECONNECT_INTERVAL)
        // 开始重新尝试连接
        connect()
        // 连接计数器
        mReconnectRetryCount++
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
    private fun isConnecting(): Boolean {
        return mWebSocketClient != null && mWebSocketListener != null && mWebSocketListener!!.isConnecting()
    }

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

    /**
     * 自定义的 WebSocketListener
     */
    inner class CustomWebSocketListener : okhttp3.WebSocketListener() {

        // 是否正在连接中
        private var isConnecting: Boolean = false

        override fun onOpen(webSocket: WebSocket, response: Response) {
            super.onOpen(webSocket, response)

            // 是否已经连接成功. 如果接收到请求将进行协议切换认定该请求建立成功
            isConnecting = response.isSuccessful || response.code == 101
            Log.e(TAG, "已经成功连接WebSocket服务....")
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)
            // 接收到WebSocket信息
            Log.d(TAG, String.format("接收到WebSocket消息: [%s]", text))
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosing(webSocket, code, reason)
            isConnecting = false
            Log.e(TAG, "正在关闭WebSocket服务....")
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosed(webSocket, code, reason)
            isConnecting = false
            Log.e(TAG, "已经关闭WebSocket服务....")
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            super.onFailure(webSocket, t, response)
            Log.e(TAG, "WebSocket 出现异常.", t)

            isConnecting = false

            // 如果 t 是 socketException 并且 message 并非是正常结束的情况则进行重新连接操作
            if (t is SocketException && "Socket closed" != t.message) {
                // 开始重新连接
                reconnect()
            }
        }

        /**
         * 是否正在连接中...
         * @return true if connecting else not connecting
         */
        fun isConnecting(): Boolean = isConnecting
    }
}