package xyz.poolp.bigmac.presentation.main.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import xyz.poolp.bigmac.R
import xyz.poolp.bigmac.util.supportWideScreen
import xyz.poolp.core.domain.McDonalds

private const val CONTENT_ANIMATION_DURATION = 300

@OptIn(ExperimentalMaterial3Api::class)
// Scaffold is experimental in m3
@Composable
fun BigMacScreen(
    bigMacViewModel: BigMacViewModel,
    onClosePressed: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val uiState by bigMacViewModel.uiState.collectAsStateWithLifecycle()

    Surface(modifier = Modifier.supportWideScreen()) {
        val topAppBarState = rememberTopAppBarState()
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)
        Scaffold(
            topBar = {
                BigMacAppBar(
                    step = uiState.mcdonalds.size,
                    title = uiState.mcdonalds.last().first().locality,
                    scrollBehavior = scrollBehavior,
                    onClosePressed = onClosePressed,
                    onBackPressed = { bigMacViewModel.onPreviousPressed() },
                    shouldShowPreviousButton = uiState.mcdonalds.size > 1
                )
            },
        )
        { innerPadding ->
            AnimatedContent(
                targetState = uiState,
                transitionSpec = {

                    val initial = initialState.mcdonalds.size
                    val target = targetState.mcdonalds.size

                    if (initial != target) {
                        val animationSpec: TweenSpec<IntOffset> = tween(CONTENT_ANIMATION_DURATION)
                        val direction = getTransitionDirection(
                            initialIndex = initial,
                            targetIndex = target
                        )

                        slideIntoContainer(
                            towards = direction,
                            animationSpec = animationSpec,
                        ) togetherWith slideOutOfContainer(
                            towards = direction,
                            animationSpec = animationSpec
                        )
                    } else {
                        EnterTransition.None togetherWith ExitTransition.None
                    }
                },
                //transitionSpec = null,
                label = "BigMacScreenDataAnimation"
            ) { targetState ->
                McDonaldsList(
                    bigMacState = targetState,
                    onMcDoItemPressed = {
                        scope.launch {
                            bigMacViewModel.onMcDoItemPressed(it)
                        }
                    },
                    retryMcDonalds = {
                        scope.launch { bigMacViewModel.loadMcDonalds() }
                    },
                    retryMcDonaldsPhoto = {
                        scope.launch { bigMacViewModel.loadPhoto() }
                    },
                    modifier = Modifier
                        .nestedScroll(scrollBehavior.nestedScrollConnection)
                        .padding(innerPadding)
                )
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
                    text = stringResource(R.string.mcdonalds_in, step, title),
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
    bigMacState: BigMacUIState,
    onMcDoItemPressed: (mcdo: McDonalds) -> Unit,
    retryMcDonalds: () -> Unit,
    retryMcDonaldsPhoto: () -> Unit,
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
                state = bigMacState,
                mcdonalds = bigMacState.mcdonalds.last().first(),
                lamorlayeMcDo = bigMacState.mcdonalds.first().first(),
                retryMcDonaldsPhoto = retryMcDonaldsPhoto,
                step = bigMacState.mcdonalds.size
            )
        }

        item {
            when (bigMacState.mcDonaldsViewState) {
                is McDonaldsViewState.Success -> {
                    if (bigMacState.mcdonalds.last().size > 1) {
                        McDonaldsNearbyListSection(
                            mcdonalds = bigMacState.mcdonalds.last().drop(1),
                            currentMcDo = bigMacState.mcdonalds.last().first(),
                            step = bigMacState.mcdonalds.size,
                            roadToMcDonalds = onMcDoItemPressed
                        )
                    }
                }

                is McDonaldsViewState.Failure -> {
                    FilledTonalButton(
                        onClick = retryMcDonalds,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                    ) {
                        Text(text = stringResource(id = R.string.an_error_occurred))
                    }
                }

                is McDonaldsViewState.Loading -> {
                    LinearProgressIndicator(modifier = Modifier.fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                        .padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun McDonaldsListTopSection(
    state: BigMacUIState,
    mcdonalds: McDonalds,
    lamorlayeMcDo: McDonalds,
    retryMcDonaldsPhoto: () -> Unit,
    step: Int
) {
    Text(
        modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp),
        text = stringResource(id = R.string.mcdo_around_you_title),
        style = MaterialTheme.typography.titleMedium
    )
    McDonaldsCardTop(
        state = state,
        mcDo = mcdonalds,
        lamorlayeMcDo = lamorlayeMcDo,
        retryMcDonaldsPhoto = retryMcDonaldsPhoto,
        step = step
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
private fun McDonaldsNearbyListSection(
    mcdonalds: List<McDonalds>,
    currentMcDo: McDonalds,
    step: Int,
    roadToMcDonalds: (McDonalds) -> Unit
) {
    Column {
        mcdonalds.forEach { mcdo ->
            McDoCardNearby(
                mcdo = mcdo,
                currentMcDo = currentMcDo,
                step = step,
                roadToMcdo = roadToMcDonalds
            )
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


