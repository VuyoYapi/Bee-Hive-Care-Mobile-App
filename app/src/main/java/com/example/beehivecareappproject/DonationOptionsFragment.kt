package com.example.beehivecareappproject

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DonationOptionsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DonationOptionsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_donation_options, container, false)

        val nameEdit = view.findViewById<EditText>(R.id.editTextName1)
        val surnameEdit = view.findViewById<EditText>(R.id.editTextSurname2)
        val emailEdit = view.findViewById<EditText>(R.id.editTextTextEmailAddress2)
        val phoneEdit = view.findViewById<EditText>(R.id.editTextPhone)
        val donationType = view.findViewById<EditText>(R.id.editTextTypeDonation)
        val numberDonation = view.findViewById<EditText>(R.id.editTextNumerDonation)
        val date = view.findViewById<EditText>(R.id.editTextDate)
        val message = view.findViewById<EditText>(R.id.editTextTextMultiLine2)
        val button = view.findViewById<Button>(R.id.submitDonationButton)

        val database = Firebase.database
        val myRef = database.getReference("Bee Hive Care Donation database new edition")

        button.setOnClickListener {
            Log.d("DonationFragment", "Submit button clicked")  // Debug click event
            // Generate new entry key for each donation
            val newDonationRef = myRef.push()

            val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val localDate = try {
                LocalDate.parse(date.text.toString(), dateFormatter)
            } catch (e: Exception) {
                LocalDate.now() // Default to current date if parsing fails
            }

            val updatedInformation = DonationClass(
                name = nameEdit.text.toString(),
                surname = surnameEdit.text.toString(),
                email = emailEdit.text.toString(),
                phone = phoneEdit.text.toString().toIntOrNull() ?: 0,
                typeOfDonate = donationType.text.toString(),
                numberOfDonate = numberDonation.text.toString().toIntOrNull() ?: 0,
                date = localDate,  // Keeping the parsed or current date
                message = message.text.toString()
            )

            newDonationRef.setValue(updatedInformation)
                .addOnSuccessListener {
                    Toast.makeText(context, "Donation successfully submitted", Toast.LENGTH_SHORT).show()

                    // Clear input fields
                    nameEdit.text.clear()
                    surnameEdit.text.clear()
                    emailEdit.text.clear()
                    phoneEdit.text.clear()
                    donationType.text.clear()
                    numberDonation.text.clear()
                    date.text.clear()
                    message.text.clear()
                }
                .addOnFailureListener { error ->
                    Log.e("FirebaseError", "Error writing data: ${error.message}")
                    Toast.makeText(context, "Failed to submit donation: ${error.message}", Toast.LENGTH_SHORT).show()
                }
        }

        return view
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DonationOptionsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DonationOptionsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}