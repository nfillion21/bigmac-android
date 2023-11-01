package pgm.poolp.bigmac.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BigMacViewModel @Inject internal constructor() : ViewModel() {

    private var page = 0

    private val _bigMacScreenData = mutableStateOf(createBigMacScreenData())
    val bigMacScreenData: BigMacScreenData
        get() = _bigMacScreenData.value

    /**
     * Returns true if the ViewModel handled the back press (i.e., it went back one question)
     */
    fun onBackPressed(): Boolean {
        if (page == 0) {
            return false
        }
        changePage(page - 1)
        return true
    }

    fun onPreviousPressed() {
        if (page == 0) {
            throw IllegalStateException("onPreviousPressed when on question 0")
        }
        changePage(page - 1)
    }

    private fun changePage(newPage: Int) {
        page = newPage
        _bigMacScreenData.value = createBigMacScreenData()
    }

    private fun createBigMacScreenData(): BigMacScreenData {
        return BigMacScreenData(
            page = page,
            shouldShowPreviousButton = page > 0
        )
    }
}

data class BigMacScreenData(
    val page: Int,
    val shouldShowPreviousButton: Boolean
)