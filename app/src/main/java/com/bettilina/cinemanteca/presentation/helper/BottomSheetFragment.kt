package com.bettilina.cinemanteca.presentation.helper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bettilina.cinemanteca.R
import com.bettilina.cinemanteca.presentation.view.main.favorites.FavoritesFragment
import com.bettilina.cinemanteca.presentation.view.main.home.HomeFragment
import com.bettilina.cinemanteca.utils.OrderCriterial
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_bottom_sheet.*

class BottomSheetFragment : BottomSheetDialogFragment() {

    private var fragmentView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentView = inflater.inflate(R.layout.fragment_bottom_sheet, container, false)
        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        txt_OrderAverage.setOnClickListener {
            fragmentManager?.let {
                val homeFragment =
                    it.findFragmentByTag(HomeFragmentTag) as HomeFragment?
                val favsFragment =
                    it.findFragmentByTag(FavsFragmentTag) as FavoritesFragment?
                homeFragment?.let {
                    /*Order by average*/
                    homeFragment.orderView(OrderCriterial.AVERAGE)
                }
                favsFragment?.let {
                    /*Order by average*/
                    favsFragment.orderView(OrderCriterial.AVERAGE)
                }
            }
        }
        txt_OrderText.setOnClickListener {
            fragmentManager?.let {
                val homeFragment =
                    it.findFragmentByTag(HomeFragmentTag) as HomeFragment?
                val favsFragment =
                    it.findFragmentByTag(FavsFragmentTag) as FavoritesFragment?
                homeFragment?.let {
                    /*Order by title*/
                    homeFragment.orderView(OrderCriterial.TITLE)
                }
                favsFragment?.let {
                    /*Order by average*/
                    favsFragment.orderView(OrderCriterial.TITLE)
                }
            }
        }

    }
    companion object {
        const val TAG = "ModalBottomSheet"
        private const val HomeFragmentTag = "HomeFragmentTag"
        private const val FavsFragmentTag = "FavsFragmentTag"
    }
}
