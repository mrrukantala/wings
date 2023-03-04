package com.example.myapplication.presentation.menu.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.bossku.utils.app.SharedPreferences
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.domain.entity.ProductEntity
import com.kennyc.view.MultiStateView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    @Inject
    lateinit var pref: SharedPreferences
    private val viewModel: HomeViewModel by viewModels()

    private lateinit var binding: FragmentHomeBinding
    private val adapter: ProductHomeAdapter by lazy {
        ProductHomeAdapter({
            Log.v("DATA", it.toString())
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getData(pref.getUser())
        observer()
    }

    private fun observer() {
        viewModel.state.flowWithLifecycle(lifecycle)
            .onEach { state -> handler(state) }
            .launchIn(lifecycleScope)
    }

    private fun handler(productState: ProductState) {
        when (productState) {
            is ProductState.Loading -> binding.msvProduct.viewState =
                MultiStateView.ViewState.LOADING
            is ProductState.Error -> Unit
            is ProductState.Success -> successHandler(productState.data)
            else -> {}
        }
    }

    private fun successHandler(data: List<ProductEntity>) {
        if (data.isEmpty()) {
            binding.msvProduct.viewState = MultiStateView.ViewState.EMPTY
        } else {
            binding.msvProduct.viewState = MultiStateView.ViewState.CONTENT
        }
    }
}