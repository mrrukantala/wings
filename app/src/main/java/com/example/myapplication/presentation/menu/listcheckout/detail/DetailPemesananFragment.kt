package com.example.myapplication.presentation.menu.listcheckout.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.bossku.utils.app.SharedPreferences
import com.example.bossku.utils.convertUTC2TimeTo2
import com.example.bossku.utils.enums.ConverterDate
import com.example.bossku.utils.toRupiah
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDetailPemesananBinding
import com.example.myapplication.domain.entity.DetailTransactionEntity
import com.kennyc.view.MultiStateView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class DetailPemesananFragment : Fragment() {

    @Inject
    lateinit var pref: SharedPreferences
    private val args: DetailPemesananFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailPemesananBinding
    private val viewModel: DetailPemesananViewModel by viewModels()

    private val menuNavController: NavController? by lazy {
        activity?.findNavController(R.id.nav_host_fragment_menu)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailPemesananBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.apply {
            toolbar.setNavigationIcon(R.drawable.ic_round_arrow_back_24_black)
            toolbar.title = "Detail Transaksi"
            toolbar.setNavigationOnClickListener {
                menuNavController?.navigateUp()
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getProductDetail(args.docCode, args.data.docuNumber)
        observe()
    }

    private fun observe() {
        viewModel.state.flowWithLifecycle(lifecycle)
            .onEach { state -> handleState(state) }
            .launchIn(lifecycleScope)
    }

    fun handleState(state: StateDetailTransaction) {
        with(binding) {
            when (state) {
                is StateDetailTransaction.Loading -> {
                    msvDetailProduct.viewState = MultiStateView.ViewState.LOADING
                }
                is StateDetailTransaction.Success -> {
                    val data = state.detailTransaction
                    msvDetailProduct.viewState = MultiStateView.ViewState.CONTENT
                    bindData(data)
                }
                else -> {}
            }
        }
    }

    private fun bindData(data: DetailTransactionEntity) {
        with(binding) {
            tvCodenNumberDocument.text = "${data.documentCode} - ${data.docuNumber}"
            tvValueTanggalPembelian.text = args.data.date
            tvValueNamaProduk.text = args.data.namaProduct
            tvHargaSatuan.text = "${args.data.quantity} x ${toRupiah(args.data.price)}"
            val hargaAsli = data.subTotal
            tvValueTotalHargaPerItem.text = toRupiah(hargaAsli)
            tvLabelTotalHarga.text = "Total Harga (${args.data.quantity} barang)"
            tvValueTotalHarga.text = toRupiah(data.subTotal)
            tvLabelDiscount.text = "Discount Harga (${args.data.quantity} barang)"
            val totalDiskon = (data.discount.toInt() * args.data.quantity.toInt())
            tvValueDiscount.text = toRupiah(totalDiskon.toString())
            tvValueTotalBelanja.text = toRupiah(args.data.total)
        }
    }
}