package xyz.poolp.bigmac.bigmac

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import xyz.poolp.bigmac.R
import xyz.poolp.bigmac.util.supportWideScreen
import xyz.poolp.bigmac.viewmodels.BigMacScreenData
import xyz.poolp.bigmac.viewmodels.BigMacViewModel
import xyz.poolp.core.domain.McDonalds

private const val CONTENT_ANIMATION_DURATION = 300

@OptIn(ExperimentalMaterial3Api::class)
// Scaffold is experimental in m3
@Composable
fun BigMacScreen(
    bigMacViewModel: BigMacViewModel,
    bigMacScreenData: BigMacScreenData,
    onClosePressed: () -> Unit,
    onPreviousPressed: () -> Unit,
    onMcDoPressed: (String) -> Unit,
) {
    val scope = rememberCoroutineScope()

    Surface(modifier = Modifier.supportWideScreen()) {
        val topAppBarState = rememberTopAppBarState()
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)
        Scaffold(
            topBar = {
                BigMacAppBar(
                    step = bigMacScreenData.step,
                    title = "Lamorlaye",
                    scrollBehavior = scrollBehavior,
                    onClosePressed = onClosePressed,
                    onBackPressed = onPreviousPressed,
                    shouldShowPreviousButton = bigMacScreenData.shouldShowPreviousButton
                )
            },
        )
        { innerPadding ->
            AnimatedContent(
                targetState = bigMacScreenData,
                transitionSpec = {
                    val animationSpec: TweenSpec<IntOffset> = tween(CONTENT_ANIMATION_DURATION)

                    val direction = getTransitionDirection(
                        initialIndex = initialState.step,
                        targetIndex = targetState.step,
                    )

                    slideIntoContainer(
                        towards = direction,
                        animationSpec = animationSpec,
                    ) togetherWith slideOutOfContainer(
                        towards = direction,
                        animationSpec = animationSpec
                    )
                },
                label = "bigMacScreenDataAnimation"
            ) { targetState ->

                when (targetState.step) {
                    1 -> {
                        McDonaldsList(
                            mcdonalds = listOf(
                                McDonalds(
                                    identifier = "1",
                                    formattedAddress = "formattedAddress",
                                    shortFormattedAddress = "shortFormattedAddress",
                                    latitude = 3.44,
                                    longitude = 4.33,
                                    locality = "Paris"
                                ),
                                McDonalds(
                                    identifier = "2",
                                    formattedAddress = "formattedAddress",
                                    shortFormattedAddress = "shortFormattedAddress",
                                    latitude = 3.44,
                                    longitude = 4.33,
                                    locality = "Paris"
                                ),
                            ),
                            onMcDonaldsPressed = {
                                scope.launch {
                                    bigMacViewModel.onBigMacPressed()
                                }
                            },
                            modifier = Modifier
                                .nestedScroll(scrollBehavior.nestedScrollConnection)
                                .padding(innerPadding)
                        )
                    }

                    else -> {
                        McDonaldsList(
                            mcdonalds = listOf(
                                McDonalds(
                                    identifier = "1",
                                    formattedAddress = "formattedAddress",
                                    shortFormattedAddress = "shortFormattedAddress",
                                    latitude = 3.44,
                                    longitude = 4.33,
                                    locality = "Paris"
                                ),
                                McDonalds(
                                    identifier = "2",
                                    formattedAddress = "formattedAddress",
                                    shortFormattedAddress = "shortFormattedAddress",
                                    latitude = 3.44,
                                    longitude = 4.33,
                                    locality = "Paris"
                                ),
                            ),
                            onMcDonaldsPressed = onMcDoPressed,
                            modifier = Modifier
                                .nestedScroll(scrollBehavior.nestedScrollConnection)
                                .padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}

private fun getTransitionDirection(
    initialIndex: Int,
    targetIndex: Int
): AnimatedContentTransitionScope.SlideDirection {
    return if (targetIndex > initialIndex) {
        // Going forwards in the road: Set the initial offset to start
        // at the size of the content so it slides in from right to left, and
        // slides out from the left of the screen to -fullWidth
        AnimatedContentTransitionScope.SlideDirection.Left
    } else {
        // Going back to the previous mcdo in the set, we do the same
        // transition as above, but with different offsets - the inverse of
        // above, negative fullWidth to enter, and fullWidth to exit.
        AnimatedContentTransitionScope.SlideDirection.Right
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BigMacAppBar(
    step: Int,
    title: String,
    shouldShowPreviousButton: Boolean,
    scrollBehavior: TopAppBarScrollBehavior?,
    onClosePressed: () -> Unit,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Row {
                Image(
                    painter = painterResource(id = R.drawable.mcdonalds_logo),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(RoundedCornerShape(percent = 15))
                        .size(36.dp)
                )
                Text(
                    text = stringResource(R.string.mcdonalds_in, step + 1, title),
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        },
        navigationIcon = {
            if (shouldShowPreviousButton) {
                IconButton(onClick = onBackPressed) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.cd_navigate_up),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        },
        actions = {
            IconButton(
                onClick = onClosePressed,
                modifier = Modifier.padding(4.dp)
            ) {
                Icon(
                    Icons.Filled.Close,
                    contentDescription = stringResource(id = R.string.close),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        scrollBehavior = scrollBehavior,
        modifier = modifier
    )
}

@Composable
fun McDonaldsList(
    mcdonalds: List<McDonalds>,
    onMcDonaldsPressed: (postId: String) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    state: LazyListState = rememberLazyListState(),
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        state = state
    ) {
        item {
            McDonaldsListTopSection(
                mcdonalds.first(),
                onMcDonaldsPressed
            )
        }
        item { McDonaldsListHistorySection(mcdonalds.drop(1), onMcDonaldsPressed) }
    }
}

@Composable
private fun McDonaldsListTopSection(mcdonalds: McDonalds, roadToMcDonalds: (String) -> Unit) {
    Text(
        modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp),
        text = stringResource(id = R.string.mcdo_around_you_title),
        style = MaterialTheme.typography.titleMedium
    )
    McDonaldsCardTop(
        mcdonalds = mcdonalds,
        modifier = Modifier.clickable(onClick = { roadToMcDonalds(mcdonalds.identifier) })
    )
    PostListDivider()
}

/**
 * Full-width list items that display "based on your history" for [McDonaldsList]
 *
 * @param mcdonalds (state) to display
 * @param roadToMcDonalds (event) request navigation to Article screen
 */
@Composable
private fun McDonaldsListHistorySection(
    mcdonalds: List<McDonalds>,
    roadToMcDonalds: (String) -> Unit
) {
    Column {
        mcdonalds.forEach { mcdo ->
            McDonaldsCardNearby(mcdo, roadToMcDonalds)
            PostListDivider()
        }
    }
}

/**
 * Full-width divider with padding for [McDonaldsList]
 */
@Composable
private fun PostListDivider() {
    Divider(
        modifier = Modifier.padding(horizontal = 14.dp),
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
    )
}


