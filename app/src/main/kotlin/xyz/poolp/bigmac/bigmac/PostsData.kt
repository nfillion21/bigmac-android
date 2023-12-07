@file:Suppress("ktlint:max-line-length") // String constants read better
package xyz.poolp.bigmac.bigmac

import xyz.poolp.bigmac.R

/**
 * Define hardcoded posts to avoid handling any non-ui operations.
 */


val post1 = Post(
    id = "dc523f0ed25c",
    title = "2 Av. de la Libération, 60260 Lamorlaye, France",
    subtitle = "How to configure your module paths, instead of using Gradle’s default.",
    url = "https://medium.com/androiddevelopers/gradle-path-configuration-dc523f0ed25c",
    metadata = Metadata(
        readTimeMinutes = 1
    ),
    imageId = R.drawable.post_1,
    imageThumbId = R.drawable.mcdonalds_logo
)

val post2 = Post(
    id = "7446d8dfd7dc",
    title = "2 Av. de la Libération, 60260 Lamorlaye, France",
    subtitle = "Use Dagger in Kotlin! This article includes best practices to optimize your build time and gotchas you might encounter.",
    url = "https://medium.com/androiddevelopers/dagger-in-kotlin-gotchas-and-optimizations-7446d8dfd7dc",
    metadata = Metadata(
        readTimeMinutes = 3
    ),
    imageId = R.drawable.post_2,
    imageThumbId = R.drawable.mcdonalds_logo
)

val post3 = Post(
    id = "ac552dcc1741",
    title = "2 Av. de la Libération, 60260 Lamorlaye, France",
    subtitle = "Learn how to get started converting Java Programming Language code to Kotlin, making it more idiomatic and avoid common pitfalls, by…",
    url = "https://medium.com/androiddevelopers/from-java-programming-language-to-kotlin-the-idiomatic-way-ac552dcc1741",
    metadata = Metadata(
        readTimeMinutes = 1
    ),
    imageId = R.drawable.post_3,
    imageThumbId = R.drawable.mcdonalds_logo
)

val post4 = Post(
    id = "84eb677660d9",
    title = "2 Av. de la Libération, 60260 Lamorlaye, France",
    subtitle = "TL;DR: Expose resource IDs from ViewModels to avoid showing obsolete data.",
    url = "https://medium.com/androiddevelopers/locale-changes-and-the-androidviewmodel-antipattern-84eb677660d9",
    metadata = Metadata(
        readTimeMinutes = 1
    ),
    imageId = R.drawable.current_mcdonalds,
    imageThumbId = R.drawable.mcdonalds_logo
)

val post5 = Post(
    id = "55db18283aca",
    title = "2 Av. de la Libération, 60260 Lamorlaye, France",
    subtitle = "Working with collections is a common task and the Kotlin Standard Library offers many great utility functions. It also offers two ways of…",
    url = "https://medium.com/androiddevelopers/collections-and-sequences-in-kotlin-55db18283aca",
    metadata = Metadata(
        readTimeMinutes = 4
    ),
    imageId = R.drawable.post_5,
    imageThumbId = R.drawable.mcdonalds_logo
)

val posts: PostsFeed =
    PostsFeed(
        highlightedPost = post4,
        recentPosts = listOf(post1, post2, post3, post4, post5),
        popularPosts = listOf(
            post5,
            post1.copy(id = "post6"),
        ),
        recommendedPosts = listOf(
            post3.copy(id = "post8"),
            post4.copy(id = "post9"),
            post5.copy(id = "post10")
        )
    )
