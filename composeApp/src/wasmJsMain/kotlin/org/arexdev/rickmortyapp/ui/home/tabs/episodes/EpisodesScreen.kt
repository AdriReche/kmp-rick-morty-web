package org.arexdev.rickmortyapp.ui.home.tabs.episodes

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.arexdev.rickmortyapp.di.Provider
import org.arexdev.rickmortyapp.domain.model.EpisodeModel
import org.arexdev.rickmortyapp.domain.model.SeasonEpisode
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import rickmortyweb.composeapp.generated.resources.Res
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
    EpisodesScreenPag(episodesViewModel)
}

@Composable
fun EpisodesScreenPag(viewModel: EpisodesViewModel) {
    val state by viewModel.state.collectAsState()
    LazyRow {

        items(state.episodes.items.size) { index ->
            if (index >= state.episodes.items.size - 5 && !state.episodes.loading && !state.episodes.endReached) {
                viewModel.loadPaginationNext()
            }
            EpisodeItemList(state.episodes.items[index])
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
fun EpisodeItemList(episodeModel: EpisodeModel) {
    Column(Modifier.width(300.dp).padding(8.dp).clickable {}) {
        Image(
            modifier = Modifier.height(400.dp).fillMaxWidth(),
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
