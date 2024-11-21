package com.example.beehivecareappproject

import android.os.Bundle
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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ContactFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ContactFragment : Fragment() {
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
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_contact, container, false)

        // Now you can find views using 'view'
        val nameEdit = view.findViewById<EditText>(R.id.editTextName)
        val emailEdit = view.findViewById<EditText>(R.id.editTextTextEmailAddress)
        val subjectEdit = view.findViewById<EditText>(R.id.editTextSubject)
        val messageEdit = view.findViewById<EditText>(R.id.editTextMessage)
        val button = view.findViewById<Button>(R.id.submitContactButton)

        val database = Firebase.database
        val myRef1 = database.getReference("Bee Connection test2").child("3")
        val myRef = database.getReference("Bee Hive Care Contact information new")

        // You can now use 'nameEditText' as needed, for example:
        // nameEditText.setText("Your Name")
        var contactinformation: Contactclass
        button.setOnClickListener {
            val contactInformation = Contactclass(
                nameEdit.text.toString(),
                emailEdit.text.toString(),
                subjectEdit.text.toString(),
                messageEdit.text.toString()
            )

            // Use push() to generate a unique ID for each new contact entry
            myRef.push().setValue(contactInformation)
                .addOnSuccessListener {
                    // Show a success message
                    Toast.makeText(
                        context,
                        "Contact information submitted successfully",
                        Toast.LENGTH_SHORT
                    ).show()

                    // Optionally clear the fields after submission
                    nameEdit.text.clear()
                    emailEdit.text.clear()
                    subjectEdit.text.clear()
                    messageEdit.text.clear()
                }
                .addOnFailureListener { error ->
                    // Log or display the error message
                    Toast.makeText(
                        context,
                        "Failed to submit contact information: ${error.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            myRef1.setValue("F")

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
         * @return A new instance of fragment ContactFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ContactFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}