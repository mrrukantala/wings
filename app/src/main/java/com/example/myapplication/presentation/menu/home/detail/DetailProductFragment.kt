package com.example.myapplication.presentation.menu.home.detail

import android.graphics.Paint
import android.os.Bundle
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
import com.example.bossku.utils.toRupiah
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDetailProductBinding
import com.kennyc.view.MultiStateView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class DetailProductFragment : Fragment() {

    @Inject
    lateinit var pref: SharedPreferences

    private val args: DetailProductFragmentArgs by navArgs()
    private val viewModel: DetailProductViewModel by viewModels()
    private lateinit var binding: FragmentDetailProductBinding

    private val menuNavController: NavController? by lazy {
        activity?.findNavController(R.id.nav_host_fragment_menu)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailProductBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.apply {
            toolbar.setNavigationIcon(R.drawable.ic_round_arrow_back_24_black)
            toolbar.title = "Detail Produk"
            toolbar.setNavigationOnClickListener {
                menuNavController?.navigateUp()
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getProductDetail(args.idproduk)
        if (args.username == pref.getUser()) {
            binding.btnAddproduct.visibility = View.GONE
        }
        observe()
    }

    private fun observe() {
        viewModel.state.flowWithLifecycle(lifecycle)
            .onEach { state -> handleState(state) }
            .launchIn(lifecycleScope)
    }

    fun handleState(state: StateDetailProduct) {
        with(binding) {
            when (state) {
                is StateDetailProduct.Loading -> {
                    msvDetailProduct.viewState = MultiStateView.ViewState.LOADING
                }
                is StateDetailProduct.Success -> {
                    val data = state.detailProductEntity
                    msvDetailProduct.viewState = MultiStateView.ViewState.CONTENT

                    imageView2.setImageResource(R.drawable.img)
                    tvProductPrice.text = toRupiah((data.price - data.discount).toString())
                    tvProductDiscount.text = toRupiah(data.price.toString())
                    tvProductDiscount.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    tvProductName.text = data.productName

                    desc.text = "Dimensi barang ${data.dimension}"
                    tvUnit.text = data.unit


                }
                else -> {}
            }
        }
    }
}