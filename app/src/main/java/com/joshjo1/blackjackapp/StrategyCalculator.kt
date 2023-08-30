package com.joshjo1.blackjackapp

import com.joshjo1.blackjackapp.models.Card

object StrategyCalculator {

    /**
     * Determine best strategy for player
     *
     * @param pCards player's cards
     * @param pSum player's sum
     * @param pSoft player soft sum
     * @param dCards dealer's cards
     * @return optimal strategy for player
     */
    fun get(pCards: List<Card>, pSum: Int, pSoft: Boolean, dCards: List<Card>): String {
        if (pCards.size < 2) return "ERROR"
        if (dCards.size != 1) return "ERROR"

        val playerFirst = pCards[0]
        val playerSecond = pCards[1]
        val dealerVal = dCards[0].value

        // Pairs
        if (pCards.size == 2 && playerFirst.rank == playerSecond.rank) {
            return when (playerFirst.value) {
                2, 3, 6, 7 -> if (dealerVal <= 7) "Split" else "Hit"
                4 -> if (dealerVal in 5..6) "Split" else "Hit"
                5 -> if (dealerVal <= 9) "Double" else "Hit"
                8 -> if (dealerVal <= 9) "Split" else "Hit"
                9 -> if (dealerVal <= 9) "Split" else "Stand"
                10 -> "Stand"
                11 -> if (dealerVal <= 10) "Split" else "Hit"
                else -> "ERROR"
            }
        }

        // Soft sum
        if (pSoft) {
            return when (pSum - 11) {
                2, 3 -> if (dealerVal in 5..6) "Double" else "Hit"
                4, 5, 6 -> if (dealerVal in 4..6) "Double" else "Hit"
                7 -> if (dealerVal <= 6) "Double" else if (dealerVal <= 8) "Stand" else "Hit"
                8, 9, 10 -> "Stand"
                else -> "ERROR"
            }
        }

        // Everything else
        return if (pSum <= 8) "Hit"
        else if (pSum == 9) if (dealerVal <= 6) "Double" else "Hit"
        else if (pSum <= 11) if (dealerVal <= 9) "Double" else "Hit"
        else if (pSum == 12) if (dealerVal in 4..6) "Stand" else "Hit"
        else if (pSum <= 16) if (dealerVal <= 6) "Stand" else "Hit"
        else if (pSum <= 21) "Stand"
        else "ERROR"
    }
}