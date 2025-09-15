package com.example.mealz.ui.meals

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.mealz.ui.AppBar
import com.example.mealz.ui.details.MealDetailsScreen
import com.example.mealz.ui.theme.MealzTheme
import com.example.model.response.MealsResponse

private const val TAG = "MealsCategoriesScreen"
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MealsCategoriesScreen(navigationCallback: (String) -> Unit) {
    val viewModel: MealsCategoriesViewModel = viewModel()
    val meals = viewModel.mealsState.value
    Scaffold(topBar = { AppBar(
        title = "Mealz Categories",
        icon = Icons.Default.Home
    ){ } }) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ){
            LazyColumn(
                contentPadding = PaddingValues(16.dp)
            ) {
                items(meals){ meals ->
                    MealsCategory(meals,navigationCallback)


                }
            }
        }
    }

}

@Composable
fun MealsCategory(meals: MealsResponse,navigationCallback: (String) -> Unit){
    var isExpanded by remember { mutableStateOf(false)}
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 12.dp
        ),
        border = CardDefaults.outlinedCardBorder(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .clickable {
                navigationCallback(meals.id)
            }

    ) {
        Row (modifier = Modifier.animateContentSize()){

            AsyncImage(
                model = meals.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(88.dp)
                    .padding(4.dp)
            )

            Column(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .fillMaxWidth(0.8f)
                    .padding(4.dp)
            ) {
                Text(
                    text = meals.name,
                    style = MaterialTheme.typography.headlineSmall
                )
                CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onSurfaceVariant) {
                    Text(
                        text = meals.description,
                        style = MaterialTheme.typography.titleSmall,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = if (isExpanded)10 else 4,
                    )
                }
            }
            Icon(
                imageVector = if (isExpanded)
                    Icons.Filled.KeyboardArrowUp
                else
                    Icons.Filled.KeyboardArrowDown,
                contentDescription = "Expand row icon",
                modifier = Modifier
                    .padding(16.dp)
                    .align(
                        if (isExpanded)
                            Alignment.Bottom
                    else
                    Alignment.CenterVertically)
                    .clickable {
                        isExpanded = !isExpanded
                    }
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MealzTheme {
        MealsCategoriesScreen({})
    }
}

@Preview(showBackground = true)
@Composable
fun MealsDetailPreview(){
    MealzTheme {
        MealDetailsScreen(null,null)
    }
}