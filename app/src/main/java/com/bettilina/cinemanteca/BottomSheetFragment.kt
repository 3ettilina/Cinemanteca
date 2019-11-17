package com.bettilina.cinemanteca


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bettilina.cinemanteca.presentation.view.main.home.HomeFragment
import com.bettilina.cinemanteca.utils.OrderCriterial
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_bottom_sheet.*


class BottomSheetFragment() : BottomSheetDialogFragment() {

    private var fragmentView: View? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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
                    it.findFragmentByTag(HomeFragmentTag) as HomeFragment
                val favsFragment =
                    it.findFragmentByTag(HomeFragmentTag) as HomeFragment
                homeFragment?.let {
                    homeFragment.isVisible?.let {
                        /*Order by average*/
                        val rv = homeFragment.orderView(OrderCriterial.AVERAGE)
                    }
                }
                favsFragment?.let {
                    favsFragment.isVisible?.let {
                        /*Order by title*/
                    }
                }
            }

        }
        txt_OrderText.setOnClickListener {
            fragmentManager?.let {
                val homeFragment =
                    it.findFragmentByTag(HomeFragmentTag) as HomeFragment
                val favsFragment =
                    it.findFragmentByTag(HomeFragmentTag) as HomeFragment
                homeFragment?.let {
                    homeFragment.isVisible?.let {
                        /*Order by title*/
                        val rv = homeFragment.orderView(OrderCriterial.TITLE)
                    }
                }
                favsFragment?.let {
                    favsFragment.isVisible?.let {
                        /*Order by average*/
                    }
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
