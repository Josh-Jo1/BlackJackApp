package com.joshjo1.blackjackapp

import androidx.lifecycle.ViewModel
import com.joshjo1.blackjackapp.models.Hand
import com.joshjo1.blackjackapp.models.Shoe

class GameViewModel : ViewModel() {

    private val shoe = Shoe(6)
    private val dealer = Hand()
    private val player = Hand()

    fun getDealerCards() = dealer.getCards()
    fun isDealerBust() = dealer.isBust()
    fun getPlayerCards() = player.getCards()
    fun isPlayerBust() = player.isBust()

    /**
     * Player hits
     */
    fun playerHit() {
        player.addCard(shoe.getTopCard())
    }

    /**
     * Dealer's turn, performed automatically
     */
    fun dealerTurn() {
        while (dealer.sum < player.sum) {
            dealer.addCard(shoe.getTopCard())
        }
    }
}