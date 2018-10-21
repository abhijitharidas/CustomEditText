package com.codetantri.customedittext

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.Selection
import android.text.TextWatcher
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_entertext_layout.view.*

class MainActivity : AppCompatActivity() {

    var phoneNumberWatcher: PhoneNumberWatcher? = null

    var fullName: String = ""
    var phoneNo: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpFormatter()


        verifyButton.setOnClickListener { _ ->
            verifyData()
        }
    }

    private fun setUpFormatter() {
        phoneNumberWatcher = PhoneNumberWatcher(this, "IN", 91)
        tvPhone.hintText.addTextChangedListener(phoneNumberWatcher)
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

    /**
     * This updates country dynamically as user types in area code
     *
     * @return
     */
    private fun getCountryDetectorTextWatcher(): TextWatcher {

        if (editText_registeredCarrierNumber != null) {
            if (areaCodeCountryDetectorTextWatcher == null) {
                areaCodeCountryDetectorTextWatcher = object : TextWatcher {
                    internal var lastCheckedNumber: String? = null


                    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

                    }

                    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                        val selectedCountry = getSelectedCountry()
                        if (selectedCountry != null && (lastCheckedNumber == null || lastCheckedNumber != s.toString()) && countryDetectionBasedOnAreaAllowed) {
                            //possible countries
                            if (currentCountryGroup != null) {
                                val enteredValue = getEditText_registeredCarrierNumber().getText().toString()
                                if (enteredValue.length >= currentCountryGroup.areaCodeLength) {
                                    val digitsValue = PhoneNumberUtil.normalizeDigitsOnly(enteredValue)
                                    if (digitsValue.length >= currentCountryGroup.areaCodeLength) {
                                        val currentAreaCode = digitsValue.substring(0, currentCountryGroup.areaCodeLength)
                                        if (currentAreaCode != lastCheckedAreaCode) {
                                            val detectedCountry = currentCountryGroup.getCountryForAreaCode(context, getLanguageToApply(), currentAreaCode)
                                            if (!detectedCountry.equals(selectedCountry)) {
                                                countryChangedDueToAreaCode = true
                                                lastCursorPosition = Selection.getSelectionEnd(s)
                                                setSelectedCountry(detectedCountry)
                                            }
                                            lastCheckedAreaCode = currentAreaCode
                                        }
                                    }
                                }
                            }
                            lastCheckedNumber = s.toString()
                        }
                    }

                    override fun afterTextChanged(s: Editable) {


                    }
                }
            }
        }
        return areaCodeCountryDetectorTextWatcher
    }
}
