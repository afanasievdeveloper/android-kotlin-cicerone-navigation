package ua.afanasievdeveloper.android_kotlin_cicerone_bottom_navigation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.fragment_main.*
import ua.afanasievdeveloper.android_kotlin_cicerone_bottom_navigation.navigation.ContainerFragment
import ua.afanasievdeveloper.android_kotlin_cicerone_bottom_navigation.R
import ua.afanasievdeveloper.android_kotlin_cicerone_bottom_navigation.navigation.ContainerType
import java.lang.IllegalArgumentException

/**
 * @author A. Afanasiev (https://github.com/afanasievdeveloper)
 */
class MainFragment : Fragment() {

    private var currentType: ContainerType? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        savedInstanceState?.getString(KEY)?.let { typeName ->
            val type = ContainerType.valueOf(typeName)
            currentType = type
            mainBottomNavigationView.selectedItemId = type.itemId()
        }

        if(currentType == null) {
            currentType = ContainerType.FIRST
        }

        mainBottomNavigationView.setOnNavigationItemSelectedListener {
            val type = it.itemId.containerType()
            currentType = type
            childFragmentManager.changeContainer(type.name)
            return@setOnNavigationItemSelectedListener true
        }

        currentType?.let { mainBottomNavigationView.selectedItemId = it.itemId() }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY, currentType?.name)
    }

    private fun Int.containerType(): ContainerType = when (this) {
        R.id.itemFirst -> ContainerType.FIRST
        R.id.itemSecond -> ContainerType.SECOND
        R.id.itemThird -> ContainerType.THIRD
        R.id.itemFourth -> ContainerType.FOURTH
        else -> throw IllegalArgumentException("Unknown navigation item")
    }

    private fun ContainerType.itemId(): Int = when (this) {
        ContainerType.FIRST -> R.id.itemFirst
        ContainerType.SECOND -> R.id.itemSecond
        ContainerType.THIRD -> R.id.itemThird
        ContainerType.FOURTH -> R.id.itemFourth
    }

    private fun FragmentManager.changeContainer(typeName: String) {
        val currentContainer = fragments.firstOrNull { it.isVisible }
        val nextContainer = findFragmentByTag(typeName) ?: ContainerFragment.newInstance(typeName)

        if (currentContainer == nextContainer) return

        beginTransaction().apply {
            if (!nextContainer.isAdded) {
                add(R.id.itemFragmentContainer, nextContainer, typeName)
            }
            currentContainer?.let { hide(it) }
            show(nextContainer)
            commitNow()
        }
    }

    companion object {
        private const val KEY = ".bundle.main.key"
        fun newInstance() = MainFragment()
    }
}