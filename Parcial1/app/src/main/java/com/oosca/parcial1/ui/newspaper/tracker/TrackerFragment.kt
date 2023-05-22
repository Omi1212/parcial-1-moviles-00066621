package com.oosca.parcial1.ui.newspaper.tracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.oosca.parcial1.R
import com.oosca.parcial1.data.model.NewspaperModel
import com.oosca.parcial1.databinding.FragmentTrackerBinding
import com.oosca.parcial1.ui.newspaper.tracker.recyclerview.NewspaperRecyclerViewAdapter
import com.oosca.parcial1.ui.newspaper.viewmodel.NewspaperViewModel

class TrackerFragment : Fragment() {

    private val newspaperViewModel: NewspaperViewModel by activityViewModels {
        NewspaperViewModel.Factory
    }

    private lateinit var adapter: NewspaperRecyclerViewAdapter
    lateinit var binding: FragmentTrackerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrackerBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView(view)

        binding.floatingActionButton.setOnClickListener{
            newspaperViewModel.clearData()
            it.findNavController().navigate(R.id.action_trackerFragment_to_newNewspaperFragment)
        }
    }

    private fun setRecyclerView(view:View){
        binding.recyclerView.layoutManager = LinearLayoutManager(view.context)

        adapter = NewspaperRecyclerViewAdapter { selectedNewspaper ->
            showSelectedItem(selectedNewspaper)
        }

        binding.recyclerView.adapter = adapter
        displayMovie()
    }


    private fun showSelectedItem(newspaper: NewspaperModel){
        newspaperViewModel.setSelectedNewspaper(newspaper)
        findNavController().navigate(R.id.action_trackerFragment_to_newspaperFragment)
    }

    private fun displayMovie(){
        adapter.setData(newspaperViewModel.getNewspapers())
        adapter.notifyDataSetChanged()
    }

}