package ua.afanasievdeveloper.android_kotlin_cicerone_bottom_navigation.screens

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_local.*
import ru.terrakok.cicerone.Router
import ua.afanasievdeveloper.android_kotlin_cicerone_bottom_navigation.*
import ua.afanasievdeveloper.android_kotlin_cicerone_bottom_navigation.navigation.Screen
import ua.afanasievdeveloper.android_kotlin_cicerone_bottom_navigation.utils.getGlobalRouter
import ua.afanasievdeveloper.android_kotlin_cicerone_bottom_navigation.utils.getLocalRouter
import ua.afanasievdeveloper.android_kotlin_cicerone_bottom_navigation.utils.require
import ua.afanasievdeveloper.android_kotlin_cicerone_bottom_navigation.utils.withArguments

/**
 * @author A. Afanasiev (https://github.com/afanasievdeveloper)
 */
class LocalFragment : Fragment() {

    private lateinit var localRouter: Router
    private lateinit var globalRouter: Router

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        localRouter = getLocalRouter()
        globalRouter = getGlobalRouter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_local, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val name = (savedInstanceState ?: arguments).require<String>(KEY)
        testTextView.text = name
        nextLocalButton.setOnClickListener { localRouter.navigateTo(Screen.Local("$name+ ")) }
        nextGlobalButton.setOnClickListener { globalRouter.navigateTo(Screen.Global("Global + ")) }
    }

    companion object {
        private const val KEY = ".bundles.key"

        fun newInstance(name: String): LocalFragment {
            return LocalFragment().withArguments { putString(KEY, name) }
        }
    }
}