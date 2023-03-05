package com.example.myapplication.domain.dao.penjualan

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.data.model.TransactionDetailItem
import com.example.myapplication.data.model.TransactionHeaderItem
import com.example.myapplication.domain.entity.DetailTransactionEntity
import com.example.myapplication.domain.entity.ListTransactionEntity

@Dao
interface TransactionDao {
    @Insert
    fun insertDetail(data: TransactionDetailItem)

    @Insert
    fun insertHeader(data: TransactionHeaderItem)

    @Query("SELECT * FROM transaction_detail_data WHERE _docment_code = :key")
    fun getTransactionDetailByKey(key: String): TransactionDetailItem

    @Query("SELECT * FROM transaction_header_data WHERE _user = :key")
    fun getTransactionHeaderByKey(key: String): TransactionHeaderItem

    @Query("SELECT _document_code,transaction_detail_data._document_number, product_data._product_name, transaction_header_data._user, _total, _date, transaction_detail_data._price, transaction_detail_data._quantity from transaction_header_data INNER JOIN transaction_detail_data INNER JOIN product_data ON _docment_code = _document_code AND _product_code = product_data.productCode WHERE transaction_header_data._user =:key")
    fun getListTransaction(key: String): List<ListTransactionEntity>

    @Query("SELECT _docment_code, _document_number, transaction_detail_data._unit, _sub_total, transaction_detail_data._currency, productCode, _discount FROM transaction_detail_data INNER JOIN product_data ON productCode = _product_code WHERE  _docment_code =:docCode AND _document_number = :docNumber")
    fun getDetailTransaction(docCode: String, docNumber: String): DetailTransactionEntity
}