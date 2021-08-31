package top.j3dream.example.mqtt

import android.app.Application
import timber.log.Timber

/**
 * - 文件描述: 自定义的 Application
 * @author Jia Junjian
 * @since 2021/8/30 11:38
 */
class CustomApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}