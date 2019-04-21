package ua.afanasievdeveloper.android_kotlin_cicerone_bottom_navigation.screens

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_global.*
import ru.terrakok.cicerone.Router
import ua.afanasievdeveloper.android_kotlin_cicerone_bottom_navigation.R
import ua.afanasievdeveloper.android_kotlin_cicerone_bottom_navigation.utils.getGlobalRouter
import ua.afanasievdeveloper.android_kotlin_cicerone_bottom_navigation.navigation.Screen
import ua.afanasievdeveloper.android_kotlin_cicerone_bottom_navigation.utils.require
import ua.afanasievdeveloper.android_kotlin_cicerone_bottom_navigation.utils.withArguments

/**
 * @author A. Afanasiev (https://github.com/afanasievdeveloper)
 */
class GlobalFragment : Fragment() {

    private lateinit var globalRouter: Router

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        globalRouter = getGlobalRouter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_global, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val name = (savedInstanceState ?: arguments).require<String>(KEY)
        testTextView.text = name
        nextGlobalButton.setOnClickListener { globalRouter.navigateTo(Screen.Global("$name+ ")) }
    }

    companion object {
        private const val KEY = ".bundles.some.key"

        fun newInstance(name: String): GlobalFragment {
            return GlobalFragment().withArguments { putString(KEY, name) }
        }
    }
}