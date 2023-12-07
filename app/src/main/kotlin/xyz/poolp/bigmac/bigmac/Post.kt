package xyz.poolp.bigmac.bigmac

import androidx.annotation.DrawableRes

data class Post(
    val id: String,
    val title: String,
    val subtitle: String? = null,
    val url: String,
    val metadata: Metadata,
    @DrawableRes val imageId: Int,
    @DrawableRes val imageThumbId: Int
)

data class Metadata(
    val readTimeMinutes: Int
)

data class Publication(
    val name: String,
    val logoUrl: String
)

data class Paragraph(
    val type: ParagraphType,
    val text: String,
    val markups: List<Markup> = emptyList()
)

data class Markup(
    val type: MarkupType,
    val start: Int,
    val end: Int,
    val href: String? = null
)

enum class MarkupType {
    Link,
    Code,
    Italic,
    Bold,
}

enum class ParagraphType {
    Title,
    Caption,
    Header,
    Subhead,
    Text,
    CodeBlock,
    Quote,
    Bullet,
}
