package top.j3dream.example.mqtt.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

/**
 * - 文件描述: Android Notification 帮助类
 * @author Jia Junjian
 * @since 2021/8/30 17:30
 */
object Notifications {

    /**
     * 创建系统通知渠道
     * @param context context
     * @param channelID channelID
     * @param channelNAME channelNAME
     * @param level 等级
     * @return channelID
     */
    fun createNotificationChannel(
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