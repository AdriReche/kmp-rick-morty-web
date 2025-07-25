package org.arexdev.rickmortyapp.ui.home.tabs.episodes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import kotlinx.browser.document
import org.arexdev.rickmortyapp.di.Provider
import org.arexdev.rickmortyapp.domain.model.EpisodeModel
import org.arexdev.rickmortyapp.domain.model.SeasonEpisode
import org.arexdev.rickmortyapp.ui.core.component.HtmlView
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import rickmortyweb.composeapp.generated.resources.Res
import rickmortyweb.composeapp.generated.resources.portal
import rickmortyweb.composeapp.generated.resources.season1
import rickmortyweb.composeapp.generated.resources.season2
import rickmortyweb.composeapp.generated.resources.season3
import rickmortyweb.composeapp.generated.resources.season4
import rickmortyweb.composeapp.generated.resources.season5
import rickmortyweb.composeapp.generated.resources.season6
import rickmortyweb.composeapp.generated.resources.season7

@Composable
fun EpisodesScreen() {
    val episodesViewModel = remember { Provider.episodesViewModel }
    val state by episodesViewModel.state.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EpisodesScreenPag(episodesViewModel)
        HideVideoButton(playVideo = state.playVideo, onCloseVideo = { episodesViewModel.onCloseVideo() })
        EpisodePlayer(playVideo = state.playVideo)

    }
}

@Composable
fun HideVideoButton(playVideo: String, onCloseVideo: () -> Unit) {
    AnimatedVisibility(playVideo.isNotBlank()) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(Res.drawable.portal),
                contentDescription = "",
                modifier = Modifier
                    .padding(8.dp)
                    .size(100.dp)
                    .clickable { onCloseVideo() }
            )
        }
    }
}

@Composable
fun EpisodePlayer(playVideo: String) {
    AnimatedVisibility(playVideo.isNotBlank()) {
        ElevatedCard(
            modifier = Modifier.fillMaxWidth(0.75f).fillMaxHeight(0.75f).padding(16.dp)
                .border(3.dp, Color.Green, CardDefaults.elevatedShape)
        ) {
            Box(modifier = Modifier.background(Color.Black).fillMaxSize()) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(16.dp)) {
                    HtmlView(
                        modifier = Modifier.fillMaxSize(),
                        factory = {
                            val video = document.createElement("video")
                            video.setAttribute("controls", "true")
                            video.setAttribute("autoplay", "true")
                            video.setAttribute(
                                "src",
                                playVideo
                            )
                            video
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun EpisodesScreenPag(episodesViewModel: EpisodesViewModel) {
    val state by episodesViewModel.state.collectAsState()
    LazyRow {

        items(state.episodes.items.size) { index ->
            if (index >= state.episodes.items.size - 5 && !state.episodes.loading && !state.episodes.endReached) {
                episodesViewModel.loadPaginationNext()
            }
            EpisodeItemList(state.episodes.items[index], { url -> episodesViewModel.onPlaySelected(url) })
        }

        item {
            if (state.episodes.loading) {
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

@Composable
fun EpisodeItemList(episodeModel: EpisodeModel, onEpisodeSelected: (String) -> Unit) {
    Column(Modifier.width(200.dp).padding(8.dp).clickable { onEpisodeSelected(episodeModel.videoUrl) }) {
        Image(
            modifier = Modifier.height(300.dp).fillMaxWidth(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            painter = painterResource(getSeasonImage(episodeModel.season)),
        )
    }
}

fun getSeasonImage(seasonEpisode: SeasonEpisode): DrawableResource {
    return when (seasonEpisode) {
        SeasonEpisode.SEASON_1 -> Res.drawable.season1
        SeasonEpisode.SEASON_2 -> Res.drawable.season2
        SeasonEpisode.SEASON_3 -> Res.drawable.season3
        SeasonEpisode.SEASON_4 -> Res.drawable.season4
        SeasonEpisode.SEASON_5 -> Res.drawable.season5
        SeasonEpisode.SEASON_6 -> Res.drawable.season6
        SeasonEpisode.SEASON_7 -> Res.drawable.season7
        SeasonEpisode.UNKNOWN -> Res.drawable.season1
    }
}
