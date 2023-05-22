package com.oosca.parcial1.ui.newspaper

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.oosca.parcial1.R
import com.oosca.parcial1.databinding.FragmentNewspaperBinding
import com.oosca.parcial1.databinding.FragmentTrackerBinding
import com.oosca.parcial1.ui.newspaper.viewmodel.NewspaperViewModel

class NewspaperFragment : Fragment() {

    private val viewModel: NewspaperViewModel by activityViewModels{
        NewspaperViewModel.Factory
    }

    private lateinit var binding: FragmentNewspaperBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewspaperBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewModel()
    }

    private fun setViewModel(){
        binding.viewmodel = viewModel
    }
}