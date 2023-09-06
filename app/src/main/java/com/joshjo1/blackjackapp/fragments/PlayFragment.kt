package com.joshjo1.blackjackapp.fragments

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.joshjo1.blackjackapp.R
import com.joshjo1.blackjackapp.CardAdapter
import com.joshjo1.blackjackapp.GameViewModel
import com.joshjo1.blackjackapp.StrategyCalculator

class PlayFragment : Fragment() {

    private lateinit var viewModel: GameViewModel
    private lateinit var dealerView: RecyclerView
    private lateinit var dealerAdapter: CardAdapter
    private lateinit var dealerStatusView: TextView
    private lateinit var playerView: RecyclerView
    private lateinit var playerAdapter: CardAdapter
    private lateinit var playerStatusView: TextView
    private lateinit var standButton: Button
    private lateinit var hitButton: Button
    private lateinit var splitButton: Button
    private lateinit var doubleButton: Button
    private lateinit var hintButton: ImageButton

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val view = inflater.inflate(R.layout.fragment_play, container, false)

        viewModel = ViewModelProvider(requireActivity())[GameViewModel::class.java]

        // Set up dealer cards recycler view
        dealerView = view.findViewById(R.id.dealerCardsView)
        dealerAdapter = CardAdapter(viewModel.dealer.getCards())
        dealerView.adapter = dealerAdapter

        // Set up player cards recycler view
        playerView = view.findViewById(R.id.playerCardsView)
        playerAdapter = CardAdapter(viewModel.player.getCards())
        playerView.adapter = playerAdapter

        // Set up dealer sum view
        val dealerSumView = view.findViewById<TextView>(R.id.dealerSumView)
        val dealerSumObserver = Observer<Int> {
            dealerSumView.text = if (it == 0) "" else it.toString()
        }
        viewModel.dealer.getSum().observe(viewLifecycleOwner, dealerSumObserver)

        // Set up player sum view
        val playerSumView = view.findViewById<TextView>(R.id.playerSumView)
        val playerSumObserver = Observer<Int> {
            playerSumView.text = if (it == 0) "" else it.toString()
        }
        viewModel.player.getSum().observe(viewLifecycleOwner, playerSumObserver)

        // Set up status views
        dealerStatusView = view.findViewById(R.id.dealerStatusView)
        playerStatusView = view.findViewById(R.id.playerStatusView)

        // Set up actions buttons
        standButton = view.findViewById(R.id.standButton)
        hitButton = view.findViewById(R.id.hitButton)
        splitButton = view.findViewById(R.id.splitButton)
        doubleButton = view.findViewById(R.id.doubleButton)
        hintButton = view.findViewById(R.id.hintButton)

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
            hintButton.updateLayoutParams<ConstraintLayout.LayoutParams> {
                startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                endToEnd = ConstraintLayout.LayoutParams.UNSET
                marginStart = resources.getDimension(R.dimen.hint_end).toInt()
                marginEnd = 0
            }
        }

        // Set up start/restart button
        val startButton = view.findViewById<ImageButton>(R.id.startButton)
        startButton.setOnClickListener {
            startButton.setBackgroundResource(R.drawable.refresh)
            startButton.contentDescription = resources.getString(R.string.restart)

            dealerSumView.visibility = View.VISIBLE
            playerSumView.visibility = View.VISIBLE

            dealerStatusView.text = ""
            playerStatusView.text = ""

            // Reset order matters
            dealerAdapter.reset()
            playerAdapter.reset()
            viewModel.reset()

            // Deal player
            viewModel.player.addCard()
            playerAdapter.notifyItemInserted(0)
            // Deal dealer
            handler.postDelayed({
                viewModel.dealer.addCard()
                dealerAdapter.notifyItemInserted(0)
            }, resources.getInteger(R.integer.card_flip_duration_half).toLong())
            // Deal player
            handler.postDelayed({
                viewModel.player.addCard()
                playerAdapter.notifyItemInserted(1)
                enableButtons(true)
            }, resources.getInteger(R.integer.card_flip_duration_half).toLong() * 2)
        }

        standButton.setOnClickListener {
            enableButtons(false)
            dealerTurn()
        }

        hitButton.setOnClickListener {
            viewModel.player.addCard()
            val endPosition = viewModel.player.getCards().size - 1
            playerAdapter.notifyItemInserted(endPosition)
            playerView.scrollToPosition(endPosition)

            if (viewModel.player.isBust()) {
                enableButtons(false)
                playerStatusView.text = resources.getString(R.string.bust)
            }
        }

        hintButton.setOnClickListener {
            val strategy = StrategyCalculator.get(viewModel.player, viewModel.dealer)
            Toast.makeText(context, strategy, Toast.LENGTH_LONG).show()
        }

        return view
    }

    /**
     * Dealer's turn
     */
    private fun dealerTurn() {
        if (viewModel.dealer.getSum().value!! < viewModel.player.getSum().value!!) {
            val endPosition = viewModel.dealer.getCards().size
            handler.postDelayed({
                viewModel.dealer.addCard()
                dealerAdapter.notifyItemInserted(endPosition)
                dealerView.scrollToPosition(endPosition)
                dealerTurn()
            }, resources.getInteger(R.integer.card_flip_duration_half).toLong() * (endPosition - 1))
        } else {
            if (viewModel.dealer.isBust()) {
                playerStatusView.text = resources.getString(R.string.winner)
            } else {
                dealerStatusView.text = resources.getString(R.string.winner)
            }
        }
    }

    /**
     * Enable all action buttons
     *
     * @param enable true will enable buttons
     */
    private fun enableButtons(enable: Boolean) {
        standButton.isEnabled = enable
        hitButton.isEnabled = enable
//        splitButton.isEnabled = enable
//        doubleButton.isEnabled = enable
        hintButton.visibility = if (enable) View.VISIBLE else View.INVISIBLE
    }
}