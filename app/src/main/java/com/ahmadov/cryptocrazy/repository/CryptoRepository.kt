package com.ahmadov.cryptocrazy.repository

import com.ahmadov.cryptocrazy.model.Crypto
import com.ahmadov.cryptocrazy.model.CryptoList
import com.ahmadov.cryptocrazy.service.CryptoApi
import com.ahmadov.cryptocrazy.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class CryptoRepository  @Inject constructor(
    val api: CryptoApi
){
    suspend fun getCryptoListRp(): Resource<CryptoList>{
        val response=try {
            api.getCryptoList()
        }
        catch (e:Exception){
            return Resource.Error("Error")
        }
        return Resource.Success(response)
    }

    suspend fun getCrypto(id:String):Resource<Crypto>{
        val response=try{
            api.getCrypto()
        }
        catch (e:Exception){
            return Resource.Error("Error")
        }
        return  Resource.Success(response)
    }
}