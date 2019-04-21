package ua.afanasievdeveloper.android_kotlin_cicerone_bottom_navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ua.afanasievdeveloper.android_kotlin_cicerone_bottom_navigation.navigation.RouterProvider
import ua.afanasievdeveloper.android_kotlin_cicerone_bottom_navigation.navigation.Screen
import ua.afanasievdeveloper.android_kotlin_cicerone_bottom_navigation.utils.ExitManager
import ua.afanasievdeveloper.android_kotlin_cicerone_bottom_navigation.utils.toast

/**
 * @author A. Afanasiev (https://github.com/afanasievdeveloper)
 */
class MainActivity : AppCompatActivity(), RouterProvider {

    private val exitManager = ExitManager()

    private val navigation = Cicerone.create()
    private val navigator by lazy(LazyThreadSafetyMode.NONE) {
        SupportAppNavigator(this, supportFragmentManager, R.id.mainFragmentContainer)
    }

    override val router: Router
        get() = navigation.router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        router.newRootScreen(Screen.Splash())
    }

    override fun onResume() {
        super.onResume()
        navigation.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigation.navigatorHolder.setNavigator(null)
    }

    override fun onBackPressed() {
        when {
            supportFragmentManager.backStackEntryCount > 0 -> router.exit()
            else -> {
                supportFragmentManager.fragments.firstOrNull { it.isVisible }
                    ?.childFragmentManager?.fragments?.firstOrNull { it.isVisible }?.also { container ->
                    if (container.childFragmentManager.backStackEntryCount > 0) {
                        (container as? RouterProvider)?.router?.exit()
                    } else {
                        exitIfNeed()
                    }
                } ?: exitIfNeed()
            }
        }
    }

    private fun exitIfNeed(manager: ExitManager = exitManager, screenRouter: Router = router) {
        manager.exitIfNeed { screenRouter.exit() } ?: toast("Press again to exit")
    }
}