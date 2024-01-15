package com.example.movielister.ui.theme.screens

import android.annotation.SuppressLint
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavController
import com.example.movielister.navigation.Movie_enum
import com.example.movielister.navigation.SharedViewModel
import com.example.movielister.components.MovieTopAppBar
import com.example.movielister.components.SearchBarM3
import com.example.movielister.viewmodel.BottomNavigationViewModel
import com.example.movielister.viewmodel.WatchListViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController,
    watchListViewModel: WatchListViewModel,
    sharedViewModel: SharedViewModel,
    bottomNavigationViewModel: BottomNavigationViewModel
) {
    val watchListState = watchListViewModel.watchList
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
//    var selecteditemIndex by rememberSaveable {
//        mutableStateOf(0)
//    }
    val items= listOf(
        BottomNavigationItem(
            title="Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            hasNews = false

        ){

            bottomNavigationViewModel.setSelecteditemindexed(0)
            navController.navigate(Movie_enum.HomeScreen.name )
        },
        BottomNavigationItem(
            title="Search",
            selectedIcon = Icons.Filled.Search,
            unselectedIcon = Icons.Outlined.Search,
            hasNews = false

        ){
            bottomNavigationViewModel.setSelecteditemindexed(1)
            navController.navigate(Movie_enum.SearchScreen.name )
        },
        BottomNavigationItem(
            title="Watchlist",
            selectedIcon = Icons.Filled.Favorite,
            unselectedIcon = Icons.Outlined.FavoriteBorder,
            hasNews = false

        ){
            bottomNavigationViewModel.setSelecteditemindexed(2)
            navController.navigate(Movie_enum.WatchListScreen.name )
        },
    )

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { MovieTopAppBar(scrollBehavior = scrollBehavior,name="Search Movie") },
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

        SearchBarM3()


    }

}