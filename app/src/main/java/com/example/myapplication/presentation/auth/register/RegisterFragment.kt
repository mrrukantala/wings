package com.example.myapplication.presentation.auth.register

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myapplication.databinding.FragmentRegisterBinding
import com.example.myapplication.domain.entity.UserD
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private val viewModel: RegisterViewModel by viewModels()

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (s?.isEmpty() == true) {
//                viewModel.setAllFieldNull()
            } else {
//                viewModel.setAllField(
//                    binding.editTextEmail.editText?.text.toString(),
//                    binding.editTextPassword.editText?.text.toString()
//                )
            }
        }

        override fun afterTextChanged(s: Editable?) {
            binding.btnLogin.isEnabled = !isFormEmpty() && isFormValid()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        etUsername = binding.etUsername.editText!!
        etPassword = binding.etPassword.editText!!

        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        listOf(etUsername, etPassword).forEach { it.addTextChangedListener(textWatcher) }

        binding.btnLogin.setOnClickListener {
            viewModel.getData(etUsername.text.toString())
//            viewModel.register(UserD(etUsername.text.toString(), etPassword.text.toString()))
        }
    }

    private fun isFormValid() = etUsername.error == null && binding.etPassword.error == null
    private fun isFormEmpty() = etUsername.text.isNullOrEmpty() || etPassword.text.isNullOrEmpty()

}