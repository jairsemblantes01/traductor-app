package com.sjscvl.traductor.model.endpoints

import com.sjscvl.traductor.model.entities.Users
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserEndPoint {
  @GET("users/{user}")
  suspend fun getUser(@Path("user") user: String): Response<Users>
}