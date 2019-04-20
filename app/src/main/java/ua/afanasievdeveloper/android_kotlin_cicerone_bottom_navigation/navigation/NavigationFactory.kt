package ua.afanasievdeveloper.android_kotlin_cicerone_bottom_navigation.navigation

import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

/**
 * Replase.
 *
 * @author A. Afanasiev (https://github.com/afanasievdeveloper)
 */
class NavigationFactory(
    private val routerSupplier: () -> Cicerone<Router>,
    private val containers: HashMap<String, Cicerone<Router>> = hashMapOf()
) {

    @Synchronized
    fun getContainer(name: String): Cicerone<Router> {
        return containers.getOrPut(name, routerSupplier)
    }
}