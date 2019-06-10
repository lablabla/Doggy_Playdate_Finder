package com.lablabla.doggyplaydatefinder

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.lablabla.doggyplaydatefinder.models.User

import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    val getUserLinstener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // Get Post object and use the values to update the UI
            val user = dataSnapshot.getValue(User::class.java)
            if (user != null)
            {
                switchToLogin(user)
            }
            else
            {
                // TODO
            }

        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Post failed, log a message
            // ...
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null)
        {
            getUserFromDB(user.uid)
        }
        button_register.setOnClickListener()
        {
            register()
        }
    }

    private fun getUserFromDB(userID: String) {
        FirebaseDatabase.getInstance().reference.child("users").child(userID)
            .addListenerForSingleValueEvent(getUserLinstener)
    }


    private fun register()
    {
        if(username.text.isNullOrEmpty() || password.text.isNullOrEmpty())
        {
            // TODO: Strings
            Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show()
            return
        }
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(username.text.toString(), password.text.toString())
            .addOnSuccessListener{
                Toast.makeText(this, "Register Successful", Toast.LENGTH_SHORT).show()
                storeUserInDB(it.user)
            }
            .addOnFailureListener{
                Toast.makeText(this, "Register Failed: ${it.localizedMessage}", Toast.LENGTH_SHORT).show()
            }

    }

    private fun storeUserInDB(firebaeUser: FirebaseUser) {
        val user = User(firebaeUser.uid, firebaeUser.displayName, firebaeUser.email)
        val database = FirebaseDatabase.getInstance().reference
        database.child("users").child(user.UID).setValue(user)
            .addOnSuccessListener { switchToLogin(user) }
            .addOnFailureListener{
                Toast.makeText(this, "Saving to db Failed: ${it.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun switchToLogin(user: User) {
        Log.d("Register", "Switching to login with user ${user.UID}")
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("user", user)
        startActivity(intent)
        finish()
    }

}
