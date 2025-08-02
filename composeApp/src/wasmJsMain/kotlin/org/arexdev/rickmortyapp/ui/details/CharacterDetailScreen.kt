package org.arexdev.rickmortyapp.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.arexdev.rickmortyapp.di.Provider
import org.arexdev.rickmortyapp.domain.model.CharacterModel
import org.arexdev.rickmortyapp.domain.model.EpisodeModel
import org.arexdev.rickmortyapp.ui.core.BackgroundPrimaryColor
import org.arexdev.rickmortyapp.ui.core.BackgroundSecondaryColor
import org.arexdev.rickmortyapp.ui.core.BackgroundTertiaryColor
import org.arexdev.rickmortyapp.ui.core.DefaultTextColor
import org.arexdev.rickmortyapp.ui.core.Green
import org.arexdev.rickmortyapp.ui.core.Pink
import org.arexdev.rickmortyapp.ui.core.component.TextTitle
import org.arexdev.rickmortyapp.ui.core.component.WebAsyncImage
import org.arexdev.rickmortyapp.ui.core.ex.aliveBorder
import org.jetbrains.compose.resources.painterResource
import rickmortyweb.composeapp.generated.resources.Res
import rickmortyweb.composeapp.generated.resources.space

@Composable
fun CharacterDetailScreen(characterModel: CharacterModel) {


    val viewModel = remember { Provider.provideCharacterDetailViewModel(characterModel) }
    val state by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.fillMaxSize().background(BackgroundPrimaryColor).verticalScroll(scrollState)) {
        MainHeader(state.characterModel)
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier.fillMaxSize()
                .clip(RoundedCornerShape(topStartPercent = 2, topEndPercent = 2))
                .background(BackgroundSecondaryColor)
        )
        {
            CharacterInformation(state.characterModel)
            CharacterEpisodesList(state.episodes)
        }
    }

}

@Composable
fun CharacterEpisodesList(episodes: List<EpisodeModel>?) {
    ElevatedCard(
        modifier = Modifier.padding(16.dp).fillMaxWidth(),
        colors = CardDefaults.elevatedCardColors(containerColor = BackgroundTertiaryColor)
    ) {
        Box(modifier = Modifier.padding(16.dp), contentAlignment = Alignment.Center) {
            if (episodes == null) {
                CircularProgressIndicator(color = Green)
            } else {
                Column {
                    TextTitle("Episode List")
                    Spacer(modifier = Modifier.height(6.dp))
                    episodes.forEach { episode ->
                        EpisodeItem(episode)
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }

        }
    }
}

@Composable
fun EpisodeItem(episode: EpisodeModel) {

    Text(episode.name, color = Green, fontWeight = FontWeight.Bold)
    Text(episode.episode, color = DefaultTextColor)

}

@Composable
fun CharacterInformation(characterModel: CharacterModel) {
    ElevatedCard(
        modifier = Modifier.padding(16.dp).fillMaxWidth(),
        colors = CardDefaults.elevatedCardColors(containerColor = BackgroundTertiaryColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            TextTitle("About the character")
            Spacer(modifier = Modifier.height(6.dp))
            InformationDetail("Origin", characterModel.origin)
            Spacer(modifier = Modifier.height(2.dp))
            InformationDetail("Gender", characterModel.gender)
        }
    }
}

@Composable
fun InformationDetail(title: String, detail: String) {
    Row {
        Text(title, color = Color.Black, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.width(4.dp))
        Text(detail, color = Green, fontWeight = FontWeight.Bold)
    }
}


@Composable
fun MainHeader(characterModel: CharacterModel) {
    Box(modifier = Modifier.fillMaxWidth().height(300.dp)) {
        Image(
            painter = painterResource(Res.drawable.space),
            "",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        CharacterHeader(characterModel)
    }
}

@Composable
fun CharacterHeader(characterModel: CharacterModel) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .clip(RoundedCornerShape(topStartPercent = 10, topEndPercent = 10))
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(characterModel.name, color = Pink, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(
                "Species: ${characterModel.species}",
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(Modifier.height(16.dp))
            Box(contentAlignment = Alignment.TopCenter) {
                Box(
                    modifier = Modifier.size(205.dp).clip(CircleShape).background(Color.Black.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    WebAsyncImage(
                        url = characterModel.image,
                        contentDescription = null,
                        modifier = Modifier.size(190.dp).clip(CircleShape).aliveBorder(characterModel.isAlive),
                        contentScale = ContentScale.Crop
                    )
                }
                val aliveCopy = if (characterModel.isAlive) "ALIVE" else "DEAD"
                val aliveColor = if (characterModel.isAlive) Green else Color.Red
                Text(
                    aliveCopy, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold,
                    modifier = Modifier.clip(RoundedCornerShape(30)).background(aliveColor)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
            Spacer(Modifier.weight(1f))
        }

    }
}