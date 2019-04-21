package ua.afanasievdeveloper.android_kotlin_cicerone_bottom_navigation.screens

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_authorization.*
import ru.terrakok.cicerone.Router
import ua.afanasievdeveloper.android_kotlin_cicerone_bottom_navigation.R
import ua.afanasievdeveloper.android_kotlin_cicerone_bottom_navigation.utils.getGlobalRouter
import ua.afanasievdeveloper.android_kotlin_cicerone_bottom_navigation.navigation.Screen

/**
 * @author A. Afanasiev (https://github.com/afanasievdeveloper)
 */
class AuthorizationFragment : Fragment() {

    private lateinit var router: Router

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        router = getGlobalRouter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_authorization, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authButton.setOnClickListener { router.replaceScreen(Screen.Main()) }
    }

    companion object {
        fun newInstance() = AuthorizationFragment()
    }
}