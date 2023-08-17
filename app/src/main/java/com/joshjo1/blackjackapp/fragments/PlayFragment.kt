package com.joshjo1.blackjackapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.joshjo1.blackjackapp.R
import com.joshjo1.blackjackapp.CardAdapter
import com.joshjo1.blackjackapp.GameViewModel

class PlayFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val view = inflater.inflate(R.layout.fragment_play, container, false)

        val viewModel = ViewModelProvider(requireActivity())[GameViewModel::class.java]

        // Set up dealer cards recycler view
        val dealerView = view.findViewById<RecyclerView>(R.id.dealerCardsView)
        val dealerAdapter = CardAdapter(viewModel.getDealerCards())
        dealerView.adapter = dealerAdapter

        // Set up player cards recycler view
        val playerView = view.findViewById<RecyclerView>(R.id.playerCardsView)
        val playerAdapter = CardAdapter(viewModel.getPlayerCards())
        playerView.adapter = playerAdapter

        val standButton = view.findViewById<Button>(R.id.standButton)
        val hitButton = view.findViewById<Button>(R.id.hitButton)
        val splitButton = view.findViewById<Button>(R.id.splitButton)
        val doubleButton = view.findViewById<Button>(R.id.doubleButton)

        // Change button positions if left handed setting on
        if (sharedPref.getBoolean(getString(R.string.leftHandedSet), false)) {
            standButton.updateLayoutParams<ConstraintLayout.LayoutParams> {
                startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                endToEnd = ConstraintLayout.LayoutParams.UNSET
                marginStart = resources.getDimension(R.dimen.controls_edge).toInt()
                marginEnd = 0
            }
            hitButton.updateLayoutParams<ConstraintLayout.LayoutParams> {
                startToEnd = standButton.id
                endToStart = ConstraintLayout.LayoutParams.UNSET
                marginStart = resources.getDimension(R.dimen.controls_between).toInt()
                marginEnd = 0
            }
            splitButton.updateLayoutParams<ConstraintLayout.LayoutParams> {
                startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                endToEnd = ConstraintLayout.LayoutParams.UNSET
                marginStart = resources.getDimension(R.dimen.controls_edge).toInt()
                marginEnd = 0
            }
            doubleButton.updateLayoutParams<ConstraintLayout.LayoutParams> {
                startToEnd = splitButton.id
                endToStart = ConstraintLayout.LayoutParams.UNSET
                marginStart = resources.getDimension(R.dimen.controls_between).toInt()
                marginEnd = 0
            }
        }

        hitButton.setOnClickListener {
            viewModel.playerHit()
            val position = viewModel.getPlayerCards().size - 1
            playerAdapter.notifyItemInserted(position)
            playerView.scrollToPosition(position)

            if (viewModel.isPlayerBust()) {
                hitButton.isEnabled = false
            }
        }

        return view
    }
}