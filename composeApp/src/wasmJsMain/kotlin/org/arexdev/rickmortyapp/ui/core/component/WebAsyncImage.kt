package org.arexdev.rickmortyapp.ui.core.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.layout.ContentScale
import org.arexdev.rickmortyapp.di.Provider.getCharacterImage
import org.jetbrains.skia.Image as SkiaImage


@Composable
fun WebAsyncImage(
    url: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit
) {
    var isLoading by remember { mutableStateOf(true) }
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    getCharacterImage

    // Cargar la imagen
    LaunchedEffect(url) {
        imageBitmap = loadWebImage(url)
        isLoading = false
    }

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        if (isLoading) {
            CircularProgressIndicator()
        } else if (imageBitmap != null) {
            Image(
                painter = BitmapPainter(imageBitmap!!),
                contentDescription = contentDescription,
                contentScale = contentScale,
                modifier = Modifier.matchParentSize()
            )
        }
    }
}

suspend fun loadWebImage(url: String): ImageBitmap {
    val imageModel = getCharacterImage.invoke(url)
    val skiaImage = SkiaImage.makeFromEncoded(imageModel.imageData)
    return skiaImage.toComposeImageBitmap()
}