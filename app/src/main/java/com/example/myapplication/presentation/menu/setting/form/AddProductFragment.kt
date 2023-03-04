package com.example.myapplication.presentation.menu.setting.form

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.bossku.utils.app.SharedPreferences
import com.example.bossku.utils.ui.addCurrencyTextWatcher
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentAddProductBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class AddProductFragment : Fragment() {

    @Inject
    lateinit var pref: SharedPreferences

    private val viewModel: AddProductViewModel by viewModels()
    private val menuNavController: NavController? by lazy {
        activity?.findNavController(R.id.nav_host_fragment_menu)
    }
    private lateinit var binding: FragmentAddProductBinding

    private lateinit var etProductName: EditText
    private lateinit var etProductPrice: EditText
    private lateinit var etProductDiscount: EditText
    private lateinit var etDimensionWidth: EditText
    private lateinit var etDimensionHeight: EditText

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            if (s?.isEmpty() == true) {
                viewModel.setAllFieldNull()
            } else {
                viewModel.setAllField(
                    etProductName.text.toString(),
                    etProductPrice.text.toString(),
                    etProductDiscount.text.toString(),
                    etDimensionWidth.text.toString(),
                    etDimensionWidth.text.toString()
                )
            }
        }

        override fun afterTextChanged(s: Editable?) {
            binding.btnAddproduct.isEnabled = !isFormEmpty()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddProductBinding.inflate(inflater, container, false)
        with(binding) {
            toolbar.setNavigationIcon(R.drawable.ic_round_arrow_back_24_black)
            toolbar.title = "Tambah Produk Anda"
            toolbar.setNavigationOnClickListener {
                menuNavController?.navigateUp()
            }
            edtHarga.addCurrencyTextWatcher()
            edtDiscount.addCurrencyTextWatcher()
            lifecycleOwner = viewLifecycleOwner

            etProductName = this.etTambahProduk.editText!!
            etProductPrice = this.edtHarga.editText!!
            etProductDiscount = this.edtDiscount.editText!!
            etDimensionWidth = this.edtPanjang.editText!!
            etDimensionHeight = this.edtLebar.editText!!

            btnAddproduct.setOnClickListener {
                viewModel.uploadProduct(pref.getUser())
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listOf(
            etProductName, etProductPrice, etProductDiscount, etDimensionWidth, etDimensionHeight
        ).forEach {
            it.addTextChangedListener(textWatcher)
        }
        observerAdd()
    }

    private fun observerAdd() {
        viewModel.stateAddData.flowWithLifecycle(lifecycle)
            .onEach { state -> handleState(state) }
            .launchIn(lifecycleScope)
    }

    private fun handleState(state: TambahProductState) {
        when (state) {

            is TambahProductState.Loading -> {
                loadingAddAndEdit(View.VISIBLE)
            }

            is TambahProductState.Success -> {
                loadingAddAndEdit(View.GONE)
                Toast.makeText(requireContext(), "Berhasil menambah data", Toast.LENGTH_LONG).show()
                menuNavController?.navigateUp()
            }
            else -> {}
        }
    }

    private fun loadingAddAndEdit(state: Int) {
        binding.iloding.root.visibility = state
//        binding.iloading.root.visibility = state
    }

    private fun isFormEmpty(): Boolean {
        return etProductName.text.isNullOrEmpty()
                || etProductPrice.text.isNullOrEmpty()
                || etProductDiscount.text.isNullOrEmpty()
                || etDimensionWidth.text.isNullOrEmpty()
                || etDimensionHeight.text.isNullOrEmpty()
    }
}