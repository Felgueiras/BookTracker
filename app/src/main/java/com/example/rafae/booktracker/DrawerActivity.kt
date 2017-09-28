package com.example.rafae.booktracker

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.NotificationCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.android.notificationchannels.MainActivity
import com.example.android.notificationchannels.NotificationHelper
import com.example.rafae.booktracker.views.BooksListView
import com.example.rafae.booktracker.views.NotifTest
import kotlinx.android.synthetic.main.activity_drawer.*
import kotlinx.android.synthetic.main.app_bar_drawer.*

class DrawerActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawer)
        setSupportActionBar(toolbar)

//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        // default fragment
        // Handle navigation view item clicks here.
        var endFragment: Fragment? = null


        endFragment = NotifTest.Companion.newInstance(1, supportFragmentManager) as Fragment


        var fragmentManager = getSupportFragmentManager()
        fragmentManager.beginTransaction()
                .replace(R.id.current_fragment, endFragment, "initial_tag")
                .commit();
    }






    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.drawer, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        var endFragment: Fragment? = null

        when (item.itemId) {
            R.id.goodreadsBooks -> {
                endFragment = BooksListView.Companion.newInstance(1, supportFragmentManager) as Fragment
            }
            R.id.currentlyReading -> {
                endFragment = CurrentlyReadingList.Companion.newInstance(1, supportFragmentManager) as Fragment
            }
            R.id.notifTest -> {
                endFragment = NotifTest.Companion.newInstance(1, supportFragmentManager) as Fragment
            }
            R.id.spotify -> {

            }
        }

        /**
         * } else if (id == R.id.raspPi) {
        endFragment = TaskListActivity.Companion.newInstance(1);
        } else if (id == R.id.kotlin) {
        //            endFragment = new KotlinStuffMain();
        } else if (id == R.id.settings) {
        // open settings activity
        Intent i = new Intent(this, SettingsActivity.class);
        startActivity(i);
        return true;
        }
         **/

        if (endFragment != null) {
            var fragmentManager = getSupportFragmentManager()
            fragmentManager.beginTransaction()
                    .replace(R.id.current_fragment, endFragment, "initial_tag")
                    .commit();
        }



        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
