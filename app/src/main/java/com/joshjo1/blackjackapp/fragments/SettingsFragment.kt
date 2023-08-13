package com.joshjo1.blackjackapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import com.joshjo1.blackjackapp.R

class SettingsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        val leftHandedSwitch = view.findViewById<SwitchCompat>(R.id.leftHandedSwitch)
        leftHandedSwitch.apply {
            isChecked = sharedPref.getBoolean(getString(R.string.leftHandedSet), false)
            setOnCheckedChangeListener { _, isChecked ->
                with (sharedPref.edit()) {
                    putBoolean(getString(R.string.leftHandedSet), isChecked)
                    apply()
                }
            }
        }

        return view
    }
}