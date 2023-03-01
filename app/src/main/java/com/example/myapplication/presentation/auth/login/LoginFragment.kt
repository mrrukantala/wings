package com.example.myapplication.presentation.auth.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.bossku.utils.app.SharedPreferences
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentLogin2Binding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    @Inject
    lateinit var pref: SharedPreferences
    private lateinit var viewModel: LoginViewModel

    private lateinit var binding: FragmentLogin2Binding
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText

    private val authNavController: NavController? by lazy {
        activity?.findNavController(R.id.nav_host_fragment_authentication)
    }

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
        binding = FragmentLogin2Binding.inflate(inflater, container, false)
        etUsername = binding.etUsername.editText!!
        etPassword = binding.etPassword.editText!!

        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listOf(etUsername, etPassword).forEach { it.addTextChangedListener(textWatcher) }

        binding.btnLogin.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "${etUsername.text ?: ""} ${etPassword.text ?: ""}",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.btnRegis.setOnClickListener {
            authNavController?.navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }
    }

    private fun isFormValid() = etUsername.error == null && binding.etPassword.error == null
    private fun isFormEmpty() = etUsername.text.isNullOrEmpty() || etPassword.text.isNullOrEmpty()

}