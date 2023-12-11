package xyz.poolp.bigmac.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.poolp.core.domain.McDonalds
import xyz.poolp.core.usecase.PostMcDonaldsUseCase
import javax.inject.Inject

@HiltViewModel
class BigMacViewModel @Inject internal constructor(
    private val postMcDonaldsUseCase: PostMcDonaldsUseCase
) : ViewModel() {

    // UI state exposed to the UI
    private val _uiState = MutableStateFlow(BigMacUIState())
    val uiState: StateFlow<BigMacUIState> = _uiState

    //private var step = 0

    /*
    private val _bigMacScreenData = mutableStateOf(createBigMacScreenData())
    val bigMacScreenData: BigMacUIState
        get() = _bigMacScreenData.value
    */

    /*
    init {
        observeAssets()
    }

    private fun observeAssets() {
        viewModelScope.launch {
            assetsUseCase.invoke()
                .catch { ex ->
                    _uiState.value = MoaiHomeUIState(
                        ethError = ex.message,
                        tzError = ex.message
                    )
                }
                .collect { assetList ->
                    _uiState.update {
                        it.copy(
                            assets = assetList,
                            step = 1
                        )
                    }
                    launchAllAssets(fromPullToRefresh = false)
                }
        }
    }
    */

    /**
     * Returns true if the ViewModel handled the back press (i.e., it went back one page)
     */
    fun onBackPressed(): Boolean {
        if (_uiState.value.mcdonalds.isEmpty()) {
            return false
        }
        backToPreviousMcDonalds()
        return true
    }

    fun onPreviousPressed() {
        if (_uiState.value.mcdonalds.size <= 1) {
            throw IllegalStateException("onPreviousPressed when on step 0")
        }
        backToPreviousMcDonalds()
    }

    fun onMcDoPressed(mcdonalds: McDonalds) {
        val list = _uiState.value.mcdonalds
        _uiState.update {
            it.copy(
                mcdonalds = list.apply {
                    add(listOf(mcdonalds))
                }
            )
        }
        switchStep(increase = true)
    }

    private fun backToPreviousMcDonalds() {
        val list = _uiState.value.mcdonalds
        _uiState.update {
            it.copy(
                mcdonalds = list.apply {
                    dropLast(1)
                }
            )
        }
        switchStep(increase = false)
    }

    suspend fun onMcDonaldsPressed(mcDonalds: McDonalds) {
        viewModelScope.launch {
            val k = postMcDonaldsUseCase.invoke()
            val k2 = "hello world"
        }
    }

    private fun switchStep(increase:Boolean) {
        _uiState.update {
            it.copy(
                step = if (increase) _uiState.value.step + 1 else _uiState.value.step -1
            )
        }
    }
}

data class BigMacUIState(
    val mcdonalds: MutableList<List<McDonalds>> = mutableListOf(
        listOf(
            McDonalds(
                identifier = "ChIJb_DalK1H5kcRQUZGWQPkr5g",
                formattedAddress = "2 Av. de la Libération, 60260 Lamorlaye, France",
                shortFormattedAddress = "2 Av. de la Libération, Lamorlaye",
                latitude = 49.145964299999996,
                longitude = 2.4415903,
                locality = "Lamorlaye"
            )
        )
    ),
    val step: Int = 0
)

/*
data class MoaiHomeUIState(
    val ethLoading: Boolean = false,
    val tezLoading: Boolean = false,
    val commodityLoading: Boolean = false,
    val pullToRefreshLoading: Boolean = false,
    val step: Int = 0,
    val tzError: String? = null,
    val ethError: String? = null,
    val commodityError: String? = null
)
*/
