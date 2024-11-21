package com.example.beehivecareappproject

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class GraphFragment : Fragment() {

    private lateinit var phaseProgressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_graph, container, false)
        phaseProgressBar = view.findViewById(R.id.phaseProgressBar)

        // Set up the bar graph phase dynamically
        setBarGraphPhase(30) // You can pass any value to represent the progress

        return view
    }

    // Function to update the phase of the bar graph
    private fun setBarGraphPhase(progressValue: Int) {
        // Log the progress value to make sure it's changing
        println("Setting progress to: $progressValue")

        // Set the progress on the ProgressBar
        phaseProgressBar.progress = progressValue

        // Determine the phase based on the progress value
        val phaseColor = when {
            progressValue < 30 -> ContextCompat.getColor(requireContext(), R.color.yellow) // Phase 1
            progressValue in 30..69 -> ContextCompat.getColor(requireContext(), R.color.orange) // Phase 2
            else -> ContextCompat.getColor(requireContext(), R.color.green) // Phase 3
        }

        // Log the color being set to check if it's correct
        println("Setting color to: $phaseColor")

        // Set the color of the progress bar based on phase
        phaseProgressBar.progressTintList = ColorStateList.valueOf(phaseColor)
    }
}

