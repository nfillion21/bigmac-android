package xyz.poolp.bigmac.bigmac

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.hilt.navigation.compose.hiltViewModel
import xyz.poolp.bigmac.viewmodels.BigMacViewModel

private const val CONTENT_ANIMATION_DURATION = 300

/**
 * Displays a [BigMacScreen] tied to the passed [BigMacViewModel]
 */
@Composable
fun BigMacRoute(
    onNavUp: () -> Unit,
) {
    val viewModel: BigMacViewModel = hiltViewModel()
    val bigMacScreenData = viewModel.bigMacScreenData

    BackHandler {
        if (!viewModel.onBackPressed()) {
            onNavUp()
        }
    }

    BigMacScreen(
        bigMacScreenData = bigMacScreenData,
        onClosePressed = {
            onNavUp()
        },
        onPreviousPressed = { viewModel.onPreviousPressed() },
    ) { paddingValues ->

        val modifier = Modifier.padding(paddingValues)

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
                //1 -> PostList(postsFeed = posts, onArticleTapped = {})
                //else -> PostList(postsFeed = posts, onArticleTapped = {})
            }
            /*
            when (targetState.surveyQuestion) {
                SurveyQuestion.NUMBER_OF_DICE -> NumberOfDiceQuestion(
                    selectedAnswer = viewModel.numberOfDiceResponse,
                    onOptionSelected = viewModel::onNumberOfDiceResponse,
                    modifier = modifier,
                )

                SurveyQuestion.DICE_RESULT -> DiceResultQuestion(
                    selectedAnswer = viewModel.diceResultResponse,
                    onOptionSelected = viewModel::onDiceResultResponse,
                    dices = dices,
                    modifier = modifier,
                )
            }
            */
        }
    }
}

private fun getTransitionDirection(
    initialIndex: Int,
    targetIndex: Int
): AnimatedContentTransitionScope.SlideDirection {
    return if (targetIndex > initialIndex) {
        // Going forwards in the survey: Set the initial offset to start
        // at the size of the content so it slides in from right to left, and
        // slides out from the left of the screen to -fullWidth
        AnimatedContentTransitionScope.SlideDirection.Left
    } else {
        // Going back to the previous question in the set, we do the same
        // transition as above, but with different offsets - the inverse of
        // above, negative fullWidth to enter, and fullWidth to exit.
        AnimatedContentTransitionScope.SlideDirection.Right
    }
}
