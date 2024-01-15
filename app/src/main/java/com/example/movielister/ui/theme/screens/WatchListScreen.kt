package com.example.movielister.ui.theme.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.movielister.components.MovieCard
import com.example.movielister.navigation.Movie_enum
import com.example.movielister.navigation.SharedViewModel
import com.example.movielister.viewmodel.DataStoreViewModel
import com.example.movielister.components.MovieTopAppBar
import com.example.movielister.viewmodel.BottomNavigationViewModel
import com.example.movielister.viewmodel.WatchListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WatchListScreen(
    navController: NavController,
    watchListViewModel: WatchListViewModel,
    sharedViewModel: SharedViewModel,
    bottomNavigationViewModel: BottomNavigationViewModel,
    dataStoreViewModel: DataStoreViewModel
) {
//    val watchListState = dataStoreViewModel.watchList
    val watchListState = watchListViewModel.watchList
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    var selecteditemIndex by rememberSaveable {
        mutableStateOf(0)
    }
    val items = listOf(
        BottomNavigationItem(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            hasNews = false

        ) {

            bottomNavigationViewModel.setSelecteditemindexed(0)
            navController.navigate(Movie_enum.HomeScreen.name)
        },
//        BottomNavigationItem(
//            title = "Search",
//            selectedIcon = Icons.Filled.Search,
//            unselectedIcon = Icons.Outlined.Search,
//            hasNews = false
//
//        ) {
//            bottomNavigationViewModel.setSelecteditemindexed(1)
//            navController.navigate(Movie_enum.SearchScreen.name)
//        },
        BottomNavigationItem(
            title = "Watchlist",
            selectedIcon = Icons.Filled.Favorite,
            unselectedIcon = Icons.Outlined.FavoriteBorder,
            hasNews = false

        ) {
            bottomNavigationViewModel.setSelecteditemindexed(2)
            navController.navigate(Movie_enum.WatchListScreen.name)
        },
    )

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { MovieTopAppBar(scrollBehavior = scrollBehavior, name = "WatchList") },
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = bottomNavigationViewModel.getSelecteditemindexed() == index,
                        onClick = item.OnClick,
                        label = {
                            Text(text = item.title)
                        },
                        icon = {
                            BadgedBox(
                                badge = {
                                    if (item.badgeCount != null) {
                                        Badge {
                                            Text(text = item.badgeCount.toString())
                                        }
                                    }
                                }) {
                                Icon(
                                    imageVector = if (index == bottomNavigationViewModel.getSelecteditemindexed()) {
                                        item.selectedIcon

                                    } else item.unselectedIcon,
                                    contentDescription = item.title
                                )
                            }
                        })

                }
            }
        }
    ) {
        val watchList = watchListState.value
        if (watchList != null) {
            if (watchList.isEmpty()) {
                Box(modifier =Modifier
                    .fillMaxSize(),
                    contentAlignment = Alignment.Center
                )
                {
                    Text(text = "  Your WatchList\n\n       is Empty",
                        style = TextStyle(
                            fontSize = 30.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            } else {
                Spacer(modifier = Modifier.height(50.dp))

                LazyVerticalGrid(
                    columns = GridCells.Adaptive(150.dp),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(20.dp)
                ) {

                    items(watchList) { movie ->
                        Column(
                            modifier = Modifier
                                .padding(10.dp)
                        ) {
                            MovieCard(movie = movie) { movie ->
                                sharedViewModel.addMovie(movie)
                                navController.navigate(Movie_enum.DetailsScreen.name)
                            }

                        }

                    }
                }

            }
        }

    }
}

