package com.example.expensely.application

import android.app.Application
import com.example.expensely.database.TransactionDatabase
import com.example.expensely.database.TransactionRepository

class TransactionApplication : Application(){
    private val database by lazy { TransactionDatabase.getDatabase(this) }
    val repository by lazy { TransactionRepository(database.transactionDao()) }
}