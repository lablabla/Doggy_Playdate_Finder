package com.lablabla.doggyplaydatefinder.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.lablabla.doggyplaydatefinder.R
import com.lablabla.doggyplaydatefinder.models.User

import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        val button = view.findViewById<Button>(R.id.button_register)
        button.setOnClickListener {
            register()
        }

        val loginTextView = view.findViewById<TextView>(R.id.register_login_textView)
        loginTextView.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
        return view
    }





    private fun register()
    {
        if(username.text.isNullOrEmpty() || password.text.isNullOrEmpty())
        {
            // TODO: Strings
            Toast.makeText(activity, "Empty", Toast.LENGTH_SHORT).show()
            return
        }
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(username.text.toString(), password.text.toString())
            .addOnSuccessListener{
                Toast.makeText(activity, "Login Successful", Toast.LENGTH_SHORT).show()
                storeUserInDB(it.user)
            }
            .addOnFailureListener{
                Toast.makeText(activity, "Login Failed: ${it.localizedMessage}", Toast.LENGTH_SHORT).show()
            }

    }

    private fun storeUserInDB(firebaseUser: FirebaseUser) {
        val user = User(firebaseUser.uid, firebaseUser.displayName, firebaseUser.email)
        val database = FirebaseDatabase.getInstance().reference
        database.child("users").child(user.UID).setValue(user)
            .addOnSuccessListener { switchToMain(user) }
            .addOnFailureListener{
                Toast.makeText(activity, "Saving to db Failed: ${it.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun switchToMain(user: User) {
        Log.d("Login", "Switching to friends with user ${user.UID}")
        findNavController().navigate(R.id.navigation_friends,
            null,
            NavOptions.Builder()
                .setPopUpTo(R.id.navigation_friends, true)
                .build())
    }

}
