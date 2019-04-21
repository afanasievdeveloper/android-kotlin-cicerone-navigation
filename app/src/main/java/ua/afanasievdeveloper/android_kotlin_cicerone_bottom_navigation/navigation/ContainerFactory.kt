package ua.afanasievdeveloper.android_kotlin_cicerone_bottom_navigation.navigation

import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

/**
 * @author A. Afanasiev (https://github.com/afanasievdeveloper)
 */
class ContainerFactory(
    private val containerSupplier: () -> Cicerone<Router>,
    private val containers: HashMap<String, Cicerone<Router>> = hashMapOf()
) {
    @Synchronized
    fun getContainer(name: String): Cicerone<Router> = containers.getOrPut(name, containerSupplier)
}

// Must be singleton
val factory = ContainerFactory(containerSupplier = { Cicerone.create() })