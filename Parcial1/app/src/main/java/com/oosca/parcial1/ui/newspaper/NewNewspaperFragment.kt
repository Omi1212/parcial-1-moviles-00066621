package com.oosca.parcial1.ui.newspaper

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.oosca.parcial1.R
import com.oosca.parcial1.databinding.FragmentNewNewspaperBinding

class NewNewspaperFragment : Fragment() {

    private val newspaperViewModel: NewspaperViewModel by activityViewModels {
        NewspaperViewModel.Factory
    }
    lateinit var binding: FragmentNewNewspaperBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewNewspaperBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewModel()
        observeStatus()
    }

    private fun setViewModel(){
        binding.viewmodel = newspaperViewModel
    }

    private fun observeStatus(){
        newspaperViewModel.status.observe(viewLifecycleOwner) { status ->
            when{
                status.equals(NewspaperViewModel.NEWSPAPER_CREATED) -> {
                    Log.d(APP_TAG, status)
                    Log.d(APP_TAG, newspaperViewModel.getNewspapers().toString())
                    newspaperViewModel.clearData()
                    newspaperViewModel.clearStatus()
                    findNavController().popBackStack()
                }
                status.equals(NewspaperViewModel.WRONG_INFORMATION) -> {
                    Log.d(APP_TAG, status)
                    newspaperViewModel.clearStatus()

                }
            }
        }
    }
    companion object{

        const val APP_TAG = "APP_TAG"
    }

}

