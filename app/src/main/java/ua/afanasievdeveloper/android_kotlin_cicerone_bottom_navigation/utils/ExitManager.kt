package ua.afanasievdeveloper.android_kotlin_cicerone_bottom_navigation.utils

/**
 * @author A. Afanasiev (https://github.com/afanasievdeveloper)
 */
class ExitManager(
    private val delay: Long = 2000L
) {

    private var lastTime: Long = 0

    fun exitIfNeed(exitAction: () -> Unit): Unit? {
        val currentTime = System.currentTimeMillis()
        return if (currentTime - lastTime > delay) {
            lastTime = currentTime
            null
        } else {
            exitAction()
        }
    }
}