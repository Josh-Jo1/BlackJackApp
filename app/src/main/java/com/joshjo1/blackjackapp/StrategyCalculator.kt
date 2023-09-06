package com.joshjo1.blackjackapp

import com.joshjo1.blackjackapp.models.Hand

object StrategyCalculator {

    /**
     * Determine best strategy for player
     *
     * @param player player's hand
     * @param dealer dealer's hand
     */
    fun get(player: Hand, dealer: Hand): String {
        if (player.getCards().size < 2) return "ERROR"
        if (dealer.getCards().size != 1) return "ERROR"

        val playerFirst = player.getCards()[0]
        val playerSecond = player.getCards()[1]
        val dealerVal = dealer.getCards()[0].value

        // Pairs
        if (player.getCards().size == 2 && playerFirst.rank == playerSecond.rank) {
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
        if (player.isSoftSum()) {
            return when (player.getSum().value!! - 11) {
                2, 3 -> if (dealerVal in 5..6) "Double" else "Hit"
                4, 5, 6 -> if (dealerVal in 4..6) "Double" else "Hit"
                7 -> if (dealerVal <= 6) "Double" else if (dealerVal <= 8) "Stand" else "Hit"
                8, 9, 10 -> "Stand"
                else -> "ERROR"
            }
        }

        // Everything else
        return if (player.getSum().value!! <= 8) "Hit"
        else if (player.getSum().value!! == 9) if (dealerVal <= 6) "Double" else "Hit"
        else if (player.getSum().value!! <= 11) if (dealerVal <= 9) "Double" else "Hit"
        else if (player.getSum().value!! == 12) if (dealerVal in 4..6) "Stand" else "Hit"
        else if (player.getSum().value!! <= 16) if (dealerVal <= 6) "Stand" else "Hit"
        else if (player.getSum().value!! <= 21) "Stand"
        else "ERROR"
    }
}