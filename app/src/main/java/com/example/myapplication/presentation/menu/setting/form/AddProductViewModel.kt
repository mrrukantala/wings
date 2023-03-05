package com.example.myapplication.presentation.menu.setting.form

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.ProductItem
import com.example.myapplication.domain.entity.ProductEntity
import com.example.myapplication.domain.usecase.ProductUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val productUserCase: ProductUserCase
) : ViewModel() {

    private var _productName = MutableLiveData<String?>()
    val productName get() = _productName

    private var _productPrice = MutableLiveData<Int>()
    val productPrice get() = _productPrice

    private var _currenyType = MutableLiveData<String>("IDR")

    private var _discount = MutableLiveData<Int>()
    val discount get() = _discount

    private var _dimensionWidth = MutableLiveData<Int>()
    val dimensionWidth get() = _dimensionWidth

    private var _dimensionHeight = MutableLiveData<Int>()
    val dimensionHeight get() = _dimensionHeight

    private var _dimension = MutableLiveData("${_dimensionWidth.value} cm x ${_dimensionHeight.value}  cm")

    private val _stateAddData = MutableStateFlow<TambahProductState>(TambahProductState.Init)
    val stateAddData: Flow<TambahProductState> get() = _stateAddData

    private fun loadingAddData() {
        _stateAddData.value = TambahProductState.Loading()
    }

    private fun successAddData(postProductEntity: ProductEntity) {
        _stateAddData.value = TambahProductState.Success(postProductEntity)
    }

    fun uploadProduct(user: String) {
        val postProductRequest =
            ProductItem(
                System.currentTimeMillis().toString(),
                _productName.value.toString(),
                user,
                _productPrice.value.toString().toInt(),
                _currenyType.value.toString(),
                _discount.value.toString().toInt(),
                _dimension.value.toString(),
                "PCS"
            )

        viewModelScope.launch(Dispatchers.IO) {
            productUserCase.insertProduct(postProductRequest)
                .onStart {
                    loadingAddData()
                }
                .catch {
                }
                .collect { result ->
                    when (result) {
                        is com.example.bossku.utils.common.base.Result.Success -> {
                            successAddData(result.data)
                        }
                        is com.example.bossku.utils.common.base.Result.Error -> {
                        }
                    }
                }
        }
    }

    fun setAllFieldNull() {
        setNullNamaProduct()
        setNullHargaProduct()
        setNullPotonganProduct()
        setNullWidthProduct()
        setNullHeightProduct()
    }

    private fun setNullNamaProduct() {
        _productName.value = null
    }

    private fun setNullHargaProduct() {
        _productPrice.value = 0
    }

    private fun setNullPotonganProduct() {
        _discount.value = 0
    }

    private fun setNullWidthProduct() {
        _dimensionWidth.value = 0
    }

    private fun setNullHeightProduct() {
        _dimensionHeight.value = 0
    }

    fun setAllField(
        valueNama: String,
        valueHarga: String,
        valuePotongan: String,
        valueWidth: String,
        valueHeight: String
    ) {
        setNamaProduct(valueNama)
        setHargaProduct(valueHarga)
        setPotonganProduct(valuePotongan)
        setWidthProduct(valueWidth)
        setHeightProduct(valueHeight)
    }

    private fun setHeightProduct(valueHeight: String) {
        _dimensionHeight.value = valueHeight.replace(".", "").toInt() ?: 0
        _dimension.value = "${_dimensionWidth.value} cm x ${_dimensionHeight.value}  cm"
    }

    private fun setWidthProduct(valueWidth: String) {
        _dimensionWidth.value = valueWidth.replace(".", "").toInt() ?: 0
        _dimension.value = "${_dimensionWidth.value} cm x ${_dimensionHeight.value}  cm"
    }

    private fun setPotonganProduct(valuePotongan: String) {
        _discount.value = valuePotongan.replace(".", "").toInt() ?: 0
    }

    private fun setHargaProduct(valueHarga: String) {
        _productPrice.value = valueHarga.replace(".", "").toInt() ?: 0
    }

    private fun setNamaProduct(valueNama: String) {
        _productName.value = valueNama
    }
}

sealed class TambahProductState {
    object Init : TambahProductState()
    data class Loading(val loading: Boolean = true) : TambahProductState()
    data class Success(val postProductEntity: ProductEntity) : TambahProductState()
}