package com.example.beehivecareappproject

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class PointSystemFragment : Fragment() {

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var pointsTextView: TextView
    private lateinit var statusTextView: TextView

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
        val view = inflater.inflate(R.layout.fragment_point_system, container, false)

        // Initialize UI elements
        pointsTextView = view.findViewById(R.id.pointsTextView)
        statusTextView = view.findViewById(R.id.textStatus)

        // Load and display user points and status
        loadUserPointsAndStatus()

        return view
    }

    private fun loadUserPointsAndStatus() {
        val user = auth.currentUser
        if (user != null) {
            // Retrieve the user's points from Firebase
            database.child(user.uid).child("points").get()
                .addOnSuccessListener { dataSnapshot ->
                    val points = dataSnapshot.getValue(Int::class.java) ?: 0
                    pointsTextView.text = "Points: $points"
                    statusTextView.text = getStatusBasedOnPoints(points)
                }
                .addOnFailureListener { e ->
                    Log.e("PointSystemFragment", "Failed to load points", e)
                    Toast.makeText(context, "Error loading points", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun getStatusBasedOnPoints(points: Int): String {
        return when {
            points >= 10 -> "Packrat"
            points >= 5 -> "Collector"
            points >= 1 -> "Starter"
            else -> "No Status Yet"
        }
    }
}
