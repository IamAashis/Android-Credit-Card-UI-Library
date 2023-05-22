package com.androidLibrary.androidcreditcarduilibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.androidLibrary.androidcreditcarduilibrary.databinding.ActivityMainBinding
import com.androidlibrary.creditcardlibrary.feature.ValidateCard

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setup()
        setContentView(binding.root)
    }

    private fun setup() {
        addValidation()
    }

    private fun addValidation() {
        ValidateCard().setValidateCardNumber(binding.etCardNumber, this)
        ValidateCard().setValidateExpDate(binding.edtExpDate)
    }
}