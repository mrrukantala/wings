package com.example.myapplication.presentation.menu.home.pemesesanan

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.TransactionDetailItem
import com.example.myapplication.data.model.TransactionHeaderItem
import com.example.myapplication.domain.entity.ProductEntity
import com.example.myapplication.domain.entity.TransactionDetailEntity
import com.example.myapplication.domain.entity.TransactionHeaderEntity
import com.example.myapplication.domain.usecase.TransactionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PemesananViewModel @Inject constructor(
    private val useCase: TransactionUseCase
) : ViewModel() {

    private var _productQuantity = MutableLiveData<Int>()
    val productQuantity get() = _productQuantity

    private var _totalPrice = MutableLiveData<Int>(0)
    val totalPrice get() = _totalPrice

    private var _product = MutableLiveData<ProductEntity?>(null)
    val produk get() = _product

    private val _stateAddData =
        MutableStateFlow<AddTransactionHeaderState>(AddTransactionHeaderState.Init)
    val stateAddData: Flow<AddTransactionHeaderState> get() = _stateAddData

    private val _stateAddDetailData =
        MutableStateFlow<AddTransactionDetailState>(AddTransactionDetailState.Init)
    val stateAddDetailData: Flow<AddTransactionDetailState> get() = _stateAddDetailData


    private fun loadingAddData() {
        _stateAddData.value = AddTransactionHeaderState.Loading()
    }

    private fun successAddData(data: TransactionHeaderEntity) {
        _stateAddData.value = AddTransactionHeaderState.Success(data)
    }

    private fun successAddDataDetail(data: TransactionDetailEntity) {
        _stateAddDetailData.value = AddTransactionDetailState.Success(data)
    }

    fun order(user: String, produk: ProductEntity) {
        val dateTransaction = SimpleDateFormat("dd/MM/yyyy")
        val current = dateTransaction.format(Date())


        val data = TransactionHeaderItem(
            System.currentTimeMillis().toString(),
            System.currentTimeMillis().toString(),
            user,
            _totalPrice.value!!.toInt(),
            current
        )

        viewModelScope.launch(Dispatchers.IO) {
            useCase.insertTransactionHeader(data)
                .onStart {
                    loadingAddData()
                }
                .catch {

                }
                .collect {
                    when (it) {
                        is com.example.bossku.utils.common.base.Result.Success -> {
                            val detailData = TransactionDetailItem(
                                data.documentCode,
                                data.documentNumber,
                                produk.productCode,
                                produk.price,
                                _productQuantity.value?.toInt()!!,
                                produk.unit,
                                _totalPrice.value?.toInt()!!,
                                "IDR"
                            )
                            insertDetail(detailData)
                        }
                        is com.example.bossku.utils.common.base.Result.Error -> {
                            Log.v("DATA", "ERROR")
                        }
                    }
                }
        }
    }

    private fun insertDetail(detailData: TransactionDetailItem) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.insertTransactionDetail(detailData)
                .onStart {
                    loadingAddData()
                }
                .catch {

                }
                .collect {
                    when (it) {
                        is com.example.bossku.utils.common.base.Result.Success -> {
                            successAddDataDetail(it.data)
                        }
                        is com.example.bossku.utils.common.base.Result.Error -> Log.v(
                            "DATA",
                            "ERROR DETAIL"
                        )
                    }
                }
        }
    }


    fun setTotal(qty: Int, price: Int, discount: Int) {

        val hasil = (price * qty) - discount
        _totalPrice.value = hasil
    }

    fun setQuanityProduct(qty: Int) {
        _productQuantity.value = qty
    }

    fun setAllFieldNull() {
        setNullQuantity()
    }

    fun setAllFiled(
        qty: String
    ) {
        setQuanityProduct(qty.toInt())
    }

    private fun setNullQuantity() {
        _productQuantity.value = 0
    }

}

sealed class AddTransactionHeaderState {
    object Init : AddTransactionHeaderState()
    data class Loading(val loading: Boolean = true) : AddTransactionHeaderState()
    data class Success(val transactionHeaderEntity: TransactionHeaderEntity) :
        AddTransactionHeaderState()
}

sealed class AddTransactionDetailState {
    object Init : AddTransactionDetailState()
    data class Loading(val loading: Boolean = true) : AddTransactionDetailState()
    data class Success(val transactionDetailEntity: TransactionDetailEntity) :
        AddTransactionDetailState()
}