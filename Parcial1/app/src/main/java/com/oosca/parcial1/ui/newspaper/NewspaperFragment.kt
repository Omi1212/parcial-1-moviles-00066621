package com.oosca.parcial1.ui.newspaper

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.oosca.parcial1.R
import com.oosca.parcial1.databinding.FragmentNewspaperBinding

class NewspaperFragment : Fragment() {

    private lateinit var binding: FragmentNewspaperBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewspaperBinding.inflate(inflater,container,false)
        return binding.root
    }

}