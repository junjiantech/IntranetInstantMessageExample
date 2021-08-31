package top.j3dream.buildsrc

/**
 * - 文件描述: 项目插件管理
 * @author Jia Junjian
 * @since 2021/8/11 13:33
 */
object Plugins {

    object ProjectPlugins {
        // Android gradle plugin
        const val AGP = "com.android.tools.build:gradle:7.0.0"

        // kotlin gradle plugin
        const val Kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.21"
    }

    // module type application
    const val moduleApplication = "com.android.application"

    // module type library
    const val moduleLibrary = "com.android.library"

    // kotlin android plugin
    const val kotlinAndroid = "kotlin-android"

    // kotlin kapt plugin
    const val kotlinKapt = "kotlin-kapt"

    // maven
    const val maven = "maven"
}
