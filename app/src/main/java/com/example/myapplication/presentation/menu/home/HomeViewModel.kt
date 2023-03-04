package com.example.myapplication.presentation.menu.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.entity.ProductEntity
import com.example.myapplication.domain.usecase.ProductUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: ProductUserCase
) : ViewModel() {

    private val _products =
        MutableStateFlow<List<ProductEntity>>(listOf())
    val products get() = _products

    private val _state = MutableStateFlow<ProductState>(ProductState.Init)
    val state get() = _state

    fun success(datas: List<ProductEntity>) {
        _state.value = ProductState.Success(datas)
        _products.value = datas
    }

    fun loading() {
        _state.value = ProductState.Loading(true)
    }

    fun error(response: Any) {

    }

    fun getData(user: String) {
        Log.v("DATA", "${user} kebaca")
        viewModelScope.launch {
            useCase.selectAllProductData(user)
                .onStart { loading() }
                .catch { }
                .collect {
                    when (it) {
                        is com.example.bossku.utils.common.base.Result.Success -> success(it.data)
                        is com.example.bossku.utils.common.base.Result.Error -> Unit
                    }
                }
        }
    }
}

sealed class ProductState {
    object Init : ProductState()
    data class Loading(val loading: Boolean = true) : ProductState()
    data class Success(val data: List<ProductEntity>) : ProductState()
    data class Error(val data: List<ProductEntity>) : ProductState()
}