package com.example.expensely.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "transaction_table")
data class Transaction(
    @ColumnInfo(name = "title")
    var title:String,
    @ColumnInfo(name="amount")
    var amount:Double,
    @ColumnInfo(name = "transactionType")
    var transactionType:String,
    @ColumnInfo(name = "tags")
    var tags:String,
    @ColumnInfo(name = "dateOfTransaction")
    var date: String,
    @ColumnInfo(name = "note")
    var note:String,
    @ColumnInfo(name="createdAt")
    var createdAt:String,
    @PrimaryKey(autoGenerate = true)
    var id:Int=0
):Parcelable