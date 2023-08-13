package com.joshjo1.blackjackapp.models

import com.joshjo1.blackjackapp.types.RankT
import com.joshjo1.blackjackapp.types.SuitT

class Shoe(numDecks: Int) {

    private val shoe = mutableListOf<Card>()
    private val inPlay = mutableListOf<Card>()

    init {
        // Initialize deck
        val deck = mutableListOf<Card>()
        RankT.values().forEach { rank ->
            SuitT.values().forEach { suit ->
                deck.add(Card(rank, suit))
            }
        }

        // Initialize shoe
        (1..numDecks).forEach { _ ->
            shoe += deck
        }
        shoe.shuffle()
    }

    /**
     * Remove and return the top card from the shoe
     *
     * @return removed card
     */
    fun getTopCard(): Card {
        val topCard = shoe.removeAt(0)
        inPlay.add(topCard)
        return topCard
    }

    /**
     * Reset the shoe
     */
    fun reset() {
        shoe += inPlay
        inPlay.clear()
    }
}