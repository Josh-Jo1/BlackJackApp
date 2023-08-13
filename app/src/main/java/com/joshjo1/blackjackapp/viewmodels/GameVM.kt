package com.joshjo1.blackjackapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.joshjo1.blackjackapp.models.Card
import com.joshjo1.blackjackapp.models.Hand
import com.joshjo1.blackjackapp.models.Shoe

class GameVM: ViewModel() {

    private val shoe = Shoe(6)
    private val dealer = MutableLiveData(Hand())
    private val player = MutableLiveData(Hand())

    fun getDealer(): LiveData<Hand> = dealer
    fun getDealerCards(): List<Card> = dealer.value!!.getCards()
    fun isDealerBust() = dealer.value!!.isBust()
    fun getPlayer(): LiveData<Hand> = player
    fun getPlayerCards(): List<Card> = player.value!!.getCards()
    fun isPlayerBust() = player.value!!.isBust()

    /**
     * Player hits
     */
    fun playerHit() {
        player.value!!.addCard(shoe.getTopCard())
        player.value = player.value     // so that observer is triggered
    }

    /**
     * Dealer's turn, performed automatically
     */
    fun dealerTurn() {
        while (dealer.value!!.sum < player.value!!.sum) {
            dealer.value!!.addCard(shoe.getTopCard())
        }
    }
}