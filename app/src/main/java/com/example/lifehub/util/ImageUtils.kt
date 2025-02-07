package com.example.lifehub.util

import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import coil.compose.rememberAsyncImagePainter
import com.example.lifehub.theme.LifeHubTheme
import com.example.lifehub.theme.spacing.Spacing

@Composable
fun DynamicImage(image: String?, placeholderText: String) {
    if (image == null) {
        PlaceholderImage(placeholderText)
    } else {
        Image(
            painter = rememberAsyncImagePainter(image),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .aspectRatio(16f / 9f)
                .clip(LifeHubTheme.shapes.medium)
        )
    }
}

@Composable
fun PlaceholderImage(text: String?) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
            .clip(LifeHubTheme.shapes.medium)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.8f),
                        MaterialTheme.colorScheme.secondary
                    ),
                    start = Offset(0f, Float.POSITIVE_INFINITY),
                    end = Offset(Float.POSITIVE_INFINITY, 0f)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        text?.let { placeholderText ->
            Text(
                text = placeholderText,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = LifeHubTheme.spacing.inset.medium, bottom = Spacing.spacing12),
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xD1D5DB)
@Composable
private fun DynamicImagePreview(@PreviewParameter(DynamicImageProvider::class) data: String) {
    LifeHubTheme {
        DynamicImage(image = data, placeholderText = "Event")
    }
}

class DynamicImageProvider : PreviewParameterProvider<Image?> {
    override val values = sequenceOf(
        null,
        null
    )
}