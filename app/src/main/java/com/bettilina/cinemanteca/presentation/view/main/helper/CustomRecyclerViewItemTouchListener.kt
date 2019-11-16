package com.bettilina.cinemanteca.presentation.view.main.helper

import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import com.bettilina.cinemanteca.R
import com.bettilina.cinemanteca.presentation.helper.BaseRecyclerViewItemTouchListener
import org.jetbrains.annotations.NotNull

class CustomRecyclerViewItemTouchListener(
    recycleView: RecyclerView,
    @IdRes specialIds: IntArray?,
    clickListener: MyCustomClickListener
) :
    BaseRecyclerViewItemTouchListener<CustomRecyclerViewItemTouchListener.MyCustomClickListener>
        (recycleView, specialIds, clickListener) {

    companion object {
        private const val SPECIAL_VIEW_CLICK_AREA_EXTENSION = 5
    }

    private var clickPadding: Int

    init {
        clickPadding = (SPECIAL_VIEW_CLICK_AREA_EXTENSION *
                recycleView.resources.displayMetrics.density).toInt()
    }

    override fun onSpecialViewClick(@NotNull itemView: View,
                                    listPosition: Int) {
        when (itemView.id) {
            R.id.btn_Favorites -> mClickListener.onFavoriteClick(itemView, listPosition)
            R.id.containerMovie -> mClickListener.onMovieClick(itemView, listPosition)
            else -> mClickListener.onClick(itemView, listPosition)
        }
    }

    override fun getSpecialViewClickPadding(): Int = clickPadding

    interface MyCustomClickListener : ClickListener {
        fun onFavoriteClick(view: View, position: Int)

        fun onMovieClick(view: View, position: Int)
    }
}