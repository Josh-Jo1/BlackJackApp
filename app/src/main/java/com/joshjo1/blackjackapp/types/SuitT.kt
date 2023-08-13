package com.joshjo1.blackjackapp.types

enum class SuitT {
    CLUBS, DIAMONDS, HEARTS, SPADES;

    override fun toString(): String {
        return name.lowercase()
    }
}