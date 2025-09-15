package com.example.mealz.ui.details

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.LocalPlatformContext
import coil3.compose.rememberAsyncImagePainter
import coil3.compose.rememberConstraintsSizeResolver
import coil3.request.ImageRequest
import com.example.mealz.ui.AppBar
import com.example.model.response.MealsResponse

@Composable
fun MealDetailsScreen(meal: MealsResponse?,navController: NavHostController?){
    var profilePictureState by remember { mutableStateOf(MealDetailsState.Normal) }
    val transition = updateTransition(targetState = profilePictureState, label = "")
    val imageSizeDp by transition.animateDp(targetValueByState = {it.size})
    val color by transition.animateColor(targetValueByState = {it.color})
    val widthSize by transition.animateDp(targetValueByState = {it.borderWith})
    Scaffold(topBar = { AppBar(
        title = "Mealz Detail",
        icon = Icons.AutoMirrored.Filled.ArrowBack
    ){
        navController?.navigateUp()

    }
    }) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ){
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {

                MealDetailTitle(meal)
                MealDetailImage(meal, imageSizeDp)
                MealDetailDescription(meal,widthSize,color)
                MealDetailButton(profilePictureState){MealDetailsState ->
                    profilePictureState = MealDetailsState
                }
            }
        }
    }

}
@Composable
fun MealDetailTitle(meal: MealsResponse?){
    Text(
        text = meal?.name ?: "default name",
        modifier = Modifier
            .padding(top = 25.dp)
    )
}
@Composable
fun MealDetailImage(meal: MealsResponse?, imageSizeDp: Dp){
    Row {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center

        )  {
            val sizeResolver = rememberConstraintsSizeResolver()
            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalPlatformContext.current)
                    .data(meal?.imageUrl)
                    .size(sizeResolver)
                    .build(),
            )

            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .then(sizeResolver)
                    .size(imageSizeDp),
            )
        }

    }
}
@Composable
fun MealDetailDescription(meal: MealsResponse?, widthSize: Dp, color: Color){
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 12.dp
        ),
        border = BorderStroke(
            width = widthSize,
            color = color
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ){

        Text(
            text = meal?.description ?: "",
            modifier = Modifier.padding(16.dp)
        )
    }
}
@Composable
fun MealDetailButton(profilePictureState: MealDetailsState,onClickDetailsState: (MealDetailsState) -> Unit){
    Button(
        modifier = Modifier
            .padding(top = 16.dp),

        onClick = { onClickDetailsState(
            if (profilePictureState == MealDetailsState.Normal)
                MealDetailsState.Expanded
            else
                MealDetailsState.Normal
        ) }
    ) {
        Text("Change state of meal detail")
    }
}

enum class MealDetailsState(val color: Color, val size: Dp, val borderWith: Dp){
    Normal(Color.LightGray,120.dp, 5.dp),
    Expanded(Color.DarkGray,200.dp, 5.dp)
}