package org.hyperskill.calculator.tip

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.math.BigDecimal
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private var text: BigDecimal? = null
    private var sliderValue = 0f

    private val watcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if (p0.isNullOrBlank())
                text_view.text = ""
            else
                initTextInTextView(p0)
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if (p0.isNullOrBlank())
                text_view.text = ""
            else
                initTextInTextView(p0)
        }

        override fun afterTextChanged(p0: Editable?) {
            if (p0.isNullOrBlank())
                text_view.text = ""
            else
                initTextInTextView(p0)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initListeners()
    }

    private fun initListeners() {
        edit_text.addTextChangedListener(watcher)
        slider.addOnChangeListener { slider, value, fromUser ->
            sliderValue = value
            if (text_view.text.toString().isNotBlank())
                text_view.text = text?.let { calculateTip(it, sliderValue) }
            else
                text_view.text = ""
        }
    }

    private fun calculateTip(text: BigDecimal, sliderValue: Float): String {
        val tip = (text.times(sliderValue.toBigDecimal())) / BigDecimal.valueOf(100.0)
        val dec = DecimalFormat("0.00")
        return "Tip amount: ${dec.format(tip)}"
    }

    private fun initTextInTextView(p0: CharSequence) {
        text = when {
            p0.contains('.') -> {
                p0.toString().toBigDecimal()
            }
            else -> {
                p0.toString().toLong().toBigDecimal()
            }
        }
        text_view.text = calculateTip(text!!, sliderValue)
    }
}