package top.j3dream.buildsrc.bean

import top.j3dream.buildsrc.constant.Constant

/**
 * - 文件描述: 通用的 model info
 * @author Jia Junjian
 * @since 2021/8/11 13:43
 */
open class ModuleInfo(
    /**
     * 是否为应用模式
     */
    open var isAppMode: Boolean,
    /**
     * version code
     */
    open var versionCode: Int,
    /**
     * version name
     */
    open var versionName: String,
    /**
     * compile sdk version
     */
    val compileSdkVersion: Int = Constant.MODULE_DEF_COMPILE_SDK_VERSION,
    /**
     * build tools version
     */
    val buildToolsVersion: String = Constant.MODULE_DEF_BUILD_TOOLS_VERSION,

    /**
     * min sdk version
     */
    val minSdkVersion: Int = Constant.MODULE_DEF_MIN_SDK_VERSION,
    /**
     * target Sdk Version
     */
    val targetSdkVersion: Int = Constant.MODULE_DEF_TARGET_SDK_VERSION
)

/**
 * - 文件描述: app model info
 * @author Jia Junjian
 * @since 2021/8/11 13:54
 */
data class AppModuleInfo(
    val appId: String,
    override var versionCode: Int = Constant.MODULE_DEF_VERSION_CODE,
    override var versionName: String = Constant.MODULE_DEF_VERSION_NAME
) : ModuleInfo(isAppMode = true, versionCode, versionName)

/**
 * - 文件描述: library model info
 * @author Jia Junjian
 * @since 2021/8/11 13:54
 */
data class LibraryModuleInfo(
    override var isAppMode: Boolean,
    override var versionCode: Int = Constant.MODULE_DEF_VERSION_CODE,
    override var versionName: String = Constant.MODULE_DEF_VERSION_NAME
) : ModuleInfo(isAppMode, versionCode, versionName)