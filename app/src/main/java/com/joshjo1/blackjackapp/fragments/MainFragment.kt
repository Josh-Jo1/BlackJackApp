package com.joshjo1.blackjackapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.joshjo1.blackjackapp.R

class MainFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        view.findViewById<Button>(R.id.playButton).setOnClickListener {
            findNavController().navigate(R.id.main_to_play)
        }

        view.findViewById<Button>(R.id.settingsButton).setOnClickListener {
            findNavController().navigate(R.id.main_to_settings)
        }

        return view
    }
}