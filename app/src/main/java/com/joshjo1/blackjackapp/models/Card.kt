package com.joshjo1.blackjackapp.models

import com.joshjo1.blackjackapp.types.RankT
import com.joshjo1.blackjackapp.types.SuitT

class Card(val rank: RankT, suit: SuitT) {

    // format RANK_of_SUIT to match file name
    val id = rank.toString() + "_of_" + suit.toString()
    // note that ACE is always 11, so Hand is responsible for recalculating as 1 when necessary
    val value = if (rank == RankT.ACE) 11 else if (rank.ordinal > 8) 10 else (rank.ordinal + 2)
}