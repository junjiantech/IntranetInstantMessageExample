package top.j3dream.example.okhttp.feature

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import top.j3dream.example.okhttp.databinding.ActivityMainBinding
import top.j3dream.example.okhttp.websocket.WebSocketManager

/**
 * - 文件描述: 应用入口活动
 * @author Jia Junjian
 * @since 2021/8/31 09:37
 */
class MainActivity : AppCompatActivity() {

    private lateinit var mViewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 设置 MainActivity View.
        mViewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mViewBinding.root)
        // 初始化WebSocket管理器
        WebSocketManager.get().init("ws://192.168.0.10:8088")
        // 设置WebSocket连接
        mViewBinding.btnMainConnectWebsocket.setOnClickListener {
            WebSocketManager.get().connect()
        }

        mViewBinding.btnMainDisconnectWebsocket.setOnClickListener {
            WebSocketManager.get().disconnect()
        }
    }
}