package com.lifestyle.rickandmorty.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.lifestyle.rickandmorty.presentation.ui.theme.RickAndMortyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel = hiltViewModel<CharacterViewModel>()
            var name by rememberSaveable { mutableStateOf("") }
            RickAndMortyTheme {
                Scaffold(
                    topBar = {
                        TextField(
                            value = name,
                            onValueChange = {
                                name = it
                                viewModel.updateFilters(
                                    CharacterFilterState(name = name)
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    modifier = Modifier
                        .safeContentPadding()
                        .fillMaxSize()
                ) { innerPadding ->
                    CharacterScreen(modifier = Modifier.padding(innerPadding), viewModel)
                }
            }
        }
    }
}