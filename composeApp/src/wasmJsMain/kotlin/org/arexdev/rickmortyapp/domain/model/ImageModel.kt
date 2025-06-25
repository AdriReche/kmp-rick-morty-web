package org.arexdev.rickmortyapp.domain.model

data class ImageModel(
    val imageData: ByteArray,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as ImageModel

        if (!imageData.contentEquals(other.imageData)) return false

        return true
    }

    override fun hashCode(): Int {
        return imageData.contentHashCode()
    }
}