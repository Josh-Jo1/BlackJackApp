package com.joshjo1.blackjackapp.types

enum class RankT {
    TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE;

    override fun toString(): String {
        return name.lowercase()
    }
}