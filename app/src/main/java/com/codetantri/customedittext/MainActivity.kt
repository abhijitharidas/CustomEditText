package com.codetantri.customedittext

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var fullName: String = ""
    var phoneNo: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        verifyButton.setOnClickListener { _ ->
            verifyData()
        }
    }

    private fun verifyData() {
        fullName = tvFullName.getText()
        phoneNo = tvPhone.getText()

        if (fullName.length <= 6) {
            tvFullName.setError("Please enter a correct name")
        } else {
            tvFullName.clearError()
        }

        if (phoneNo.length <= 6) {
            tvPhone.setError("Please enter a valid phone number")
        } else {
            tvPhone.clearError()
        }
    }
}
