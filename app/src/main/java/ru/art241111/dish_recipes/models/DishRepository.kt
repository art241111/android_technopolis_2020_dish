package ru.art241111.dish_recipes.models

import ru.art241111.dish_recipes.data.Dish
import ru.art241111.dish_recipes.managers.NetManager
import ru.art241111.dish_recipes.models.localDataSource.DishLocalDataSource
import ru.art241111.dish_recipes.models.localDataSource.OnDishLocalReadyCallback
import ru.art241111.dish_recipes.models.remoteDataSource.DishRemoteDataSource
import ru.art241111.dish_recipes.models.remoteDataSource.OnDishRemoteReadyCallback



class DishRepository(val netManager: NetManager) {
    val localDataSource = DishLocalDataSource()
    val remoteDataSource = DishRemoteDataSource()

    fun getRepositories(onRepositoryReadyCallback: OnRepositoryReadyCallback) {
        netManager.isConnectedToInternet?.let {
            if (it) {
                remoteDataSource.getRepositories(object : OnDishRemoteReadyCallback {
                    override fun onRemoteDataReady(data: ArrayList<Dish>) {
                        localDataSource.saveRepositories(data)
                        onRepositoryReadyCallback.onDataReady(data)
                    }
                })
            } else {
                localDataSource.getRepositories(object : OnDishLocalReadyCallback {
                    override fun onLocalDataReady(data: ArrayList<Dish>) {
                        onRepositoryReadyCallback.onDataReady(data)
                    }
                })
            }
        }
    }
}