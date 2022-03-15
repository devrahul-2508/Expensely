package com.example.expensely.utils

object Constants {

   const val EXTRA_TRANSACTION_DETAILS="TransactionDetails"


    fun transactionType():ArrayList<String>{
        val list= ArrayList<String>()
        list.add("Income")
        list.add("Expense")
        return list
    }

    val transactionTags = listOf(
        "Housing",
        "Transportation",
        "Food",
        "Utilities",
        "Insurance",
        "Healthcare",
        "Saving & Debts",
        "Personal Spending",
        "Entertainment",
        "Miscellaneous"
    )
}