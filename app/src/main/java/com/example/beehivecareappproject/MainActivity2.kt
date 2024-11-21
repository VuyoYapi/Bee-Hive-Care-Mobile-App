package com.example.beehivecareappproject

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView

class MainActivity2 : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        drawerLayout = findViewById(R.id.drawer_layout)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        // Change color of "Login" and "Donate" menu items programmatically
        val menu = navigationView.menu
        val loginItem = menu.findItem(R.id.nav_login)
        val donateItem = menu.findItem(R.id.nav_donate)

        val green = Color.parseColor("#4CAF50")
        val loginTitle = SpannableString(loginItem.title).apply {
            setSpan(ForegroundColorSpan(green), 0, length, 0)
        }
        loginItem.title = loginTitle

        val donateTitle = SpannableString(donateItem.title).apply {
            setSpan(ForegroundColorSpan(green), 0, length, 0)
        }
        donateItem.title = donateTitle

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        if (savedInstanceState == null) {
            replaceFragment(Home2Fragment())
            navigationView.setCheckedItem(R.id.nav_home1)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home1 -> replaceFragment(Home2Fragment())
            R.id.nav_donateSystem -> replaceFragment(DonateIntoSystem())
            R.id.nav_point -> replaceFragment(PointSystemFragment())
            R.id.nav_graph -> replaceFragment(GraphFragment())
            R.id.nav_login -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, HomeFragment())
                    .addToBackStack(null)  // Allows the user to navigate back
                    .commit()
            }
            R.id.nav_donate -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.tshupehospice.co.za/payfastdonate.html"))
                startActivity(intent)
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}
