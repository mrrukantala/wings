package com.example.myapplication.presentation.menu.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.bossku.utils.DialogUtils
import com.example.bossku.utils.app.SharedPreferences
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentProfileBinding
import com.example.myapplication.presentation.menu.ContainerMenuFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    @Inject
    lateinit var pref: SharedPreferences

    private val mainNavController: NavController? by lazy {
        activity?.findNavController(R.id.nav_host_fragment_main)
    }

    lateinit var binding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvLogout.setOnClickListener {
            logout()
        }
    }

    fun logout() {
        DialogUtils.showCustomDialog(
            context = requireContext(),
            title = getString(R.string.label_confirmation),
            message = getString(R.string.label_message_dialog_logout),
            positiveAction = Pair(getString(R.string.label_ya)) {
                doLogout()
            },
            negativeAction = Pair(getString(R.string.label_no)) {},
            autoDismiss = false
        )
    }

    private fun doLogout() {
        pref.clear()
        if (pref.getUser().isEmpty()) {
            mainNavController?.navigate(ContainerMenuFragmentDirections.actionContainerMenuFragmentToContainerAuthFragment())
        }
    }
}