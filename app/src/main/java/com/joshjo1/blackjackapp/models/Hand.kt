package com.joshjo1.blackjackapp.models

import com.joshjo1.blackjackapp.types.RankT

class Hand {

    private val BUST = 22

    private val cards = mutableListOf<Card>()
    private var numAces = 0
    var sum = 0

    fun getCards(): List<Card> = cards

    /**
     * Add card to hand and check if busted
     *
     * @param card to add
     */
    fun addCard(card: Card) {
        cards.add(card)
        if (card.rank == RankT.ACE) numAces++
        sum += card.value
        while (sum >= BUST && numAces > 0) {
            sum -= 10   // ACE is now 1
            numAces--
        }
    }

    /**
     * Returns if hand is busted
     *
     * @return if hand is busted
     */
    fun isBust(): Boolean {
        return sum >= BUST
    }

    /**
     * Return all cards (to shoe) before next hand
     */
    fun returnCards() {
        cards.clear()
    }
}