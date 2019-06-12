package com.lablabla.doggyplaydatefinder.fragments


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import com.lablabla.doggyplaydatefinder.R
import com.lablabla.doggyplaydatefinder.activities.MainActivity
import com.lablabla.doggyplaydatefinder.models.User

class LoginFragment : Fragment() {

    private lateinit var mViewModel : MainActivity.MainViewModel

    private val getUserListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            val user = dataSnapshot.getValue(User::class.java)
            mViewModel.user = user
            findNavController().navigate(R.id.action_loginFragment_to_navigation_friends,
                null,
                NavOptions.Builder()
                    .setPopUpTo(R.id.navigation_login, true)
                    .build())
        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Post failed, log a message
            // ...
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        activity?.run {
            mViewModel = ViewModelProviders.of(this).get(MainActivity.MainViewModel::class.java)
        }
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        val registerTextView = view.findViewById<TextView>(R.id.login_register_textView)
        registerTextView.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }



        val email = view.findViewById<EditText>(R.id.login_email_editText)
        val pwd = view.findViewById<EditText>(R.id.login_paassword_editText)

        view.findViewById<Button>(R.id.button_login).setOnClickListener {
            if (email.text.isNotEmpty() && pwd.text.isNotEmpty()) {
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(
                        email.text.toString(),
                        pwd.text.toString()
                    )
                    .addOnSuccessListener {

                        FirebaseDatabase.getInstance().reference.child("users").child(it.user.uid)
                            .addListenerForSingleValueEvent(getUserListener)
                    }
                    .addOnFailureListener {
                        Toast.makeText(activity, "Login failed: ${it.localizedMessage}", Toast.LENGTH_LONG).show()
                    }
                val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view!!.windowToken, 0)
            }
        }

        return view
    }


}
