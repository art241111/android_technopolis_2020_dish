package ru.art241111.dish_recipes.view_models

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import ru.art241111.dish_recipes.data.FullDish
import ru.art241111.dish_recipes.managers.NetManager
import ru.art241111.dish_recipes.models.DishRepository
import ru.art241111.dish_recipes.models.remoteDataSource.providers.searchDishByIngredientsFromEdamamProvider.dataModel.DishModel
import ru.art241111.dish_recipes.models.remoteDataSource.providers.searchDishByIngredientsFromEdamamProvider.dataModel.Recipes
import ru.art241111.dish_recipes.view_models.protocols.UpdateFavorite
import ru.art241111.kotlinmvvm.extensionFunctions.plusAssign

/**
 * ViewModel to work with data and searchDishActivity
 * @author Artem Geraimov.
 */
class SearchDishViewModel(application: Application)
                             : AndroidViewModel(application), UpdateFavorite {
    // Data repository.
    private val dishRepository: DishRepository = DishRepository(NetManager(getApplication()))

    // Array of dishes.
    val dishes = MutableLiveData<ArrayList<FullDish>>()
    var dishesArrayList = ArrayList<FullDish>()

    // Array of ingredients.
    val ingredients = ArrayList<String>()

    // Check data is loading or not.
    val isLoading = ObservableField(false)

    // TODO: Read about Disposable
    private val compositeDisposable = CompositeDisposable()

    // is application create firs
    var isApplicationCreateFirst = true

    // start page to load data
    private var startPosition = 0

    /**
     * Load new data, when data on screen end
     */
    fun loadDishesWhenOnScreenEnd(){
        startPosition += 10
        loadDishes()
    }

    /**
     * Load new data, when user enter new ingredient
     */
    fun loadDishesWhenUserAddNewIngredientOrStartApplication(){
        startPosition = 10
        isLoading.set(true)
        this.dishesArrayList = ArrayList()
        loadDishes()
    }

    /**
     * load dishes from repositories.
     */
    private fun loadDishes() {
        compositeDisposable += dishRepository
                .getDishes(ingredients, startPosition.toString())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<List<FullDish>>() {
                    override fun onError(e: Throwable) {
                        Log.e("SearchDishViewModel.kt",
                                "Error with reading data + ${e.message}")
                    }

                    override fun onNext(data: List<FullDish>) {
                        data.map{
                            dishesArrayList.add(it)
                        }
                        dishes.value = dishesArrayList
                    }

                    override fun onComplete() {
                        isLoading.set(false)
                    }
                })
    }

    override fun updateFavoriteAtAllArray() {
        dishesArrayList.map { updateFavoriteAtOneDish(it) }
        dishes.value = dishesArrayList

    }

    override fun updateFavoriteAtOneDish(dish: FullDish) {
        dishesArrayList.forEach{
            if(it == dish){
                it.isFavorite = dish.isFavorite
            }
        }
        dishes.value = dishesArrayList
    }

    /**
     * Delete observer's.
     */
    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    /**
     * Add ingredient to ingredients array.
     */
    fun addIngredient(ingredient: String) {
        ingredients.add(ingredient)
    }

    /**
     * Remove ingredient to ingredients array.
     */
    fun deleteIngredient(ingredient: String) {
        ingredients.remove(ingredient)
    }
}