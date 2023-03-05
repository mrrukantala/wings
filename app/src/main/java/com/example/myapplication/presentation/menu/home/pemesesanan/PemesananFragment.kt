package com.example.myapplication.presentation.menu.home.pemesesanan

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.bossku.utils.toRupiah
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentPemesananBinding
import com.example.myapplication.domain.entity.ProductEntity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@AndroidEntryPoint
class PemesananFragment(private val data: ProductEntity) : BottomSheetDialogFragment() {

    @Inject
    lateinit var pref: com.example.bossku.utils.app.SharedPreferences
    private val menuNavController: NavController? by lazy {
        activity?.findNavController(R.id.nav_host_fragment_menu)
    }

    private val viewModel: PemesananViewModel by viewModels()
    private lateinit var binding: FragmentPemesananBinding

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            viewModel.setTotal(binding.include14.etDependent.text.toString().toInt(), data.price)
            binding.tvValueTotalHarga.text =
                toRupiah(viewModel.totalPrice.value.toString())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPemesananBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.setTotal(binding.include14.etDependent.text.toString().toInt(), data.price)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            include14.etDependent.text = "1".toEditable()
            tvValueHarga.text = toRupiah(data.price.toString())
            viewModel.setTotal(include14.etDependent.text.toString().toInt(), data.price)
            btnPesan.setOnClickListener {
                viewModel.order(pref.getUser(), data)
            }
            ivProduct.setImageResource(R.drawable.img)
            textView24.text = data.productName
            setFieldNumber()
            observer()
        }
    }

    private fun observer() {
        viewModel.totalPrice.observe(viewLifecycleOwner) {
            binding.tvValueTotalHarga.text = toRupiah(viewModel.totalPrice.value.toString())
        }

        viewModel.stateAddDetailData.flowWithLifecycle(lifecycle)
            .onEach { state -> handleState(state) }
            .launchIn(lifecycleScope)
    }

    private fun handleState(state: AddTransactionDetailState) {
        when (state) {

            is AddTransactionDetailState.Loading -> {
                Toast.makeText(requireContext(), "Tunggu Sebentar", Toast.LENGTH_LONG).show()
            }

            is AddTransactionDetailState.Success -> {
                Toast.makeText(requireContext(), "Berhasil memesan produk", Toast.LENGTH_LONG)
                    .show()
                this.dismiss()
            }
            else -> {}
        }
    }

    private fun setFieldNumber() {
        val bindingInclude = binding.include14
        bindingInclude.etDependent.addTextChangedListener(textWatcher)
        val checkVal = viewModel.productQuantity.value
        if (checkVal != null) {
            if (checkVal >= 1) {
                bindingInclude.btnDependentDecrease.background = ContextCompat.getDrawable(
                    binding.root.context,
                    R.drawable.bg_circle_blue
                )
                bindingInclude.btnDependentDecrease.setImageResource(R.drawable.ic_minus)
            }
            if (checkVal == 1) {
                bindingInclude.btnDependentDecrease.background = ContextCompat.getDrawable(
                    binding.root.context,
                    R.drawable.ic_oval_gray
                )
                bindingInclude.btnDependentDecrease.setImageResource(R.drawable.ic_minus)
            }
        }
        // increase
        bindingInclude.btnDependentIncrease.setOnClickListener {
            var value = bindingInclude.etDependent.text.toString().toInt()
            value++

            if (value >= 1) {
                bindingInclude.btnDependentDecrease.background = ContextCompat.getDrawable(
                    binding.root.context,
                    R.drawable.bg_circle_blue
                )
                bindingInclude.btnDependentDecrease.setImageResource(R.drawable.ic_minus)
            }
            if (value == 1) {
                bindingInclude.btnDependentDecrease.background = ContextCompat.getDrawable(
                    binding.root.context,
                    R.drawable.ic_oval_gray
                )
                bindingInclude.btnDependentDecrease.setImageResource(R.drawable.ic_minus)
            }
            viewModel.setQuanityProduct(value)
            bindingInclude.btnDependentDecrease.isEnabled = viewModel.productQuantity.value!! > 1
            bindingInclude.etDependent.text =
                viewModel.productQuantity.value.toString().toEditable()
        }

        bindingInclude.btnDependentDecrease.setOnClickListener {
            var valueMin = bindingInclude.etDependent.text.toString().toInt()

            if (valueMin > 1) valueMin--

            if (valueMin > 1) {
                bindingInclude.btnDependentDecrease.background = ContextCompat.getDrawable(
                    binding.root.context, R.drawable.bg_circle_blue
                )
                bindingInclude.btnDependentDecrease.setImageResource(R.drawable.ic_minus)
            } else if (valueMin == 1) {
                bindingInclude.btnDependentDecrease.background = ContextCompat.getDrawable(
                    binding.root.context, R.drawable.ic_oval_gray
                )
                bindingInclude.btnDependentDecrease.setImageResource(R.drawable.ic_minus)
            }
            viewModel.setQuanityProduct(valueMin)
            bindingInclude.btnDependentDecrease.isEnabled = viewModel.productQuantity.value!! > 1
            bindingInclude.etDependent.text =
                viewModel.productQuantity.value.toString().toEditable()
        }
    }
}

fun String.toEditable(): Editable {
    return Editable.Factory.getInstance().newEditable(this)
}

