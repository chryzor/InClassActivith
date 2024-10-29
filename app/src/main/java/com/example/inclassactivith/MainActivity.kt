package com.example.inclassactivith

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.NestedScrollingParent
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.DialogFragment
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private var saucePrice = 0
    private var toppingsPrice = 0
    private lateinit var totalCostTextView: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize total cost text view
        totalCostTextView = findViewById(R.id.total_cost_textview)

        // Spinner setup
        val pizzaSpinner = findViewById<Spinner>(R.id.Spinner)
        val sauce = arrayOf("Select a sauce","Barbecue Sauce - $15", "Tomato Basil Sauce - $12", "Marinara Sauce - $10")
        val prices = arrayOf(0, 15, 12, 10)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item,sauce)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        pizzaSpinner.adapter = adapter

        pizzaSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                saucePrice = prices[position]
                Toast.makeText(this@MainActivity, sauce[position], Toast.LENGTH_SHORT).show()
                updateTotalCost()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                // This is for when nothing is clicked this what  android studio gave me to help fix the code
            }
        }



        // Toppings CheckBoxes
        val toppings = listOf(
            findViewById<CheckBox>(R.id.checkbox_Chicken),
            findViewById<CheckBox>(R.id.Pepperoni),
            findViewById<CheckBox>(R.id.Olives),
            findViewById<CheckBox>(R.id.Onions),
            findViewById<CheckBox>(R.id.checkbox_cheddear)
        )

        // Handle topping selections
        toppings.forEach { topping ->
            topping.setOnCheckedChangeListener { buttonView, isChecked ->
                val toppingName = buttonView.text.toString()
                if (isChecked) {
                    toppingsPrice += 1
                    Toast.makeText(this, "$toppingName added", Toast.LENGTH_SHORT).show()
                } else {
                    toppingsPrice -= 1
                    Toast.makeText(this, "$toppingName removed", Toast.LENGTH_SHORT).show()
                }
                updateTotalCost()
            }
        }
    }

    // Function to handle sauce selection and display toast
    private fun updateSauceSelection(price: Int, sauceName: String) {
        saucePrice = price
        Toast.makeText(this, "$sauceName selected", Toast.LENGTH_SHORT).show()
        updateTotalCost()
    }

    // Function to update the total cost
    private fun updateTotalCost() {
        val totalCost = saucePrice + toppingsPrice
        totalCostTextView.text = "Total Cost: $$totalCost"
    }

    // TimePickerFragment for any future use
    class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)
            return TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity))
        }

        override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
            Log.i("MYTAG", "Hour is $hourOfDay , minute is $minute")
        }
    }
}