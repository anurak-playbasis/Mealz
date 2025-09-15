package com.example.mealz.ui.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.model.MealsRepository
import com.example.model.response.MealsResponse

private const val TAG = "MealDetailsViewModel"
class MealDetailsViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    private val repository: MealsRepository = MealsRepository.getInstance()
    var mealState = mutableStateOf<MealsResponse?>(null)
    init {
        val mealId = savedStateHandle.get<String>("meal_category_id")?: ""
        mealState.value = repository.getMeal(mealId)
    }
}