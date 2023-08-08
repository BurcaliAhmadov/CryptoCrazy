package com.ahmadov.cryptocrazy.service

import com.ahmadov.cryptocrazy.model.Crypto
import com.ahmadov.cryptocrazy.model.CryptoList
import com.ahmadov.cryptocrazy.model.CryptoListItem
import retrofit2.Response
import retrofit2.http.GET

interface CryptoApi {

    @GET("cryptolist.json")
    suspend fun getCryptoList():CryptoList

    @GET("crypto.json")
    suspend fun getCrypto(): Crypto
}