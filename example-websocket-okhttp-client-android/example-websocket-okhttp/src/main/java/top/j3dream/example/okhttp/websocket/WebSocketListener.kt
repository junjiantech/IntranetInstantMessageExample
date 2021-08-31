package top.j3dream.example.okhttp.websocket

import android.util.Log
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

/**
 * - 文件描述: WebSocket 连接监听
 * @author Jia Junjian
 * @since 2021/8/31 10:16
 */
class WebSocketListener : WebSocketListener() {

    companion object {

        private const val TAG = "WebSocketListener"
    }

    // 是否正在连接中
    private var isConnecting: Boolean = false

    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        // 是否已经连接成功
        isConnecting = response.code == 200
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
    }

    /**
     * 是否正在连接中...
     * @return true if connecting else not connecting
     */
    fun isConnecting(): Boolean = isConnecting
}