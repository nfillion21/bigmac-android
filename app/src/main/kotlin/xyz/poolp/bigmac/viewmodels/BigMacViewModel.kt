package xyz.poolp.bigmac.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.poolp.core.domain.McDonalds
import xyz.poolp.core.usecase.GetMcDonaldsPhotoUseCase
import xyz.poolp.core.usecase.PostMcDonaldsUseCase
import javax.inject.Inject

@HiltViewModel
class BigMacViewModel @Inject internal constructor(
    private val postMcDonaldsUseCase: PostMcDonaldsUseCase,
    private val getMcDonaldsPhotoUseCase: GetMcDonaldsPhotoUseCase
) : ViewModel() {

    // UI state exposed to the UI
    private val _uiState = MutableStateFlow(BigMacUIState())
    val uiState: StateFlow<BigMacUIState> = _uiState

    init {
        viewModelScope.launch {
            val lamorLayeMcDo = _uiState.value.mcdonalds.first().first()
            val mcdonalds = postMcDonaldsUseCase.invoke(
                latitude = lamorLayeMcDo.latitude,
                longitude = lamorLayeMcDo.longitude
            )
            _uiState.update {
                it.copy(
                    mcdonalds = it.mcdonalds.dropLast(1) + listOf(mcdonalds)
                )
            }
        }
    }

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
        if (_uiState.value.mcdonalds.size <= 1) {
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

    private fun backToPreviousMcDonalds() {
        _uiState.update {
            it.copy(
                mcdonalds = it.mcdonalds.dropLast(1)
            )
        }
        //moveStep(increase = false)
    }

    fun onMcDoItemPressed(mcDo: McDonalds) {
        _uiState.update {
            it.copy(
                mcdonalds = (it.mcdonalds + listOf(listOf(mcDo)))
            )
        }
        viewModelScope.launch {
            val mcdonalds = postMcDonaldsUseCase.invoke(
                latitude = mcDo.latitude,
                longitude = mcDo.longitude
            )
            _uiState.update {
                it.copy(
                    mcdonalds = it.mcdonalds.dropLast(1) + listOf(mcdonalds)
                )
            }

            val firstMcDo = _uiState.value.mcdonalds.last().first()
            firstMcDo.photo = getMcDonaldsPhotoUseCase.invoke(name = firstMcDo.photosNames.first().name)
            val lastMcDonalds = _uiState.value.mcdonalds.last()
            _uiState.update {
                it.copy(
                    mcdonalds = it.mcdonalds.dropLast(1) + (listOf(listOf(firstMcDo) + lastMcDonalds.drop(1)))
                )
            }
        }
    }

    suspend fun onCurrentMcDoPressed(mcDo: McDonalds) {
        viewModelScope.launch {
        }
    }
}

data class BigMacUIState(
    val mcdonalds: List<List<McDonalds>> = mutableStateListOf(
        mutableStateListOf(
            McDonalds(
                identifier = "ChIJb_DalK1H5kcRQUZGWQPkr5g",
                formattedAddress = "2 Av. de la Libération, 60260 Lamorlaye, France",
                shortFormattedAddress = "2 Av. de la Libération, Lamorlaye",
                latitude = 49.145964299999996,
                longitude = 2.4415903,
                locality = "Lamorlaye",
                photosNames = emptyList()
            )
        )
    )
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
