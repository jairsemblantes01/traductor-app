package com.sjscvl.traductor.model.repositories

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIRes {
  private fun getRetrofitBuilder(base: String): Retrofit {
    return Retrofit.Builder()
      .baseUrl(base)
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }

  fun <T> buildUsersService(service: Class<T>): T {
    val builder = getRetrofitBuilder("https://gorest.co.in/public/v2/")
    return builder.create(service)
  }
}