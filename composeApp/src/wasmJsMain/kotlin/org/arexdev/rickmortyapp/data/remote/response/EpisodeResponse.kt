package org.arexdev.rickmortyapp.data.remote.response

import kotlinx.serialization.Serializable
import org.arexdev.rickmortyapp.domain.model.EpisodeModel
import org.arexdev.rickmortyapp.domain.model.SeasonEpisode

@Serializable
data class EpisodeResponse(
    val id: Int,
    val name: String,
    val episode: String,
    val characters: List<String>
) {
    fun toDomainModel(): EpisodeModel {
        val season = getSeasonFromEpisodeCode(episode)
        return EpisodeModel(
            id = id,
            name = name,
            episode = episode,
            characters = characters.map { url -> url.substringAfterLast("/") },
            videoUrl = getVideoUrlFromSeason(season),
            season = season
        )
    }

    private fun getVideoUrlFromSeason(season: SeasonEpisode): String {
        return when (season) {
            SeasonEpisode.SEASON_1 -> "https://www.youtube.com/watch?v=8BEzj2kRjO8&ab_channel=RottenTomatoesTV"
            SeasonEpisode.SEASON_2 -> "https://www.youtube.com/watch?v=SXwf_9xJu5c&ab_channel=Yusuto"
            SeasonEpisode.SEASON_3 -> "https://www.youtube.com/watch?v=Bmg2vXOQ3kM&ab_channel=SeriesTrailerMP"
            SeasonEpisode.SEASON_4 -> "https://www.youtube.com/watch?v=bLI2-v264No&ab_channel=RottenTomatoesTV"
            SeasonEpisode.SEASON_5 -> "https://www.youtube.com/watch?v=yC1UxW8vcDo&ab_channel=RottenTomatoesTV"
            SeasonEpisode.SEASON_6 -> "https://www.youtube.com/watch?v=jerFRSQW9g8&ab_channel=RottenTomatoesTV"
            SeasonEpisode.SEASON_7 -> "https://www.youtube.com/watch?v=PkZtVBNkmso&ab_channel=RottenTomatoesTV"
            else -> "https://www.youtube.com/watch?v=unknown"
        }
    }

    private fun getSeasonFromEpisodeCode(episode: String): SeasonEpisode {
        return when {
            episode.startsWith("S01") -> SeasonEpisode.SEASON_1
            episode.startsWith("S02") -> SeasonEpisode.SEASON_2
            episode.startsWith("S03") -> SeasonEpisode.SEASON_3
            episode.startsWith("S04") -> SeasonEpisode.SEASON_4
            episode.startsWith("S05") -> SeasonEpisode.SEASON_5
            episode.startsWith("S06") -> SeasonEpisode.SEASON_6
            episode.startsWith("S07") -> SeasonEpisode.SEASON_7
            else -> SeasonEpisode.UNKNOWN
        }
    }
}