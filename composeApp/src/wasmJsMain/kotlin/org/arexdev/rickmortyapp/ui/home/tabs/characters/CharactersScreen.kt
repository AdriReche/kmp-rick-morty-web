package org.arexdev.rickmortyapp.ui.home.tabs.characters

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.arexdev.rickmortyapp.di.Provider
import org.arexdev.rickmortyapp.domain.model.CharacterModel
import org.arexdev.rickmortyapp.ui.core.BackgroundPrimaryColor
import org.arexdev.rickmortyapp.ui.core.DefaultTextColor
import org.arexdev.rickmortyapp.ui.core.Green
import org.arexdev.rickmortyapp.ui.core.component.WebAsyncImage
import org.arexdev.rickmortyapp.ui.core.ex.vertical
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun CharactersScreen(navigateToDetail: (CharacterModel) -> Unit) {
    val charactersViewModel = remember { Provider.charactersViewModel }
    val state by charactersViewModel.state.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().background(BackgroundPrimaryColor),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Characters", fontSize = 24.sp, color = DefaultTextColor, fontWeight = FontWeight.SemiBold)
            CharacterOfTheDay(state.characterOfTheDay)
        }
        Spacer(modifier = Modifier.height(24.dp))
        CharactersScreenPag(charactersViewModel, navigateToDetail)
    }
}

@Composable
fun CharacterOfTheDay(characterModel: CharacterModel? = null) {

    Card(
        modifier = Modifier.size(500.dp),
        shape = RoundedCornerShape(12)
    ) {
        if (characterModel == null) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(color = Green)
            }
        } else {
            Box(contentAlignment = Alignment.Center) {
                WebAsyncImage(
                    url = characterModel.image,
                    contentDescription = "Character of the day",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                Box(
                    Modifier.fillMaxSize().background(
                        Brush.horizontalGradient(
                            0f to Color.Black.copy(alpha = 0.9f),
                            0.4f to Color.White.copy(alpha = 0f),
                            1f to Color.White.copy(alpha = 0f)
                        )
                    )
                )
                Text(
                    characterModel.name,
                    fontSize = 40.sp,
                    maxLines = 1,
                    minLines = 1,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(horizontal = 24.dp, vertical = 16.dp)
                        .fillMaxHeight()
                        .vertical()
                        .rotate(-90f)
                )
            }
        }

    }
}

@Composable
fun CharactersScreenPag(viewModel: CharactersViewModel, navigateToDetail: (CharacterModel) -> Unit) {
    val state by viewModel.state.collectAsState()

    Box {
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
            columns = GridCells.Fixed(5),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            items(state.characters.items.size) { index ->
                if (index >= state.characters.items.size - 5 && !state.characters.loading && !state.characters.endReached) {
                    viewModel.loadPaginationNext()
                }
                CharacterItemList(state.characters.items[index]) { character ->
                    navigateToDetail(character)
                }
            }

            item(span = { GridItemSpan(5) }) {
                if (state.characters.loading) {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@Composable
fun CharacterItemList(characterModel: CharacterModel, onItemSelected: (CharacterModel) -> Unit) {
    Box(
        modifier = Modifier
            .size(200.dp)
            .clip(RoundedCornerShape(24))
            .border(2.dp, Green, shape = RoundedCornerShape(0, 24, 0, 24)).fillMaxSize()
            .clickable {
                onItemSelected(characterModel)
            },
        contentAlignment = Alignment.BottomCenter
    ) {
        WebAsyncImage(
            url = characterModel.image,
            contentDescription = "Character of the day",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            Modifier.fillMaxWidth().height(40.dp).background(
                brush = Brush.verticalGradient(
                    listOf(
                        Color.Black.copy(0f),
                        Color.Black.copy(0.6f),
                        Color.Black.copy(1f),
                    )
                )
            ), contentAlignment = Alignment.Center
        ) {
            Text(characterModel.name, color = Color.White, fontSize = 18.sp)
        }
    }
}