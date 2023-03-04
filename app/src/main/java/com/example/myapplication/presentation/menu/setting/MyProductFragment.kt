package com.example.myapplication.presentation.menu.setting

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
import com.example.myapplication.databinding.FragmentMyProductBinding
import com.example.myapplication.domain.entity.ProductEntity
import com.kennyc.view.MultiStateView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class MyProductFragment : Fragment() {

    private val menuNavController: NavController? by lazy {
        activity?.findNavController(R.id.nav_host_fragment_menu)
    }

    @Inject
    lateinit var pref: SharedPreferences
    private val viewModel: MyProductViewModel by viewModels()
    private lateinit var binding: FragmentMyProductBinding
    private val adapter: MyProductAdapter by lazy {
        MyProductAdapter({
            menuNavController?.navigate(
                MyProductFragmentDirections.actionMyProductFragmentToDetailProductFragment(
                    pref.getUser(), it.productCode
                )
            )
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyProductBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.rvProduct.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        viewModel.getData(pref.getUser())
        binding.btnAddproduct.setOnClickListener {
            menuNavController?.navigate(MyProductFragmentDirections.actionMyProductFragmentToAddProductFragment())
        }

    }

    private fun observer() {
        viewModel.state.flowWithLifecycle(lifecycle)
            .onEach { state -> handler(state) }
            .launchIn(lifecycleScope)
    }

    private fun handler(productState: MyProductState) {
        when (productState) {
            is MyProductState.Loading -> binding.msvProduct.viewState =
                MultiStateView.ViewState.LOADING
            is MyProductState.Error -> Unit
            is MyProductState.Success -> successHandler(productState.data)
            else -> {}
        }
    }

    private fun successHandler(data: List<ProductEntity>) {
        val adapter = binding.rvProduct.adapter as MyProductAdapter
        adapter.submitList(data)

        if (data.isEmpty()) {
            binding.msvProduct.viewState = MultiStateView.ViewState.EMPTY
        } else {
            binding.msvProduct.viewState = MultiStateView.ViewState.CONTENT
        }
    }

}