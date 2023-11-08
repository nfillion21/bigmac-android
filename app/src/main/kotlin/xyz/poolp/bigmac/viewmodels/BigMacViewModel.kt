package xyz.poolp.bigmac.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BigMacViewModel @Inject internal constructor() : ViewModel() {

    private var step = 0

    private val _bigMacScreenData = mutableStateOf(createBigMacScreenData())
    val bigMacScreenData: BigMacScreenData
        get() = _bigMacScreenData.value

    /**
     * Returns true if the ViewModel handled the back press (i.e., it went back one question)
     */
    fun onBackPressed(): Boolean {
        if (step == 0) {
            return false
        }
        changePage(step - 1)
        return true
    }

    fun onPreviousPressed() {
        if (step == 0) {
            throw IllegalStateException("onPreviousPressed when on question 0")
        }
        changePage(step - 1)
    }

    private fun changePage(newPage: Int) {
        step = newPage
        _bigMacScreenData.value = createBigMacScreenData()
    }

    private fun createBigMacScreenData(): BigMacScreenData {
        return BigMacScreenData(
            step = step,
            shouldShowPreviousButton = step > 0
        )
    }
}

data class BigMacScreenData(
    val step: Int,
    val shouldShowPreviousButton: Boolean
)