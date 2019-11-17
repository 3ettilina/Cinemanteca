package com.bettilina.cinemanteca.presentation.view.movie.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bettilina.cinemanteca.R
import com.bettilina.cinemanteca.presentation.view.movie.reviews.ReviewsFragment
import com.bettilina.cinemanteca.utils.Constants
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.android.synthetic.main.view_movie.view.*
import org.koin.android.ext.android.inject

class MovieFragment : Fragment(){

    private val viewModel: MovieViewModel by inject()

    private var movieId: Int = 0
    private var title: String? = ""
    private var average: String? = ""
    private var imageDir: String? = ""
    private var year: String? = ""
    private var description: String? = ""
    private var isFavorite: Int? = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie, container, false)

            //Get bundle data
            activity?.let{fragment ->
                val bundle: Bundle? = fragment.intent.extras
                bundle?.let {
                    average = it.getString(Constants.MOVIE_AVERAGE)
                    movieId = it.getInt(Constants.MOVIE_ID)
                    title = it.getString(Constants.MOVIE_TITLE)
                    imageDir = it.getString(Constants.MOVIE_IMAGE_DIR)
                    year = it.getString(Constants.MOVIE_YEAR)
                    description = it.getString(Constants.MOVIE_DESCRIPTION)
                    isFavorite = it.getInt(Constants.MOVIE_IS_FAVORITE)
                }
            }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewData(view)
        setFavoriteButtonCheckedIfFavorite()
        setOnFavoritesClickListener()
        setOnReviewsClickListener()
    }

    private fun setViewData(view: View){
        txt_TitleMovie.text = title
        txt_RankMovie.text = average
        txt_YearMovie.text = year
        txt_DescriptionMovie.text = description

        Glide
            .with(view)
            .load(imageDir)
            .centerCrop()
            .into(view.findViewById(R.id.img_PosterMovie))
    }

    private fun setFavoriteButtonCheckedIfFavorite(){
        if(viewModel.isFavoriteMovie(movieId) == 1){
            chk_Fav.isChecked = true
            isFavorite = 1
        }

        btn_Favorites.text =
            if(isFavorite == 1) getString(R.string.remove_favs_button)
            else getString(R.string.add_favs_button)
    }

    private fun setOnReviewsClickListener(){
        btn_Reviews.setOnClickListener{
            val bundle = Bundle()
            bundle.putInt(Constants.MOVIE_ID, movieId)

            val reviewsFragment = ReviewsFragment.newInstance()
            reviewsFragment.arguments = bundle

            fragmentManager!!
                .beginTransaction()
                .replace(R.id.containerMovie, reviewsFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun setOnFavoritesClickListener(){
        btn_Favorites.setOnClickListener {
            //Check to handle button state
            chk_Fav.isChecked = !chk_Fav.isChecked

            if(chk_Fav.isChecked){
                addToFavorites()
            } else {
                removeFromFavorites()
            }
        }
    }

    private fun addToFavorites(){
        //Update movie as favorite one on database
        viewModel.addFavoriteMovie(movieId)

        //Show message to user
        Toast.makeText(view?.context,
            "Movie added to favorites!",
            Toast.LENGTH_LONG).show()

        setFavoriteButtonCheckedIfFavorite()

        //Change the button text
        btn_Favorites.setText(R.string.remove_favs_button)
    }

    private fun removeFromFavorites(){
        //Update movie as favorite one on database
        viewModel.removeFavoriteMovie(movieId)

        //Show message to user
        Toast.makeText(view?.context,
            "Movie removed from favorites!",
            Toast.LENGTH_LONG).show()

        setFavoriteButtonCheckedIfFavorite()

        btn_Favorites.setText(R.string.add_favs_button)
    }
}

