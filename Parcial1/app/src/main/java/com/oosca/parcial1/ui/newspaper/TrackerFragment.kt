package com.oosca.parcial1.ui.newspaper

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.oosca.parcial1.R
import com.oosca.parcial1.databinding.FragmentTrackerBinding

class TrackerFragment : Fragment() {

    private lateinit var actionToNewspaper: CardView
    private lateinit var actionToNewNewspaper: FloatingActionButton

    lateinit var binding: FragmentTrackerBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTrackerBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        actionToNewspaper = view.findViewById(R.id.cardView)
        actionToNewNewspaper = view.findViewById(R.id.floatingActionButton)


        actionToNewspaper.setOnClickListener {
            it.findNavController().navigate(R.id.action_trackerFragment_to_newspaperFragment)
        }

        actionToNewNewspaper.setOnClickListener {
            it.findNavController().navigate(R.id.action_trackerFragment_to_newNewspaperFragment)
        }
    }
}