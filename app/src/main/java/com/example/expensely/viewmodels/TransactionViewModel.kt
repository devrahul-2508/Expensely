package com.example.expensely.viewmodels

import androidx.lifecycle.*
import com.example.expensely.database.TransactionRepository
import com.example.expensely.entities.Transaction
import kotlinx.coroutines.launch
import java.util.concurrent.Flow

class TransactionViewModel(private val transactionRepository: TransactionRepository):ViewModel() {

    val allTransactions:LiveData<List<Transaction>> =transactionRepository.allTransactions.asLiveData()

    fun insert(transaction: Transaction)=viewModelScope.launch {
        transactionRepository.insert(transaction)
    }
    fun update(transaction: Transaction)=viewModelScope.launch {
        transactionRepository.update(transaction)
    }
    fun delete(transaction: Transaction)=viewModelScope.launch {
        transactionRepository.delete(transaction)
    }
    fun getFilteredTransactions(item:String):LiveData<List<Transaction>> =transactionRepository.getFilteredTransactions(item).asLiveData()



}
class TransactionViewModelFactory(private val transactionRepository: TransactionRepository):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransactionViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return TransactionViewModel(transactionRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}