package com.lablabla.doggyplaydatefinder.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.lablabla.doggyplaydatefinder.R
import com.lablabla.doggyplaydatefinder.activities.MainActivity
import com.lablabla.doggyplaydatefinder.models.User

class SplashScreenFragment : Fragment() {

    enum class Fragments
    {
        Main,
        Login
    }

    private lateinit var mViewModel : MainActivity.MainViewModel

    private val getUserListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // Get Post object and use the values to update the UI

            val activityEnum = when(val user = dataSnapshot.getValue(User::class.java))
            {
                null -> Fragments.Login
                else ->
                {
                    mViewModel.user = user
                    Fragments.Main
                }
            }

            switchToFragment(activityEnum)

        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Post failed, log a message
            // ...
        }
    }

    private fun switchToFragment(activityEnum: Fragments) {

        val id: Int = when (activityEnum)
        {
            Fragments.Main -> R.id.action_navigation_splash_to_navigation_friends
            Fragments.Login -> R.id.action_navigation_splash_to_navigation_login
        }
        findNavController().navigate(id)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)

        activity?.run {
            mViewModel = ViewModelProviders.of(this).get(MainActivity.MainViewModel::class.java)

        } ?: throw Exception("Invalid Activity")

        FirebaseAuth.getInstance()
        .addAuthStateListener {
            val user = it.currentUser
            if (user != null) {
                FirebaseDatabase.getInstance().reference.child("users").child(user.uid)
                    .addListenerForSingleValueEvent(getUserListener)
            }
            else
            {
                switchToFragment(Fragments.Login)
            }
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)

    }
}
