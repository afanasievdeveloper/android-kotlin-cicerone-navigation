package ua.afanasievdeveloper.android_kotlin_cicerone_bottom_navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ua.afanasievdeveloper.android_kotlin_cicerone_bottom_navigation.navigation.LocalItem
import ua.afanasievdeveloper.android_kotlin_cicerone_bottom_navigation.navigation.NavigationFactory

/**
 * Replase.
 *
 * @author A. Afanasiev (https://github.com/afanasievdeveloper)
 */

val navigationFactory = NavigationFactory(routerSupplier = { Cicerone.create() })

class ContainerFragment : Fragment(), RouterProvider {

    private lateinit var containerType: LocalItem
    private lateinit var localRouter: Router
    private lateinit var localNavigatorHolder: NavigatorHolder
    private val localNavigator by lazy(LazyThreadSafetyMode.NONE) {
        SupportAppNavigator(requireActivity(), childFragmentManager, R.id.navigationContainer)
    }

    override val router: Router
        get() = localRouter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_container, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val containerName = (savedInstanceState ?: arguments).require<String>(KEY)
        containerType = LocalItem.valueOf(containerName)

        val cicerone = navigationFactory.getContainer(containerType.name)
        localRouter = cicerone.router
        localNavigatorHolder = cicerone.navigatorHolder
    }

    override fun onResume() {
        super.onResume()
        localNavigatorHolder.setNavigator(localNavigator)
    }

    override fun onPause() {
        super.onPause()
        localNavigatorHolder.setNavigator(null)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY, containerType.name)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        childFragmentManager.findFragmentById(R.id.navigationContainer)
            ?: localRouter.replaceScreen(containerType.defaultScreen())
    }

    private fun LocalItem.defaultScreen(): Screen {
        val n = when (this) {
            LocalItem.ASSEMBLY -> "1"
            LocalItem.HISTORY -> "2"
            LocalItem.DISASSEMBLY -> "3"
            LocalItem.CABINET -> "4"
        }
        return Screen.Test("$n ")
    }


    companion object {
        private const val KEY = ".bundle.test.key"

        fun newInstance(name: String): ContainerFragment {
            return ContainerFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY, name)
                }
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
fun <T> Bundle?.require(key: String): T {
    requireNotNull(value = this).apply {
        check(value = containsKey(key), lazyMessage = { "Value with key $key not found." })
        return get(key) as T
    }
}