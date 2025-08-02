package org.arexdev.rickmortyapp.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class OriginResponse(
    val name: String,
    val url: String
)