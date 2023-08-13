package com.joshjo1.blackjackapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.joshjo1.blackjackapp.R
import com.joshjo1.blackjackapp.models.Card

class CardAdapter(private val cards: List<Card>) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val context = holder.imageView.context
        val id = context.resources.getIdentifier(cards[position].id, "drawable", context.packageName)
        holder.imageView.setImageResource(id)
    }

    override fun getItemCount(): Int {
        return cards.size
    }
}