package com.example.myapplication.presentation.menu.listcheckout.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bossku.utils.common.base.Result
import com.example.myapplication.domain.entity.DetailTransactionEntity
import com.example.myapplication.domain.usecase.TransactionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailPemesananViewModel @Inject constructor(
    private val useCase: TransactionUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<StateDetailTransaction>(
        StateDetailTransaction.Init
    )
    val state get() = _state

    private val _data = MutableStateFlow<DetailTransactionEntity?>(null)
    val data: StateFlow<DetailTransactionEntity?> get() = _data

    private fun setLoading() {
        _state.value = StateDetailTransaction.Loading(true)
    }

    private fun success(detailTransaction: DetailTransactionEntity?) {
        _data.value = detailTransaction
        _state.value = StateDetailTransaction.Success(detailTransaction!!)
    }


    fun getProductDetail(docCode: String, docNumber: String) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.getDetailTransaction(docCode, docNumber)
                .onStart {
                    setLoading()
                }
                .catch { }
                .collect { result ->
                    when (result) {
                        is Result.Success -> {
                            success(result.data)
                        }
                        is Result.Error -> error(result.response)
                    }
                }
        }
    }
}

sealed class StateDetailTransaction {
    object Init : StateDetailTransaction()
    data class Loading(val isLoading: Boolean) : StateDetailTransaction()
    data class Success(val detailTransaction: DetailTransactionEntity) : StateDetailTransaction()
}