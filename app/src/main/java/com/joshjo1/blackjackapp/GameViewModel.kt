package com.joshjo1.blackjackapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.joshjo1.blackjackapp.models.Hand
import com.joshjo1.blackjackapp.models.Shoe

class GameViewModel : ViewModel() {

    private val shoe = Shoe(6)
    private val dealer = Hand()
    private val player = Hand()

    fun getDealerCards() = dealer.getCards()
    fun isDealerBust() = dealer.isBust()
    fun getDealerSum(): LiveData<Int> = dealer.getSum()
    fun getPlayerCards() = player.getCards()
    fun isPlayerBust() = player.isBust()
    fun getPlayerSum(): LiveData<Int> = player.getSum()

    /**
     * Dealer hits
     */
    fun dealerHit() {
        dealer.addCard(shoe.getTopCard())
    }

    /**
     * Player hits
     */
    fun playerHit() {
        player.addCard(shoe.getTopCard())
    }

    /**
     * Reset the GameViewModel
     */
    fun reset() {
        dealer.reset()
        player.reset()
    }
}