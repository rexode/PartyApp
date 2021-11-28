package com.example.partyapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PartyViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PartyViewModel::class.java)) {
            return PartyViewModel() as T
        }
        throw IllegalArgumentException("UnknownViewModel")
    }

}