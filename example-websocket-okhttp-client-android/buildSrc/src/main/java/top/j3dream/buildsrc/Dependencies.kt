package top.j3dream.buildsrc

/**
 * - 文件描述: 依赖管理
 * @author Jia Junjian
 * @since 2021/8/11 11:41
 */
object Dependencies {

    object AndroidX {
        const val AppCompat = "androidx.appcompat:appcompat:${DepVersion.AppCompat}"
        const val CoreKtx = "androidx.core:core-ktx:${DepVersion.CoreKtx}"
        const val ConstraintLayout =
            "androidx.constraintlayout:constraintlayout:${DepVersion.ConstraintLayout}"
        const val ActivityKtx = "androidx.activity:activity-ktx:${DepVersion.ActivityKtx}"
        const val FragmentKtx = "androidx.fragment:fragment-ktx:${DepVersion.FragmentKtx}"
        const val MultiDex = "androidx.multidex:multidex:${DepVersion.MultiDex}"
    }

    object Google {
        const val Material = "com.google.android.material:material:${DepVersion.Material}"
        const val AutoService = "com.google.auto.service:auto-service:${DepVersion.AutoService}"
        const val AutoServiceAnnotations =
            "com.google.auto.service:auto-service-annotations:${DepVersion.AutoService}"
    }

    object Jetpack {
        const val ViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${DepVersion.Lifecycle}"
        const val ViewModelSavedState =
            "androidx.lifecycle:lifecycle-viewmodel-savedstate:${DepVersion.Lifecycle}"
        const val LiveData = "androidx.lifecycle:lifecycle-livedata-ktx:${DepVersion.Lifecycle}"
        const val Lifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:${DepVersion.Lifecycle}"
        const val LifecycleCompilerAPT =
            "androidx.lifecycle:lifecycle-compiler:${DepVersion.Lifecycle}"
    }

    object Kotlin {
        const val Kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${DepVersion.Kotlin}"
        const val CoroutinesCore =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${DepVersion.Coroutine}"
        const val CoroutinesAndroid =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${DepVersion.Coroutine}"
    }

    object Testing {
        const val Junit = "junit:junit:${DepVersion.TestJunit}"
        const val TestExtJunit = "androidx.test.ext:junit:${DepVersion.TestExtJunit}"
        const val TestEspresso = "androidx.test.espresso:espresso-core:${DepVersion.TestEspresso}"
    }

    object Network {
        const val OkHttp = "com.squareup.okhttp3:okhttp:${DepVersion.OkHttp}"
        const val OkHttpInterceptorLogging =
            "com.squareup.okhttp3:logging-interceptor:${DepVersion.OkHttpInterceptorLogging}"
        const val Retrofit = "com.squareup.retrofit2:retrofit:${DepVersion.Retrofit}"
        const val RetrofitConverterGson =
            "com.squareup.retrofit2:converter-gson:${DepVersion.RetrofitConverterGson}"
        const val Netty = "io.netty:netty-all:${DepVersion.NET_NETTY}"
    }

    const val Gson = "com.google.code.gson:gson:${DepVersion.Gson}"
    const val MMKV = "com.tencent:mmkv-static:${DepVersion.MMKV}"
    const val AutoSize = "me.jessyan:autosize:${DepVersion.AutoSize}"
    const val Glide = "com.github.bumptech.glide:glide:${DepVersion.Glide}"
    const val GlideCompiler = "com.github.bumptech.glide:compiler:${DepVersion.Glide}"
    const val ARoute = "com.alibaba:arouter-api:${DepVersion.ARoute}"
    const val ARouteCompiler = "com.alibaba:arouter-compiler:${DepVersion.ARouteCompiler}"
    const val EventBus = "org.greenrobot:eventbus:${DepVersion.EventBus}"
    const val EventBusAPT = "org.greenrobot:eventbus-annotation-processor:${DepVersion.EventBus}"

    /**
     * 依赖版本管理
     */
    internal object DepVersion {

        ///region ::AndroidX
        const val AppCompat = "1.2.0"
        const val CoreKtx = "1.6.0"

        //约束布局
        const val ConstraintLayout = "2.0.1"
        const val ActivityKtx = "1.1.0"
        const val FragmentKtx = "1.2.5"
        const val MultiDex = "2.0.1"
        ///endregion

        ///region ::Google
        // 材料设计UI套件
        const val Material = "1.3.0"

        // 自动生成SPI暴露服务文件
        const val AutoService = "1.0"
        ///endregion

        ///region ::Kotlin
        const val Kotlin = "1.5.21"

        // 协程
        const val Coroutine = "1.5.1"
        ///endregion

        ///region ::Jetpack
        const val Lifecycle = "2.3.1"
        ///endregion

        ///region ::Testing
        const val TestJunit = "4.13"
        const val TestExtJunit = "1.1.2"
        const val TestEspresso = "3.3.0"
        //endregion

        const val NET_NETTY = "4.1.67.Final"

        const val OkHttp = "4.9.0"                          // OkHttp
        const val OkHttpInterceptorLogging = "4.9.1"        // OkHttp 请求Log拦截器
        const val Retrofit = "2.9.0"                        // Retrofit
        const val RetrofitConverterGson = "2.9.0"           // Retrofit Gson 转换器
        const val Gson = "2.8.7"                            // Gson
        const val MMKV = "1.2.9"                            // 腾讯 MMKV 替代SP
        const val AutoSize = "1.2.1"                        // 屏幕适配
        const val Glide = "4.12.0"                          // Glide
        const val ARoute = "1.5.1"                          // 阿里路由
        const val ARouteCompiler = "1.5.1"                  // 阿里路由 APT
        const val EventBus = "3.2.0"                        // 事件总线
    }
}
