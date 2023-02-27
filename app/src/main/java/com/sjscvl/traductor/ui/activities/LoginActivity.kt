package com.sjscvl.traductor.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.sjscvl.traductor.databinding.ActivityLoginBinding
import com.sjscvl.traductor.model.entities.Users
import com.sjscvl.traductor.usercase.UsersUC
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
  private lateinit var binding: ActivityLoginBinding
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityLoginBinding.inflate(layoutInflater)
    setContentView(binding.root)
    initClass()
  }

  private fun initClass() {
    binding.btnLogin.setOnClickListener{
      val txtUser = binding.etUser.text.toString()
      val txtMail = binding.etMail.text.toString()
      lateinit var toast: Toast
      var progress = binding.loginProgess
      if (!TextUtils.isEmpty(txtUser) && !TextUtils.isEmpty(txtMail)) {
        lifecycleScope.launch {
          try {
            progress.visibility = View.VISIBLE
            val usuario: Users? = UsersUC().getUser(txtUser)
            if (usuario != null) {
              Log.d("key", usuario.status)
              if (usuario.status == "active") {
                if (usuario.email == txtMail) {
                  var intent = Intent(this@LoginActivity, MainActivity::class.java)
                  intent.putExtra("nombre", usuario.name)
                  startActivity(intent)
                } else {
                  Log.d("key", "el mail no es el correcto")
                  toast =
                    Toast.makeText(this@LoginActivity, "El mail es incorrecto", Toast.LENGTH_LONG)
                  toast.show()
                }
              } else {
                toast =
                  Toast.makeText(this@LoginActivity, "El usuario no esta activo", Toast.LENGTH_LONG)
                toast.show()
              }
            } else {
              toast = Toast.makeText(this@LoginActivity, "No existe el usuario", Toast.LENGTH_LONG)
              toast.show()
            }
          } catch (e: Exception) {
            toast = Toast.makeText(this@LoginActivity, e.message.toString(), Toast.LENGTH_LONG)
            toast.show()
          } finally {
            progress.visibility = View.GONE
          }
        }
      } else {
        toast = Toast.makeText(this, "No puede dejar vacios los campos", Toast.LENGTH_LONG)
        toast.show()
      }
    }
  }
}