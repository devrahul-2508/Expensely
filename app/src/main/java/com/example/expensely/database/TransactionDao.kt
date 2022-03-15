package com.example.expensely.database

import androidx.room.*
import com.example.expensely.entities.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Insert
    suspend fun insert(transaction: Transaction)

    @Update
    suspend fun update(transaction: Transaction)

    @Delete
    suspend fun delete(transaction: Transaction)

    @Query("SELECT * FROM transaction_table ORDER BY ID DESC")
    fun getAllTransactions(): Flow<List<Transaction>>

    @Query("SELECT * FROM transaction_table WHERE transactionType= :item")
    fun getTransactionsByType(item:String):Flow<List<Transaction>>



}