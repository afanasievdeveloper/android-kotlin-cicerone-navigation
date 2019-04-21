package ua.afanasievdeveloper.android_kotlin_cicerone_bottom_navigation.utils

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.Router
import ua.afanasievdeveloper.android_kotlin_cicerone_bottom_navigation.navigation.RouterProvider

/**
 * @author A. Afanasiev (https://github.com/afanasievdeveloper)
 */

inline fun <reified T> Bundle?.require(key: String): T {
    requireNotNull(value = this).apply {
        check(value = containsKey(key), lazyMessage = { "Value with key $key not found." })
        return get(key) as T
    }
}

inline fun <reified T : Fragment> Fragment.withArguments(bundleBody: Bundle.() -> Unit): T {
    arguments = Bundle().apply(bundleBody)
    return this as T
}

fun Fragment.getGlobalRouter(): Router {
    return (activity as? RouterProvider)?.router ?: throw ClassCastException("$activity must implement RouterProvider")
}

fun Fragment.getLocalRouter(): Router {
    return (parentFragment as? RouterProvider)?.router
        ?: throw ClassCastException("$parentFragment must implement RouterProvider")
}

fun Context.toast(text: String) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()