package com.sjscvl.traductor.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import com.sjscvl.traductor.databinding.ActivityTextoBinding
import java.util.*

class TextoActivity : AppCompatActivity() {
  private val RCODE = 28
  lateinit var binding: ActivityTextoBinding
  private var items= arrayOf("Inglés","Español","Francés","Italiano","Aleman","Portugues","Gujarati","Tamil","Telugu")
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
      var progress = binding.traductorProgess
      val options = TranslatorOptions.Builder()
        .setSourceLanguage(selectFrom())
        .setTargetLanguage(selectTo())
        .build()
      progress.visibility = View.VISIBLE
      val universalTranslator = Translation.getClient(options)
      universalTranslator.downloadModelIfNeeded(conditions)
        .addOnSuccessListener {
          universalTranslator.translate(binding.input.text.toString())
            .addOnSuccessListener { translatedText: String ->
              binding.output.text=translatedText
              progress.visibility = View.GONE
            }
            .addOnFailureListener { e: Exception ->
              Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
              progress.visibility = View.GONE
            }
        }
        .addOnFailureListener { e: Exception ->
          Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
          progress.visibility = View.GONE
        }
    }
    //audiotexto
    binding.btnRecord.setOnClickListener {
      capturarVoz()
    }
    // binding= DataBindingUtil.setContentView(this, R.layout.activity_texto as ActivityTextoBinding) as ActivityTextoBinding
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
      "Inglés"->TranslateLanguage.ENGLISH
      "Español"->TranslateLanguage.SPANISH
      "Francés"->TranslateLanguage.FRENCH
      "Italiano"->TranslateLanguage.ITALIAN
      "Aleman"->TranslateLanguage.GERMAN
      "Portugues"->TranslateLanguage.PORTUGUESE
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
      "Aleman"->TranslateLanguage.GERMAN
      "Portugues"->TranslateLanguage.PORTUGUESE
      "Gujarati"->TranslateLanguage.GUJARATI
      "Tamil"->TranslateLanguage.TAMIL
      "Telugu"->TranslateLanguage.TELUGU
      else->TranslateLanguage.SPANISH
    }
  }
}