package com.example.myapplication.presentation.menu.home.pemesesanan

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PemesananFragment : Fragment() {

    private lateinit var viewModel: PemesananViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pemesanan, container, false)
    }


}