package com.lablabla.doggyplaydatefinder.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders

import com.lablabla.doggyplaydatefinder.R
import com.lablabla.doggyplaydatefinder.activities.MainActivity
import com.lablabla.doggyplaydatefinder.models.User

class UserFragment : FragmentWithMenu(R.id.navigation_user) {

    private lateinit var mUser : User
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        activity?.run {
            val vm = ViewModelProviders.of(this).get(MainActivity.MainViewModel::class.java)
            val user = vm.user
            if (user != null)
            {
                mUser = user
            }
        } ?: throw Exception("Invalid Activity")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false)

    }


}
