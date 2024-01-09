package com.example.movielister.ui.theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.movielister.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieTopAppBar(scrollBehavior: TopAppBarScrollBehavior, modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        modifier = modifier
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DetailScreenTopAppBar(navController: NavController,MovieName:String,scrollBehavior: TopAppBarScrollBehavior, modifier: Modifier = Modifier) {
    TopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = MovieName,
                style = MaterialTheme.typography.headlineSmall,
                fontSize = 20.sp
            )
        },
        navigationIcon = {
            IconButton(onClick = {  navController.popBackStack() } ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription ="Toggle drawer" )
            }
        },
        modifier = modifier
    )
}