package com.example.myapplication.presentation.menu.home.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bossku.utils.common.base.Result
import com.example.myapplication.domain.entity.ProductEntity
import com.example.myapplication.domain.usecase.ProductUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailProductViewModel @Inject constructor(
    private val useCase: ProductUserCase
) : ViewModel() {

    private val _state = MutableStateFlow<StateDetailProduct>(StateDetailProduct.Init)
    val state get() = _state

    private val _data = MutableStateFlow<ProductEntity?>(null)
    val data: StateFlow<ProductEntity?> get() = _data

    private fun setLoading() {
        _state.value = StateDetailProduct.Loading(true)
    }

    private fun success(detailProductEntity: ProductEntity?) {
        _data.value = detailProductEntity
        _state.value = StateDetailProduct.Success(detailProductEntity!!)
    }

    fun getProductDetail(productCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.selectAllProductDataId(productCode)
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

sealed class StateDetailProduct {
    object Init : StateDetailProduct()
    data class Loading(val isLoading: Boolean) : StateDetailProduct()
    data class Success(val detailProductEntity: ProductEntity) : StateDetailProduct()
}