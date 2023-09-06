package com.joshjo1.blackjackapp

import androidx.lifecycle.ViewModel
import com.joshjo1.blackjackapp.models.Hand
import com.joshjo1.blackjackapp.models.Shoe

class GameViewModel : ViewModel() {

    private val shoe = Shoe(6)

    val dealer = Hand(shoe)
    val player = Hand(shoe)

    /**
     * Reset the GameViewModel
     */
    fun reset() {
        dealer.reset()
        player.reset()
    }
}