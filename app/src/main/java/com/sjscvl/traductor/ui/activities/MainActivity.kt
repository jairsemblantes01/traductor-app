package com.sjscvl.traductor.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.sjscvl.traductor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    initClass()
  }
  private fun initClass () {
    val extras = intent.extras
    val nombre = extras?.getString("nombre", "Sin nombre")
    val pantalla = binding.saludo
    pantalla.text = "Bienvenido ${nombre}"
    binding.traduceTexto.setOnClickListener {
      try {
        var intent = Intent(this, TextoActivity::class.java)
        intent.putExtra("nombre", nombre)
        startActivity(intent)
      } catch(e: Exception) {
        Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
      }
    }
  }
}