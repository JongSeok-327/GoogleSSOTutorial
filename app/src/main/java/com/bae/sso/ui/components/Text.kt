package com.bae.sso.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bae.sso.ui.theme.SSOTheme

@Composable
fun ExpandableText(
    text: String,
    maxLine: Int = 30
) {
   var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        // Main Text
        Text(
            text = text,
            modifier = Modifier.clickable { expanded = !expanded },
            maxLines = if (expanded) maxLine else 3,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodySmall
        )

        // Read More / Read Less toggle
        Text(
            text = if (expanded) "Read less" else "Read more",
            modifier = Modifier
                .padding(top = 4.dp)
                .clickable { expanded = !expanded },
            color = Color.Blue,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
        )
    }
}

@Preview
@Composable
private fun ExpandableTextPreview() {
    val longText = """
        This is a very long text designed to test the expandable text feature. 
        It contains multiple lines of content to ensure that the preview shows the correct functionality. 
        The text should initially display only three lines and allow the user to click 'Read more' to see the rest of the content. 
        If the user clicks 'Read less', the text should collapse back to three lines. 
        This helps verify that the feature works as intended in a real-world scenario.
    """.trimIndent()

    SSOTheme {
        ExpandableText(
            text = longText
        )
    }
}