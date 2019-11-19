package com.bettilina.cinemanteca.mocks

import android.content.Context
import com.bettilina.cinemanteca.data.helper.NetworkingManager

class NetworkingManagerMock(context: Context): NetworkingManager(context)  {

    var isNetworkingAvailable = true

    override fun isNetworkOnline() = isNetworkingAvailable
}