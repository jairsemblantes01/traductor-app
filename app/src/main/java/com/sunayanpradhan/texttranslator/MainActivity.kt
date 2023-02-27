package com.sunayanpradhan.texttranslator

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import com.sunayanpradhan.texttranslator.databinding.ActivityMainBinding
import java.util.*


class MainActivity : AppCompatActivity() {
    private val RCODE = 28
    lateinit var binding: ActivityMainBinding
    private var items= arrayOf("English","Español", "Hindi","Bengali","Gujarati","Tamil","Telugu")
    private var conditions = DownloadConditions.Builder()
        .requireWifi()
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding= DataBindingUtil.setContentView(this,R.layout.activity_main)

        val itemsAdapter:ArrayAdapter<String> =ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line, items)

        binding.languageFrom.setAdapter(itemsAdapter)

        binding.languageTo.setAdapter(itemsAdapter)

        binding.translate.setOnClickListener {

            val options = TranslatorOptions.Builder()
                .setSourceLanguage(selectFrom())
                .setTargetLanguage(selectTo())
                .build()

            val englishGermanTranslator = Translation.getClient(options)

            englishGermanTranslator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener {

                    englishGermanTranslator.translate(binding.input.text.toString())
                        .addOnSuccessListener { translatedText ->

                            binding.output.text=translatedText

                        }
                        .addOnFailureListener { exception ->

                            Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()

                        }


                }
                .addOnFailureListener { exception ->

                    Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()

                }


        }

        //audio
        binding.btnRecord.setOnClickListener {
            capturarVoz()
        }




    }
    private fun capturarVoz() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE,
            Locale.getDefault()
        )
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, RCODE)
        } else {
            Log.e("ERROR", "Su dispositivo no admite entrada de voz")
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int, data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RCODE && resultCode == RESULT_OK && data != null) {
            val result = data.getStringArrayListExtra(
                RecognizerIntent.EXTRA_RESULTS
            )
            binding.input.setText(result!![0])
        }
    }

    private fun selectFrom(): String {

        return when(binding.languageFrom.text.toString()){

            ""-> TranslateLanguage.ENGLISH

            "English"->TranslateLanguage.ENGLISH

            "Español"->TranslateLanguage.SPANISH

            "Hindi"->TranslateLanguage.HINDI

            "Bengali"->TranslateLanguage.BENGALI

            "Gujarati"->TranslateLanguage.GUJARATI

            "Tamil"->TranslateLanguage.TAMIL

            "Telugu"->TranslateLanguage.TELUGU

            else->TranslateLanguage.ENGLISH

        }


    }

    private fun selectTo(): String {

        return when(binding.languageTo.text.toString()){

            ""-> TranslateLanguage.HINDI

            "English"->TranslateLanguage.ENGLISH

            "Español"->TranslateLanguage.SPANISH

            "Hindi"->TranslateLanguage.HINDI

            "Bengali"->TranslateLanguage.BENGALI

            "Gujarati"->TranslateLanguage.GUJARATI

            "Tamil"->TranslateLanguage.TAMIL

            "Telugu"->TranslateLanguage.TELUGU

            else->TranslateLanguage.HINDI

        }


    }


}

