package com.example.tiptime

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//Use a click listener so when the user taps the Calculate button, it calls the calculateTip function
        binding.calculateButton.setOnClickListener { calculateTip() }
        binding.costOfServiceEditText.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode)
        }
        binding.resetButton.setOnClickListener {
          binding.costOfServiceEditText.setText("")
            binding.tipResult.text = getString(R.string.tip_amount)
        }

    }

    private fun calculateTip() {
        /* Gets the text attribute of the Cost of Service and assigns it to a variable
        The text attribute is an Editable and must be converted into a String
         */
        val stringInTextField = binding.costOfServiceEditText.text.toString()
        //Converts the String to a decimal number and stores it in a variable
        val cost = stringInTextField.toDoubleOrNull()
        if (cost == null){
            binding.tipResult.text = ""
            return
        }
        // Find out which button was selected by the user and add logic to add math
        val tipPercentage = when (binding.tipOptions.checkedRadioButtonId) {
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }
        // Calculate the tip. Use var b/c user may select to round up the value
        var tip = tipPercentage * cost

        // Check to see if the isChecked attribute is "on". First assign to a variable to use in an if statement
        if (binding.roundUpSwitch.isChecked) {
            tip = kotlin.math.ceil(tip)
        }
       // Formats the tip according to the currency/country
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        // ???
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
    }
    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}


