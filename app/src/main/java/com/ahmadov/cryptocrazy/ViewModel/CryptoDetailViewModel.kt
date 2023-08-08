package com.ahmadov.cryptocrazy.ViewModel

import androidx.lifecycle.ViewModel
import com.ahmadov.cryptocrazy.model.Crypto
import com.ahmadov.cryptocrazy.model.CryptoItem
import com.ahmadov.cryptocrazy.repository.CryptoRepository
import com.ahmadov.cryptocrazy.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CryptoDetailViewModel @Inject constructor(
    private val repository: CryptoRepository
) :ViewModel() {

    suspend fun getCrypto(id:String):Resource<Crypto>{
        return  repository.getCrypto(id)

    }
}