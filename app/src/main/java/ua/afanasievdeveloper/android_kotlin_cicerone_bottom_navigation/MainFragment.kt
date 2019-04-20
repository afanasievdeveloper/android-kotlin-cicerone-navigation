package ua.afanasievdeveloper.android_kotlin_cicerone_bottom_navigation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.jakewharton.rxbinding3.material.itemSelections
import kotlinx.android.synthetic.main.fragment_main.*
import ua.afanasievdeveloper.android_kotlin_cicerone_bottom_navigation.navigation.LocalItem
import java.lang.IllegalArgumentException

/**
 * Replase.
 *
 * @author A. Afanasiev (https://github.com/afanasievdeveloper)
 */
class MainFragment : Fragment() {

    private var currentType: LocalItem? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val name = savedInstanceState?.getString(KEY)
        name?.let { currentType = LocalItem.valueOf(it) }
        currentType?.let {
            mainBottomNavigationView.selectedItemId = it.itemId()
        }

        val a = mainBottomNavigationView.itemSelections()
            //.debounce(200, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
            .map { it.itemId.containerType() }
            .subscribe {
                currentType = it
                childFragmentManager.changeContainer(it)
            }

        mainBottomNavigationView.selectedItemId
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY, mainBottomNavigationView.selectedItemId.containerType().name)
    }

    private fun Int.containerType(): LocalItem {
        return when (this) {
            R.id.itemAssembly -> LocalItem.ASSEMBLY
            R.id.itemHistory -> LocalItem.HISTORY
            R.id.itemDisassembly -> LocalItem.DISASSEMBLY
            R.id.itemCabinet -> LocalItem.CABINET
            else -> throw IllegalArgumentException("Unknown navigation item")
        }
    }

    private fun LocalItem.itemId(): Int {
        return when (this) {
            LocalItem.ASSEMBLY -> R.id.itemAssembly
            LocalItem.HISTORY -> R.id.itemHistory
            LocalItem.DISASSEMBLY -> R.id.itemDisassembly
            LocalItem.CABINET -> R.id.itemCabinet
        }
    }


    private fun FragmentManager.changeContainer(type: LocalItem) {
        val currentContainer = fragments.firstOrNull { it.isVisible }
        val nextContainer = findFragmentByTag(type.name) ?: ContainerFragment.newInstance(type.name)

        if (currentContainer == nextContainer) return

        beginTransaction().apply {
            if (!nextContainer.isAdded) {
                add(R.id.itemFragmentContainer, nextContainer, type.name)
            }
            currentContainer?.let { hide(it) }
            show(nextContainer)
            commitNow()
        }
    }

    companion object {
        private const val KEY = "somekey"

        fun newInstance() = MainFragment()
    }
}


fun Context.toast(text: String) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

fun Fragment.toast(text: String) = context?.toast(text)