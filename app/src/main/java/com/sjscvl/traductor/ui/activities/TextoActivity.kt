package com.sjscvl.traductor.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import com.sjscvl.traductor.databinding.ActivityTextoBinding

class TextoActivity : AppCompatActivity() {
  lateinit var binding: ActivityTextoBinding
  private var items= arrayOf("Inglés","Español","Francés","Italiano","Hindi","Bengali","Gujarati","Tamil","Telugu")
  private var conditions = DownloadConditions.Builder()
    .requireWifi()
    .build()
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityTextoBinding.inflate(layoutInflater)
    setContentView(binding.root)
    val itemsAdapter:ArrayAdapter<String> = ArrayAdapter(
      this,
      android.R.layout.simple_dropdown_item_1line, items)
    binding.languageFrom.setAdapter(itemsAdapter)
    binding.languageTo.setAdapter(itemsAdapter)

    binding.translate.setOnClickListener {
      val options = TranslatorOptions.Builder()
        .setSourceLanguage(selectFrom())
        .setTargetLanguage(selectTo())
        .build()

      val universalTranslator = Translation.getClient(options)
      universalTranslator.downloadModelIfNeeded(conditions)
        .addOnSuccessListener {
          universalTranslator.translate(binding.input.text.toString())
            .addOnSuccessListener { translatedText: String ->
              binding.output.text=translatedText
            }
            .addOnFailureListener { e: Exception ->
              Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
        }
        .addOnFailureListener { e: Exception ->
          Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }
    // binding= DataBindingUtil.setContentView(this, R.layout.activity_texto as ActivityTextoBinding) as ActivityTextoBinding
  }
  private fun selectFrom(): String {
    return when(binding.languageFrom.text.toString()){
      ""-> TranslateLanguage.ENGLISH
      "Inglés"->TranslateLanguage.ENGLISH
      "Español"->TranslateLanguage.SPANISH
      "Francés"->TranslateLanguage.FRENCH
      "Italiano"->TranslateLanguage.ITALIAN
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
      ""-> TranslateLanguage.SPANISH
      "Inglés"->TranslateLanguage.ENGLISH
      "Español"->TranslateLanguage.SPANISH
      "Francés"->TranslateLanguage.FRENCH
      "Italiano"->TranslateLanguage.ITALIAN
      "Hindi"->TranslateLanguage.HINDI
      "Bengali"->TranslateLanguage.BENGALI
      "Gujarati"->TranslateLanguage.GUJARATI
      "Tamil"->TranslateLanguage.TAMIL
      "Telugu"->TranslateLanguage.TELUGU
      else->TranslateLanguage.SPANISH
    }
  }
}