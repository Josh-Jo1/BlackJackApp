package com.joshjo1.blackjackapp.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.joshjo1.blackjackapp.types.RankT

class Hand {

    private val BUST = 22

    private val cards = mutableListOf<Card>()
    private var numAces = 0
    private var sum = MutableLiveData(0)

    fun getCards(): List<Card> = cards
    fun isSoftSum() = numAces != 0
    fun getSum(): LiveData<Int> = sum
    fun isBust() = sum.value!! >= BUST

    /**
     * Add card to hand
     *
     * @param card to add
     */
    fun addCard(card: Card) {
        cards.add(card)
        if (card.rank == RankT.ACE) numAces++
        sum.value = sum.value!! + card.value
        while (sum.value!! >= BUST && numAces > 0) {
            sum.value = sum.value!! - 10    // ACE is now 1
            numAces--
        }
    }

    /**
     * Reset the hand
     */
    fun reset() {
        cards.clear()
        numAces = 0
        sum.value = 0
    }
}