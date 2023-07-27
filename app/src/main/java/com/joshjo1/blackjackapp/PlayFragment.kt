package com.joshjo1.blackjackapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import com.joshjo1.blackjackapp.Utils.dpToInt

class PlayFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val view = inflater.inflate(R.layout.fragment_play, container, false)

        val standButton = view.findViewById<Button>(R.id.standButton)
        val hitButton = view.findViewById<Button>(R.id.hitButton)
        val splitButton = view.findViewById<Button>(R.id.splitButton)
        val doubleButton = view.findViewById<Button>(R.id.doubleButton)

        // Change button positions if left handed setting on
        if (sharedPref.getBoolean(getString(R.string.leftHandedSet), false)) {
            standButton.updateLayoutParams<ConstraintLayout.LayoutParams> {
                startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                endToEnd = ConstraintLayout.LayoutParams.UNSET
                marginStart = dpToInt(50F)
                marginEnd = 0
            }
            hitButton.updateLayoutParams<ConstraintLayout.LayoutParams> {
                startToEnd = standButton.id
                endToStart = ConstraintLayout.LayoutParams.UNSET
                marginStart = dpToInt(15F)
                marginEnd = 0
            }
            splitButton.updateLayoutParams<ConstraintLayout.LayoutParams> {
                startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                endToEnd = ConstraintLayout.LayoutParams.UNSET
                marginStart = dpToInt(50F)
                marginEnd = 0
            }
            doubleButton.updateLayoutParams<ConstraintLayout.LayoutParams> {
                startToEnd = splitButton.id
                endToStart = ConstraintLayout.LayoutParams.UNSET
                marginStart = dpToInt(15F)
                marginEnd = 0
            }
        }

        return view
    }

    /**
     * Wrapper for Utils.dpToInt
     *
     * @param value in dp
     */
    private fun dpToInt(value: Float): Int {
        return dpToInt(value, resources.displayMetrics)
    }
}