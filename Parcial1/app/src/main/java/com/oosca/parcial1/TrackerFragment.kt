package com.oosca.parcial1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TrackerFragment : Fragment() {

    private lateinit var actionToNewspaper: CardView
    private lateinit var actionToNewNewspaper: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tracker, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        actionToNewspaper = view.findViewById(R.id.cardView)
        actionToNewNewspaper = view.findViewById(R.id.floatingActionButton)


        actionToNewspaper.setOnClickListener {
            it.findNavController().navigate(R.id.newspaperFragment)
        }

        actionToNewNewspaper.setOnClickListener {
            it.findNavController().navigate(R.id.newNewspaperFragment)
        }
    }
}