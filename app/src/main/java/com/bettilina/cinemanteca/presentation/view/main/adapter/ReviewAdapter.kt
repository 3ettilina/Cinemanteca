package com.bettilina.cinemanteca.presentation.view.main.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bettilina.cinemanteca.R
import com.bettilina.cinemanteca.data.model.Review
import kotlinx.android.synthetic.main.view_reviews.view.*

class ReviewAdapter: RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {
    var reviews = listOf<Review>()
       /* set(value) {
            notifyDataSetChanged()
        }*/

    var context: Context? = null

    fun setReviewList(reviews:List<Review>){
        this.reviews = reviews
        notifyDataSetChanged()
    }

    //Associate the view with the viewHolder to modify its content
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LayoutInflater.from(parent.context)
            .inflate(R.layout.view_reviews, parent, false)
            .let{view -> ReviewViewHolder(view)}

    override fun getItemCount() = reviews.size

    //Calls the bind function inside the viewHolder for each item on the list
    //so the viewHolder assigns each attribute of the item, to the corresponding fields on the view
    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) =
        holder.bind(reviews[position])

    inner class ReviewViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        //Assigns attributes of the movie to the itemView, to update the view contents.
        fun bind(review: Review){
            itemView.txt_UserName.text = review.author.toUpperCase()
            itemView.txt_ReviewText.text = review.content
        }
    }
}