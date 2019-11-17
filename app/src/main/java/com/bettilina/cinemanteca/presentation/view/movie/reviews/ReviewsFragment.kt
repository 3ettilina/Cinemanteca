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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bettilina.cinemanteca.data.model.Review
import com.bettilina.cinemanteca.presentation.view.main.adapter.ReviewAdapter
import com.bettilina.cinemanteca.utils.Constants
import kotlinx.android.synthetic.main.fragment_reviews.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReviewsFragment : Fragment() {

    private val viewModel: ReviewsViewModel by viewModel()
    private val adapter = ReviewAdapter()
    private var movieId: Int = 0

    companion object {
        fun newInstance() =
            ReviewsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_reviews, container, false)

        activity?.let { fragment ->
            val bundle: Bundle? = fragment.intent.extras
            bundle?.let{
                movieId = it.getInt(Constants.MOVIE_ID)
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView_Reviews.apply{
            layoutManager = GridLayoutManager(context,1,GridLayoutManager.VERTICAL,false)
            adapter = this@ReviewsFragment.adapter
        }

        //Observe changes of data/flags on the viewModel
        viewModel.reviews.observe(viewLifecycleOwner, Observer(this::reviewsLoaded))

        viewModel.loadReviewsMovies(movieId)
        
    }

    private fun reviewsLoaded(reviews: List<Review>){
        if(reviews.size>0){
            adapter.setReviewList(reviews)
        }else{
            txt_TitleReview.text=getString(R.string.sorry_not_reviews_yet)
        }
    }

}
