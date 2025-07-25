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
            SeasonEpisode.SEASON_1 -> "https://firebasestorage.googleapis.com/v0/b/rickmortykmp-393fc.firebasestorage.app/o/RICK%20Y%20MORTY%20(Trailer%20espan%CC%83ol).mp4?alt=media&token=94f133c4-5136-424c-9ff6-2f715b212e47"
            SeasonEpisode.SEASON_2 -> "https://firebasestorage.googleapis.com/v0/b/rickmortykmp-393fc.firebasestorage.app/o/RICK%20Y%20MORTY%20(Trailer%20espan%CC%83ol).mp4?alt=media&token=94f133c4-5136-424c-9ff6-2f715b212e47"
            SeasonEpisode.SEASON_3 -> "https://firebasestorage.googleapis.com/v0/b/rickmortykmp-393fc.firebasestorage.app/o/RICK%20Y%20MORTY%20(Trailer%20espan%CC%83ol).mp4?alt=media&token=94f133c4-5136-424c-9ff6-2f715b212e47"
            SeasonEpisode.SEASON_4 -> "https://firebasestorage.googleapis.com/v0/b/rickmortykmp-393fc.firebasestorage.app/o/RICK%20Y%20MORTY%20(Trailer%20espan%CC%83ol).mp4?alt=media&token=94f133c4-5136-424c-9ff6-2f715b212e47"
            SeasonEpisode.SEASON_5 -> "https://firebasestorage.googleapis.com/v0/b/rickmortykmp-393fc.firebasestorage.app/o/RICK%20Y%20MORTY%20(Trailer%20espan%CC%83ol).mp4?alt=media&token=94f133c4-5136-424c-9ff6-2f715b212e47"
            SeasonEpisode.SEASON_6 -> "https://firebasestorage.googleapis.com/v0/b/rickmortykmp-393fc.firebasestorage.app/o/RICK%20Y%20MORTY%20(Trailer%20espan%CC%83ol).mp4?alt=media&token=94f133c4-5136-424c-9ff6-2f715b212e47"
            SeasonEpisode.SEASON_7 -> "https://firebasestorage.googleapis.com/v0/b/rickmortykmp-393fc.firebasestorage.app/o/RICK%20Y%20MORTY%20(Trailer%20espan%CC%83ol).mp4?alt=media&token=94f133c4-5136-424c-9ff6-2f715b212e47"
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