package ua.afanasievdeveloper.android_kotlin_cicerone_bottom_navigation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_test.*
import ru.terrakok.cicerone.Router

/**
 * Replase.
 *
 * @author A. Afanasiev (https://github.com/afanasievdeveloper)
 */
class TestFragment : Fragment() {

    private lateinit var router: Router
    private lateinit var globalRouter: Router

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        router = localRouter()
        globalRouter = globalRouter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val name = (savedInstanceState ?: arguments).require<String>(KEY)
        testTextView.text = name
        next.setOnClickListener { router.navigateTo(Screen.Test("$name $name")) }
        global.setOnClickListener { globalRouter.navigateTo(Screen.Simple("+ ")) }
    }

    companion object {
        private const val KEY = ".bundles.key"

        fun newInstance(name: String): TestFragment {
            return TestFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY, name)
                }
            }
        }
    }
}

fun Fragment.localRouter(): Router {
    return (parentFragment as? RouterProvider)?.router
        ?: throw ClassCastException("$parentFragment must implement RouterProvider")
}