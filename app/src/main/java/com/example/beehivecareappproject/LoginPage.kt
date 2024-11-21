package com.example.beehivecareappproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import android.widget.Toast

class LoginPage : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth  // Declare FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        val email = findViewById<EditText>(R.id.loginEmail)
        val password = findViewById<EditText>(R.id.loginPassword)
        val buttonLogin = findViewById<Button>(R.id.buttonLogin)
        val registerText = findViewById<TextView>(R.id.registerText)

        // Set onClickListener for Register TextView to navigate to the RegisterPage
        registerText.setOnClickListener {
            val intent = Intent(this, RegisterPage::class.java)
            startActivity(intent)
        }

        // Set onClickListener for the login button
        buttonLogin.setOnClickListener {
            val userEmail = email.text.toString()
            val userPassword = password.text.toString()

            // Perform login with Firebase Authentication
            loginUser(userEmail, userPassword)
        }
    }

    // Function to log in the user
    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // If login is successful, get the current user
                    val user: FirebaseUser? = auth.currentUser
                    Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()

                    // Navigate to MainActivity2 after successful login
                    val intent = Intent(this, MainActivity2::class.java)
                    startActivity(intent)
                    finish()  // Close the LoginPage so the user can't go back
                } else {
                    // If login fails, show an error message
                    Toast.makeText(this, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}