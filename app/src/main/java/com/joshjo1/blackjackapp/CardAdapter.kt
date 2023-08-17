package com.joshjo1.blackjackapp

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.joshjo1.blackjackapp.models.Card

class CardAdapter(private val cards: List<Card>) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    private var lastPosition = -1

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val backView: ImageView = itemView.findViewById(R.id.back)
        val frontView: ImageView = itemView.findViewById(R.id.front)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val context = holder.backView.context

        // Init card views
        holder.backView.setImageResource(R.drawable.card_back)
        val id = context.resources.getIdentifier(cards[position].id, "drawable", context.packageName)
        holder.frontView.setImageResource(id)

        // Animate flipping new card added
        if (position > lastPosition) {
            val scale = context.resources.displayMetrics.density
            holder.backView.cameraDistance = 8000 * scale
            holder.frontView.cameraDistance = 8000 * scale

            val flipOutAnim = AnimatorInflater.loadAnimator(context, R.animator.flip_out) as AnimatorSet
            val flipInAnim = AnimatorInflater.loadAnimator(context, R.animator.flip_in) as AnimatorSet

            flipOutAnim.setTarget(holder.backView)
            flipInAnim.setTarget(holder.frontView)
            flipOutAnim.start()
            flipInAnim.start()

            lastPosition = position
        }
    }

    override fun getItemCount(): Int {
        return cards.size
    }
}