package com.bettilina.cinemanteca.presentation.view.movie.reviews

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.bettilina.cinemanteca.R
import androidx.lifecycle.Observer
import com.bettilina.cinemanteca.data.model.Review
import com.bettilina.cinemanteca.presentation.view.main.adapter.ReviewAdapter
import com.bettilina.cinemanteca.utils.Constants
import kotlinx.android.synthetic.main.fragment_reviews.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReviewsFragment : Fragment() {

    private val viewModel: ReviewsViewModel by viewModel()
    private val adapter = ReviewAdapter()
    private var movirId:Int =0

    companion object {
        fun newInstance() =
            ReviewsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vw =  inflater.inflate(R.layout.fragment_reviews, container, false)
        activity?.let{
            val bundle:Bundle? = it.intent.extras
            bundle?.let{bundle->
                movirId = bundle.getInt(Constants.MOVIE_ID,0)
            }
        }

        return vw
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView_Reviews.apply{
            adapter = this@ReviewsFragment.adapter
        }

        //Observe changes of data/flags on the viewModel
        viewModel.reviews.observe(viewLifecycleOwner, Observer(this::reviewsLoaded))
        viewModel.loadReviewsMovies(0)
        
    }

    private fun reviewsLoaded(reviews:List<Review>){
        adapter.reviews = reviews
    }

}
