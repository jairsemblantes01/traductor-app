package com.sjscvl.traductor.usercase

import android.util.Log
import com.sjscvl.traductor.model.endpoints.UserEndPoint
import com.sjscvl.traductor.model.entities.Users
import com.sjscvl.traductor.model.repositories.APIRes

class UsersUC {
  suspend fun getUser(user: String): Users? {
    Log.d("getUser entrada", user)
    var u: Users? = null
    try {
      val servicio = APIRes().buildUsersService(UserEndPoint::class.java)
      val respuesta = servicio.getUser(user)
      Log.d("getUser afirmativo", respuesta.body().toString())
      if (respuesta.isSuccessful) {
        u = respuesta.body()!!
      }
    } catch (e: Error) {
      Log.d("Error : ", e.message.toString())
    }
    return u
  }
}