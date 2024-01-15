package com.example.movielister.ui.theme.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.movielister.model.Result
import com.example.movielister.navigation.SharedViewModel
import com.example.movielister.viewmodel.DataStoreViewModel
import com.example.movielister.components.DetailScreenTopAppBar
import com.example.movielister.viewmodel.WatchListViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    navController: NavController,
    sharedViewModel: SharedViewModel,
    watchListViewModel: WatchListViewModel,
    dataStoreViewModel: DataStoreViewModel
) {

      val movie=sharedViewModel.movie
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            if (movie != null) {
                DetailScreenTopAppBar(navController = navController,movie.title,scrollBehavior = scrollBehavior)
            }
        },

    ){
        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),

        ) {
            item{
                Spacer(modifier = Modifier.height(70.dp))
                Card(modifier = Modifier
                    .height(200.dp),
                    shape = RoundedCornerShape(8.dp)

                ) {
                    if (movie != null) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(convertToImageUrl(movie.backdrop_path))
                                .crossfade(true)
                                .build(),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize(),
                            contentScale = ContentScale.FillBounds
                        )
                    }

                }
            }



//            IconButton(onClick = {
//                navController.popBackStack()
//            }, modifier = Modifier
//                .background(Color.Transparent)
//                .padding(10.dp)) {
//                Icon(imageVector = Icons.Filled.KeyboardArrowLeft, contentDescription = "Arrow Back",
//                    modifier = Modifier.size(52.dp), tint = Color.White)
//            }
//            if(movie!=null){
//                Text(text = movie.title, modifier = Modifier
//
//                    .padding(bottom = 8.dp, start = 8.dp, end = 48.dp), color = Color.White,
//                    style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
//            }

            item{
                Spacer(modifier = Modifier.width(4.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    horizontalArrangement = Arrangement.Center
                ){
                    if (movie != null) {
                        Text(text = "${movie.release_date}",
                            style = TextStyle(
                                fontStyle = FontStyle.Normal,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.LightGray,
                                fontSize = 13.sp

                            ))
                    }
                    
                    Spacer(modifier = Modifier.width(5.dp))


                    if (movie != null) {
                        Text(text = "${movie.original_language}",
                            style = TextStyle(
                                fontStyle = FontStyle.Italic,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.LightGray,
                                fontSize = 13.sp

                            )
                        )
                    }
                }
            }
            item{

                if (movie != null) {
                    Spacer(modifier = Modifier.width(8.dp))
                    ProjectText(data =movie.overview, title = "Plot")
                }
            }

            item{
                Spacer(modifier = Modifier.height(100.dp))
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)) {
                    Column {
                        IconButton(onClick = {
                            if (movie != null) {

                                if(watchListViewModel.getCount(movie)==0){
                                    watchListViewModel.addToWatchList(movie)
                                }else{
                                    watchListViewModel.removeFromWatchList(movie)
                                }
//                            if (movie != null) {
//                                dataStoreViewModel.addToWatchList(movie)
//                            }

                            }
                        }) {
                            if(movie?.let { it1 -> watchListViewModel.getCount(it1) } ==0){
                                Icon(
                                    imageVector = Icons.Outlined.FavoriteBorder,
                                    contentDescription = "Watchlist"
                                )
                            }else{
                                Icon(
                                    imageVector = Icons.Filled.Favorite,
                                    contentDescription = "Watchlist"
                                )
                            }


                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = " Watchlist",
                            style = TextStyle(
                                fontStyle = FontStyle.Normal,
                                fontSize = 11.sp
                            ))

                    }

                    Spacer(modifier = Modifier.width(20.dp))

                    Column {
                        IconButton(onClick = {
                        }) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription ="Share" )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "    Share",
                            style = TextStyle(
                                fontStyle = FontStyle.Normal,
                                fontSize = 11.sp
                            ))

                    }
                }
            }

        }

    }

    }



@Composable
private fun DetailBody(Movie: Result) {
    Column(modifier = Modifier.padding(12.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth(), Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.width(240.dp)) {
//                ProjectText(Movie.genre, "Genre")
                ProjectText(data = Movie.release_date, title = "Year")
            }
            Spacer(modifier = Modifier.width(10.dp))
//            ProjectText(data = newMovieList.imdb, title = "IMDB")
        }
        Spacer(modifier = Modifier.height(10.dp))
        ProjectText(data =Movie.overview, title = "Plot")
        Spacer(modifier = Modifier.height(20.dp))
//        ProjectText(data = newMovieList.director, title = "Director")
//        ProjectText(data = newMovieList.actors, title = "Actors")
//
//        ProjectText(data = newMovieList.awards, title = "Awards")


    }
}

@Composable
private fun ProjectText(data: String?, title: String?) {
    Text(text = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFFf26e00),
                fontSize = 20.sp
            )
        ) {
            append("$title:  \n")
        }
        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal,
                color = Color.White,
                fontSize = 13.sp
            )
        ) {
            append(data!!)
        }
    }, Modifier.padding(4.dp))
}

