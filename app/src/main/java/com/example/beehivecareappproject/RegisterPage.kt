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


class RegisterPage : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth  // Declare FirebaseAuth
    private lateinit var name: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var confirmPassword: EditText
    private lateinit var buttonRegister: Button
    private lateinit var loginText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        name = findViewById(R.id.nameField)
        email = findViewById(R.id.registerEmail)
        password = findViewById(R.id.registerPassword)
        confirmPassword = findViewById(R.id.registerConfirmPassword)
        buttonRegister = findViewById(R.id.buttonRegister)
        loginText = findViewById(R.id.gologinText)

        // Click listener for login link
        loginText.setOnClickListener {
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
        }

        // Click listener for register button
        buttonRegister.setOnClickListener {
            val userEmail = email.text.toString()
            val userPassword = password.text.toString()
            val userConfirmPassword = confirmPassword.text.toString()

            if (userPassword == userConfirmPassword) {
                // Call Firebase register function
                registerUser(userEmail, userPassword)
            } else {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Function to register the user
    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Registration successful
                    val user: FirebaseUser? = auth.currentUser
                    Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()

                    // You can also store additional information like the user's name in Firebase Firestore if needed

                    // Navigate to another activity after registration
                    val intent = Intent(this, MainActivity2::class.java)
                    startActivity(intent)
                    finish()  // Close the RegisterPage so user can't go back
                } else {
                    // If registration fails, display a message
                    Toast.makeText(this, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}