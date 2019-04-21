package ua.afanasievdeveloper.android_kotlin_cicerone_bottom_navigation.screens

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.Router
import ua.afanasievdeveloper.android_kotlin_cicerone_bottom_navigation.R
import ua.afanasievdeveloper.android_kotlin_cicerone_bottom_navigation.navigation.Screen
import ua.afanasievdeveloper.android_kotlin_cicerone_bottom_navigation.utils.getGlobalRouter

/**
 * @author A. Afanasiev (https://github.com/afanasievdeveloper)
 */
class SplashFragment : Fragment() {

    private lateinit var globalRouter: Router

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        globalRouter = getGlobalRouter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Handler().postDelayed(
            { globalRouter.replaceScreen(Screen.Authorization()) },
            2000L
        )
    }

    companion object {
        fun newInstance() = SplashFragment()
    }
}