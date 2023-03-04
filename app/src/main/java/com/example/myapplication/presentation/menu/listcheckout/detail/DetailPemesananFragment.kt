package com.example.myapplication.presentation.menu.listcheckout.detail

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
import com.example.bossku.utils.app.SharedPreferences
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDetailPemesananBinding
import com.kennyc.view.MultiStateView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class DetailPemesananFragment : Fragment() {

    @Inject
    lateinit var pref: SharedPreferences
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
        viewModel.getProductDetail("", "")
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
                }
                else -> {}
            }
        }
    }

}