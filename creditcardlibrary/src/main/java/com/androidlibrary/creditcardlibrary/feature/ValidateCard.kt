package com.androidlibrary.creditcardlibrary.feature

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.widget.EditText
import androidx.core.content.ContextCompat
import com.androidlibrary.creditcardlibrary.R
import com.androidlibrary.creditcardlibrary.utils.CardsConstants
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ValidateCardActivity {

    private var isFormatting: Boolean = false
    private var cardType = ""
    private var cardNumber = ""
    private var isFormattingExp: Boolean = false

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_validate_card)
//    }

     fun validateCardNumber(edtCardNumber: EditText, context: Context) {
        edtCardNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                drawableIcon(s, edtCardNumber, context)
                maskedEditText(s, edtCardNumber)
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

    }

    fun validateExpDate(edtExpDate: EditText) {
        edtExpDate.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                appendExpDate(s, edtExpDate)
                backgroundResources(s, edtExpDate)
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    fun validateCvv(edtCvv: EditText) {

        edtCvv.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                activeDeactivateButton()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    fun drawableIcon(s: CharSequence?, edtCardNumber: EditText, context: Context) {
        var drawableResourceId: Int? = null

        if (!s.toString().isNullOrEmpty()) {
            if (validateCardNumber(s.toString().first().toString()) == null) {
                edtCardNumber.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    null, null, null, null
                )
                edtCardNumber.setBackgroundResource(R.drawable.rdt_background_null)
            } else {
                drawableResourceId = validateCardNumber(s.toString())?.first

                if ((cardType == CardsConstants.americanExpress && cardNumber.length != 15) || (cardType != CardsConstants.americanExpress && cardNumber.length != 16)) {
                    edtCardNumber.setBackgroundResource(R.drawable.rdt_background_null)
                } else {
                    edtCardNumber.setBackgroundResource(R.drawable.edt_background)
                }

                if (s.toString().isNotEmpty()) {
                    val myDrawable: Drawable? = drawableResourceId?.let {
                        ContextCompat.getDrawable(
                            context, it
                        )
                    }
                    edtCardNumber.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        myDrawable, null, null, null
                    )
                } else {
                    edtCardNumber.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        null, null, null, null
                    )
                }
            }
        } else {
            edtCardNumber.setCompoundDrawablesRelativeWithIntrinsicBounds(
                null, null, null, null
            )
        }
    }

    fun maskedEditText(s: CharSequence?, edtCardNumber: EditText) {
        if (!isFormatting) {
            isFormatting = true
            if (validateCardNumber(
                    s.toString()
                )?.second == CardsConstants.americanExpress
            ) {
                cardType = CardsConstants.americanExpress
                val cleanText = s.toString().replace("\\s".toRegex(), "")
                val formattedText = StringBuilder()

                for (i in cleanText.indices) {
                    formattedText.append(cleanText[i])

                    if (i > 3) {
                        if ((i + 1) % 10 == 0 && i != cleanText.length - 1) {
                            formattedText.append(" ")
                        }
                    } else {
                        if ((i + 1) % 4 == 0 && i != cleanText.length - 1) {
                            formattedText.append(" ")
                        }
                    }
                }
                setMaximumLength(17, edtCardNumber)
                edtCardNumber.setText(formattedText)
                edtCardNumber.setSelection(formattedText.length)
            } else {
                cardType = ""
                val cleanText = s.toString().replace("\\s".toRegex(), "")
                val formattedText = StringBuilder()

                for (i in cleanText.indices) {
                    formattedText.append(cleanText[i])
                    if ((i + 1) % 4 == 0 && i != cleanText.length - 1) {
                        formattedText.append(" ")
                    }
                }

                edtCardNumber.setText(formattedText)
                edtCardNumber.setSelection(formattedText.length)
                setMaximumLength(19, edtCardNumber)
            }
            isFormatting = false
        }
    }

    private fun setMaximumLength(length: Int, edtCardNumber: EditText) {
        val filterArray = arrayOf<InputFilter>(InputFilter.LengthFilter(length))
        edtCardNumber.filters = filterArray
    }

    fun validateCardNumber(cardNumber: String): Pair<Int, String>? {

        if (cardNumber.length >= 1) {
            return when (cardNumber.firstOrNull()) {
                '4' -> {
                    Pair(R.drawable.ic_visa, CardsConstants.visa)
                }
                '5' -> {
                    Pair(R.drawable.ic_master, CardsConstants.masterCard)
                }
                '3' -> {
                    Pair(R.drawable.ic_american_express, CardsConstants.americanExpress)
                }
                '6' -> {
                    Pair(R.drawable.ic_discover, CardsConstants.discover)
                }
                else -> null
            }
        } else if (cardNumber.length >= 2) {
            when (cardNumber.substring(0, 2).toInt()) {
                in 40..49 -> {
                    return Pair(R.drawable.ic_visa, CardsConstants.visa)
                }
                in 51..55 -> {
                    return Pair(R.drawable.ic_master, CardsConstants.masterCard)
                }
                34, 37 -> {
                    return Pair(R.drawable.ic_american_express, CardsConstants.americanExpress)
                }
                60, 62, 64, 65 -> {
                    return Pair(R.drawable.ic_discover, CardsConstants.discover)
                }
                else -> {
                    return null
                }
            }
        } else {
            validateCreditCardNumber(cardNumber)
        }

        return null
    }

    private fun validateCreditCardNumber(cardNumber: String): Pair<Int, String>? {
        val visaPattern = "^4[0-9]{12}(?:[0-9]{3})?$".toRegex()
        val mastercardPattern = "^5[1-5][0-9]{14}$".toRegex()
        val americanExpressPattern = "^3[47][0-9]{13}$".toRegex()
        val discoverPattern = "^6(?:011|5[0-9]{2})[0-9]{12}$".toRegex()

        val cleanedCardNumber = cardNumber.replace("\\s".toRegex(), "")

        return when {
            visaPattern.matches(cleanedCardNumber) -> Pair(R.drawable.ic_visa, CardsConstants.visa)
            mastercardPattern.matches(cleanedCardNumber) -> Pair(
                R.drawable.ic_master, CardsConstants.masterCard
            )
            americanExpressPattern.matches(cleanedCardNumber) -> Pair(
                R.drawable.ic_american_express, CardsConstants.americanExpress
            )
            discoverPattern.matches(cleanedCardNumber) -> Pair(
                R.drawable.ic_discover, CardsConstants.discover
            )
            else -> null
        }
    }

    fun appendExpDate(s: CharSequence?, edtExpDate: EditText) {
        if (!isFormattingExp) {
            isFormattingExp = true
            val cleanText = s.toString().replace("\\D+".toRegex(), "")
            val formattedText = StringBuilder()

            for (i in cleanText.indices) {
                formattedText.append(cleanText[i])
                if ((i + 1) % 2 == 0 && i != cleanText.length - 1) {
                    formattedText.append("/")
                }
            }

            edtExpDate.setText(formattedText)
            edtExpDate.setSelection(formattedText.length)
            isFormattingExp = false
        }
    }

    fun backgroundResources(s: CharSequence?, edtExpDate: EditText) {
        if (s.toString().length >= 4) {
            if (validateVisaExpDate(s.toString())) {
                edtExpDate.setBackgroundResource(R.drawable.edt_background)
            } else {
                edtExpDate.setBackgroundResource(R.drawable.rdt_background_null)
            }
        } else {
            edtExpDate.setBackgroundResource(R.drawable.rdt_background_null)
        }
    }

    private fun validateVisaExpDate(expDate: String): Boolean {
        val dateFormat = SimpleDateFormat("MM/yy")
        dateFormat.isLenient = false

        return try {
            val parsedDate = dateFormat.parse(expDate)
            val currentDate = Calendar.getInstance().apply { time = Date() }
            val expDate = Calendar.getInstance().apply { time = parsedDate }

            currentDate.before(expDate) || currentDate == expDate
        } catch (e: ParseException) {
            false
        }
    }
}