package com.lablabla.doggyplaydatefinder.fragments


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.lablabla.doggyplaydatefinder.R

open class FragmentWithMenu(resID: Int) : Fragment() {

    private val mResID :Int = resID

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
                findNavController().navigate(R.id.navigation_login,
                    null,
                    NavOptions.Builder()
                        .setPopUpTo(mResID, true)
                        .build())
            }
        }
        return super.onOptionsItemSelected(item)
    }


}
