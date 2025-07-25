package com.lifestyle.rickandmorty.presentation

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.lifestyle.rickandmorty.domain.model.Character


@Composable
fun CharacterScreen(modifier: Modifier = Modifier, viewModel: CharacterViewModel) {
    val characterItems = viewModel.characters.collectAsLazyPagingItems()


    if (characterItems.loadState.refresh is LoadState.NotLoading) {
        if (characterItems.itemCount == 0) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Nothing Found")
            }
        }
    }

    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        if (characterItems.loadState.prepend is LoadState.Loading) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
        if (characterItems.loadState.prepend is LoadState.Error) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            characterItems.retry()
                        }
                    ) {
                        Text("Retry")
                    }
                }
            }
        }

        if (characterItems.loadState.refresh is LoadState.NotLoading) {
            if (characterItems.itemCount != 0) {
                items(
                    count = characterItems.itemCount,
                    key = characterItems.itemKey { it.uuid },
                    contentType = characterItems.itemContentType { "contentType" }) { index ->
                    characterItems[index]?.let {
                        CharacterItem(it)
                    }
                }
            }
        }

        if (characterItems.loadState.append is LoadState.Loading) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }

        if (characterItems.loadState.append is LoadState.Error) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            characterItems.retry()
                        }
                    ) {
                        Text("Retry")
                    }
                }
            }
        }
    }
}


@Composable
fun CharacterItem(item: Character) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            Log.d("ImageURL", item.imageUrl)
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.imageUrl)
                    .crossfade(true)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .build(),
                contentDescription = item.name,
                modifier = Modifier
                    .size(64.dp)
                    .padding(2.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,

                )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = item.name, style = MaterialTheme.typography.titleMedium)
                Text(text = item.species, style = MaterialTheme.typography.titleMedium)
                Text(text = item.status, style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}