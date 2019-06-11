package com.lablabla.doggyplaydatefinder.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.lablabla.doggyplaydatefinder.R
import com.lablabla.doggyplaydatefinder.activities.MainActivity
import android.view.*
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.lablabla.doggyplaydatefinder.models.User


class FriendsFragment : Fragment() {


    private lateinit var mUser : User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.action_bar_menu_logged_in, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId)
        {
            R.id.menu_logout ->
            {
                FirebaseAuth.getInstance().signOut()
            }
        }
        return super.onOptionsItemSelected(item)
    }

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
        return inflater.inflate(R.layout.fragment_friends, container, false)

    }


}
