package com.example.movielister.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.movielister.R
import com.example.movielister.model.Result


@Composable
fun MovieCard(movie: Result, modifier: Modifier = Modifier,onItemClick:(Result)->Unit) {

    Card (
        modifier = modifier
            .clickable { onItemClick(movie) },
        elevation = CardDefaults.cardElevation(defaultElevation = 20.dp),
        shape = RoundedCornerShape(8.dp),

    ){
//         ****Can use the following in asyncimage for loading and error
//        error = painterResource(R.drawable.ic_broken_image),
//        placeholder = painterResource(R.drawable.loading_img),
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(convertToImageUrl(movie.poster_path))
                .crossfade(true)
                .build(),
            contentDescription = stringResource(R.string.MoviePhoto),

            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()

            ,
            error = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ){
                    Image(painter = painterResource(id = R.drawable.ic_broken_image), contentDescription = "errormessage",
                        modifier= Modifier
                            .size(100.dp)
                            .scale(0.8f))
                }

            },
            loading={
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(50.dp)
//                        .clip(RoundedCornerShape(8.dp))
//                        .background(MaterialTheme.colorScheme.primary)
                            .scale(0.4f),// Scale down the CircularProgressIndicator



                    )
                }
            }
        )
    }
}


