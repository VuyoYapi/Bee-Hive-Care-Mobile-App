package com.example.beehivecareappproject

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer_layout)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navigationView = findViewById<NavigationView>(R.id.nav_view) // Initialize navigationView here
        navigationView.setNavigationItemSelectedListener(this)

        // Change color of "Login" and "Donate" menu items to green programmatically
        val menu = navigationView.menu
        val loginItem = menu.findItem(R.id.nav_login)
        val donateItem = menu.findItem(R.id.nav_donate)

        // Create green color SpannableString for the titles
        val green = Color.parseColor("#4CAF50") // Your green color
        val loginTitle = SpannableString(loginItem.title)
        loginTitle.setSpan(ForegroundColorSpan(green), 0, loginTitle.length, 0)
        loginItem.title = loginTitle

        val donateTitle = SpannableString(donateItem.title)
        donateTitle.setSpan(ForegroundColorSpan(green), 0, donateTitle.length, 0)
        donateItem.title = donateTitle

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Set default fragment on first launch
        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
            navigationView.setCheckedItem(R.id.nav_home) // Set nav_home as default checked item
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation menu item clicks here
        when (item.itemId) {
            R.id.nav_home -> replaceFragment(HomeFragment())
            R.id.nav_about -> replaceFragment(AboutFragment())
            R.id.nav_training -> replaceFragment(TrainingFragment())
            R.id.nav_wishlist -> replaceFragment(WishListFragment())
            R.id.nav_income_generator -> replaceFragment(IncomeGeneratorFragment())
            R.id.nav_contact -> replaceFragment(ContactFragment())
            R.id.nav_donation_options -> replaceFragment(DonationOptionsFragment())
            R.id.nav_gallery -> replaceFragment(GalleryFragment())
            R.id.nav_login -> {
                // Navigate to LoginActivity
                val intent = Intent(this, LoginPage::class.java)
                startActivity(intent)
            }
            R.id.nav_donate -> {
                // Open the donation link in a browser
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("https://www.tshupehospice.co.za/payfastdonate.html")
                startActivity(intent)
            }
            // Add other cases as needed
        }
        drawerLayout.closeDrawer(GravityCompat.START) // Close the drawer after selection
        return true
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START) // Close the drawer if it's open
        } else {
            onBackPressedDispatcher.onBackPressed() // Handle back press normally
        }
    }
}
