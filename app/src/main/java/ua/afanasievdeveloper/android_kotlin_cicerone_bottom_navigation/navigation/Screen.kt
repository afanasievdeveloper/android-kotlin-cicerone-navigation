package ua.afanasievdeveloper.android_kotlin_cicerone_bottom_navigation.navigation

import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.android.support.SupportAppScreen
import ua.afanasievdeveloper.android_kotlin_cicerone_bottom_navigation.screens.*

/**
 * @author A. Afanasiev (https://github.com/afanasievdeveloper)
 */
sealed class Screen : SupportAppScreen() {

    override fun getScreenKey(): String = this::class.java.simpleName

    override fun getFragment(): Fragment = when (this) {
        is Authorization -> AuthorizationFragment.newInstance()
        is Splash -> SplashFragment.newInstance()
        is Main -> MainFragment.newInstance()
        is Local -> LocalFragment.newInstance(name)
        is Global -> GlobalFragment.newInstance(name)
    }

    class Splash : Screen()

    class Authorization : Screen()

    class Main : Screen()

    data class Local(val name: String) : Screen()

    data class Global(val name: String) : Screen()
}