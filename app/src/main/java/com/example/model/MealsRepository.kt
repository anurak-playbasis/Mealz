package com.example.model

import com.example.model.api.MealsWebService
import com.example.model.response.MealsCategoriesResponse
import com.example.model.response.MealsResponse

class MealsRepository(private val webService: MealsWebService = MealsWebService()) {

    private var cachedMeals = listOf<MealsResponse>()

    suspend fun getMeals(): MealsCategoriesResponse {
        val response = webService.getMeals()
        cachedMeals = response.categories
        return response
    }

    fun getMeal(id: String): MealsResponse?{
        return cachedMeals.firstOrNull{
            it.id == id
        }
    }

    companion object{
        @Volatile
        private var instant: MealsRepository? = null
        fun getInstance() = instant?: synchronized(this){
            instant ?: MealsRepository().also {
                instant = it
            }
        }
    }
}