package com.example.beehivecareappproject

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DonateIntoSystem : Fragment() {

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var pointsTextView: TextView  // TextView to display points

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase Auth and Database
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("Users")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_donate_into_system, container, false)

        // Initialize UI elements
        val button100: Button = view.findViewById(R.id.button100)
        val button300: Button = view.findViewById(R.id.button300)
        val button500: Button = view.findViewById(R.id.button500)
        val button1000: Button = view.findViewById(R.id.button1000)
        val customAmountField: EditText = view.findViewById(R.id.editTextNumber)
        val donateNowButton: Button = view.findViewById(R.id.button7)
        pointsTextView = view.findViewById(R.id.pointsTextView)

        // Load current points on fragment start
        loadUserPoints()

        // Set donation button actions
        button100.setOnClickListener { recordDonation("100") }
        button300.setOnClickListener { recordDonation("300") }
        button500.setOnClickListener { recordDonation("500") }
        button1000.setOnClickListener { recordDonation("1000") }

        donateNowButton.setOnClickListener {
            val customAmount = customAmountField.text.toString().trim()
            if (customAmount.isNotEmpty()) {
                recordDonation(customAmount)
            } else {
                Toast.makeText(context, "Please enter a custom amount", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun loadUserPoints() {
        val user = auth.currentUser
        if (user != null) {
            // Access the user's points in the database
            database.child(user.uid).child("points").get()
                .addOnSuccessListener { dataSnapshot ->
                    val points = dataSnapshot.getValue(Int::class.java) ?: 0
                    pointsTextView.text = "Points: $points"
                }
                .addOnFailureListener { e ->
                    Log.e("DonateIntoSystem", "Failed to load points", e)
                }
        }
    }

    private fun recordDonation(amount: String) {
        val user = auth.currentUser
        if (user != null) {
            // Prepare the donation data
            val donationData = mapOf(
                "amount" to amount,
                "userId" to user.uid,
                "timestamp" to System.currentTimeMillis()
            )

            // Store the donation and then update the user's points
            val donationsRef = FirebaseDatabase.getInstance().getReference("Donations")
            donationsRef.child(user.uid).push().setValue(donationData)
                .addOnSuccessListener {
                    Toast.makeText(context, "Donation of R$amount recorded!", Toast.LENGTH_SHORT).show()
                    incrementUserPoints()
                }
                .addOnFailureListener { e ->
                    Log.e("DonateIntoSystem", "Failed to record donation", e)
                    Toast.makeText(context, "Failed to record donation", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(context, "Please log in to donate", Toast.LENGTH_SHORT).show()
        }
    }

    private fun incrementUserPoints() {
        val user = auth.currentUser
        if (user != null) {
            val userPointsRef = database.child(user.uid).child("points")
            userPointsRef.get()
                .addOnSuccessListener { dataSnapshot ->
                    val currentPoints = dataSnapshot.getValue(Int::class.java) ?: 0
                    val newPoints = currentPoints + 1
                    userPointsRef.setValue(newPoints)
                        .addOnSuccessListener {
                            pointsTextView.text = "Points: $newPoints"  // Update points display
                            Toast.makeText(context, "You've earned 1 point!", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { e ->
                            Log.e("DonateIntoSystem", "Failed to update points", e)
                        }
                }
                .addOnFailureListener { e ->
                    Log.e("DonateIntoSystem", "Failed to retrieve points", e)
                }
        }
    }
}


