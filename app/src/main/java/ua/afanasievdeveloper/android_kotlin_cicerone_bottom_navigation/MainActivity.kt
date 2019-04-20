package ua.afanasievdeveloper.android_kotlin_cicerone_bottom_navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.android.support.SupportAppScreen

interface RouterProvider {
    val router: Router
}

class MainActivity : AppCompatActivity(), RouterProvider {

    private val exitManager = ExitManager()

    private val cicerone = Cicerone.create()
    private val navigator by lazy(LazyThreadSafetyMode.NONE) {
        SupportAppNavigator(this, supportFragmentManager, R.id.mainFragmentContainer)
    }

    override val router: Router
        get() = cicerone.router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        router.newRootScreen(Screen.Authorization())
    }

    override fun onResume() {
        super.onResume()
        cicerone.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        cicerone.navigatorHolder.setNavigator(null)
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
        manager.exitIfNeed { screenRouter.exit() } ?: toast("Нажмите еще раз для выхода")
    }
}

class ExitManager(
    private val delay: Long = Toast.LENGTH_SHORT.toLong()
) {

    private var lastTime: Long? = null

    fun exitIfNeed(finishAction: () -> Unit): Unit? {
        val currentTime = System.currentTimeMillis()
        return if (currentTime - (lastTime ?: 0L) > delay) {
            lastTime = currentTime
            null
        } else {
            finishAction()
        }
    }
}

sealed class Screen : SupportAppScreen() {

    override fun getScreenKey(): String = this::class.java.simpleName

    override fun getFragment(): Fragment = when (this) {
        is Main -> MainFragment.newInstance()
        is Authorization -> AuthorizationFragment.newInstance()
        is Test -> TestFragment.newInstance(name)
        is Simple -> SimpleFragment.newInstance(name)
    }

    class Main : Screen()

    class Authorization : Screen()

    data class Test(val name: String) : Screen()

    data class Simple(val name: String) : Screen()
}