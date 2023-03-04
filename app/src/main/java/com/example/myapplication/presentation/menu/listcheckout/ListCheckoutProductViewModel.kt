package com.example.myapplication.presentation.menu.listcheckout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.entity.ListTransactionEntity
import com.example.myapplication.domain.usecase.TransactionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListCheckoutProductViewModel @Inject constructor(
    private val useCase: TransactionUseCase
) : ViewModel() {

    private val _data = MutableStateFlow<List<ListTransactionEntity>>(listOf())
    val data get() = _data

    private val _state = MutableStateFlow<ListTransactionState>(ListTransactionState.Init)
    val state get() = _state

    fun success(datas: List<ListTransactionEntity>) {
        _state.value = ListTransactionState.Success(datas)
        _data.value = datas
    }

    fun loading() {
        _state.value = ListTransactionState.Loading(true)
    }

    fun error(response: Any) {

    }

    fun getData(key: String) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.getListTransaction(key)
                .onStart {
                    loading()
                }
                .catch { }
                .collect {
                    when (it) {
                        is com.example.bossku.utils.common.base.Result.Success -> {
                            success(it.data)
                        }
                        is com.example.bossku.utils.common.base.Result.Error -> {
                            Unit
                        }
                    }
                }
        }
    }

}

sealed class ListTransactionState {
    object Init : ListTransactionState()
    data class Loading(val loading: Boolean = true) : ListTransactionState()
    data class Success(val data: List<ListTransactionEntity>) : ListTransactionState()
    data class Error(val data: List<ListTransactionEntity>) : ListTransactionState()
}