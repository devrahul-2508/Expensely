package com.example.expensely.database

import androidx.annotation.WorkerThread
import com.example.expensely.entities.Transaction
import kotlinx.coroutines.flow.Flow

class TransactionRepository(private val transactionDao: TransactionDao) {


    val allTransactions:Flow<List<Transaction>> = transactionDao.getAllTransactions()

    @WorkerThread
    suspend fun insert(transaction: Transaction){
        transactionDao.insert(transaction)
    }
    @WorkerThread
    suspend fun update(transaction: Transaction){
        transactionDao.update(transaction)
    }
    @WorkerThread
    suspend fun delete(transaction: Transaction){
        transactionDao.delete(transaction)
    }

    fun getFilteredTransactions(item:String):Flow<List<Transaction>> =transactionDao.getTransactionsByType(item)


}