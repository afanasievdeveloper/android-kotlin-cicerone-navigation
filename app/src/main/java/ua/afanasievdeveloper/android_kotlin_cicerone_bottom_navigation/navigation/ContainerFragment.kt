package ua.afanasievdeveloper.android_kotlin_cicerone_bottom_navigation.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ua.afanasievdeveloper.android_kotlin_cicerone_bottom_navigation.R
import ua.afanasievdeveloper.android_kotlin_cicerone_bottom_navigation.utils.require
import ua.afanasievdeveloper.android_kotlin_cicerone_bottom_navigation.utils.withArguments

/**
 * @author A. Afanasiev (https://github.com/afanasievdeveloper)
 */
class ContainerFragment : Fragment(), RouterProvider {

    // Inject
    private val containerFactory: ContainerFactory = factory

    private lateinit var containerType: ContainerType

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
        containerType = ContainerType.valueOf(containerName)

        val container = containerFactory.getContainer(containerType.name)
        localRouter = container.router
        localNavigatorHolder = container.navigatorHolder
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

    private fun ContainerType.defaultScreen(): Screen {
        val name = when (this) {
            ContainerType.FIRST -> "1"
            ContainerType.SECOND -> "2"
            ContainerType.THIRD -> "3"
            ContainerType.FOURTH -> "4"
        }
        return Screen.Local("Local $name + ")
    }

    companion object {
        private const val KEY = ".bundle.test.key"

        fun newInstance(name: String): ContainerFragment {
            return ContainerFragment().withArguments { putString(KEY, name) }
        }
    }
}