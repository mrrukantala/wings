package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.bossku.utils.app.SharedPreferences
import com.example.myapplication.presentation.auth.ContainerAuthFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainNavController: NavController? by lazy {
        Navigation.findNavController(this, R.id.nav_host_fragment_main)
    }

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_MyApp)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val user = sharedPreferences.getUser()
        if (user.isNotEmpty()) goToHome()
    }

    private fun goToHome() {
        val direction =
            ContainerAuthFragmentDirections.actionContainerAuthFragmentToContainerMenuFragment()
        mainNavController?.navigate(direction)
    }
}