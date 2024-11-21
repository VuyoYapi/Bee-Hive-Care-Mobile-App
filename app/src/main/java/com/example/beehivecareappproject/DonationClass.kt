package com.example.beehivecareappproject

import java.time.LocalDate

data class DonationClass( val name: String,
                          val surname: String,  // Check this parameter name
                          val email: String,    // Check this parameter name
                          val phone: Int,
                          val typeOfDonate: String,  // Check this parameter name
                          val numberOfDonate: Int,    // Check this parameter name
                          val date: LocalDate,
                          val message: String)