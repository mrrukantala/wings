package com.example.myapplication.presentation.menu.listcheckout

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
import com.example.myapplication.databinding.FragmentListCheckoutProductBinding
import com.example.myapplication.domain.entity.ListTransactionEntity
import com.kennyc.view.MultiStateView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@AndroidEntryPoint
class ListCheckoutProductFragment : Fragment() {

    private val menuNavController: NavController? by lazy {
        activity?.findNavController(R.id.nav_host_fragment_menu)
    }

    @Inject
    lateinit var pref: SharedPreferences
    private val viewModel: ListCheckoutProductViewModel by viewModels()
    private lateinit var binding: FragmentListCheckoutProductBinding
    private val adapter: ListCheckoutAdapter by lazy {
        ListCheckoutAdapter({

        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentListCheckoutProductBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.rvListTransaction.adapter = adapter
        return binding.root
    }

    private fun observer() {
        viewModel.state.flowWithLifecycle(lifecycle)
            .onEach { state -> handler(state) }
            .launchIn(lifecycleScope)
    }

    private fun handler(listState: ListTransactionState) {
        when (listState) {
            is ListTransactionState.Loading -> binding.msvListTransaction.viewState =
                MultiStateView.ViewState.LOADING
            is ListTransactionState.Error -> Unit
            is ListTransactionState.Success -> successHandler(listState.data)
            else -> {}
        }
    }

    private fun successHandler(data: List<ListTransactionEntity>) {
        val adapter = binding.rvListTransaction.adapter as ListCheckoutAdapter
        adapter.submitList(data)
        if (data.isEmpty()) {
            binding.msvListTransaction.viewState = MultiStateView.ViewState.EMPTY
        } else {
            binding.msvListTransaction.viewState = MultiStateView.ViewState.CONTENT
        }
    }


}