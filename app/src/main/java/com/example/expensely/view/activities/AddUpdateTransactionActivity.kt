package com.example.expensely.view.activities

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import com.example.expensely.R
import com.example.expensely.application.TransactionApplication
import com.example.expensely.databinding.ActivityAddUpdateTransactionBinding
import com.example.expensely.entities.Transaction
import com.example.expensely.utils.Constants
import com.example.expensely.view.fragments.DashBoardFragment
import com.example.expensely.viewmodels.TransactionViewModel
import com.example.expensely.viewmodels.TransactionViewModelFactory
import java.lang.Double.parseDouble
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class AddUpdateTransactionActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityAddUpdateTransactionBinding
    private val transactionViewModel: TransactionViewModel by viewModels {
        TransactionViewModelFactory((application as TransactionApplication).repository)
    }
    private var transactionDetails:Transaction?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUpdateTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setAdapters()
        binding.btnInsert.setOnClickListener(this)
        binding.etDateOfTransaction.setOnClickListener(this)

       Log.e("AddUpdateActivity","running,,,")

      if (intent.hasExtra(Constants.EXTRA_TRANSACTION_DETAILS)){
          transactionDetails=intent.getParcelableExtra(Constants.EXTRA_TRANSACTION_DETAILS)
      }

        transactionDetails?.let {
            binding.etTitle.setText(it.title)
            binding.etAmount.setText(it.amount.toString())
            binding.etDateOfTransaction.setText(it.date)
            binding.etNote.setText(it.note)
            binding.btnInsert.text = "Update Transaction"
            binding.label.text="Update Transaction"
        }


    }

    private fun setAdapters() {
        val transactionTypeAdapter =
            ArrayAdapter(this, R.layout.dropdown_item, Constants.transactionType())
        binding.edtTransactionType.setAdapter(transactionTypeAdapter)

        val transactionTagAdapter =
            ArrayAdapter(this, R.layout.dropdown_item, Constants.transactionTags)
        binding.edtTags.setAdapter(transactionTagAdapter)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnInsert -> {
                insertTransaction()
                return
            }
            R.id.et_dateOfTransaction -> {
                showDatePickerDialog()
                return
            }


        }

    }

    private fun insertTransaction() {
        val title = binding.etTitle.text.toString()
        val amount = binding.etAmount.text.toString()
        val transactionType = binding.edtTransactionType.text.toString()
        val tags = binding.edtTags.text.toString()
        val transactionDate = binding.etDateOfTransaction.text.toString()
        val note = binding.etNote.text.toString()

        when {
            TextUtils.isEmpty(title) -> {
                Toast.makeText(this, "Pls Enter Title", Toast.LENGTH_SHORT).show()
            }

            TextUtils.isEmpty(amount)  -> {
                Toast.makeText(this, "Pls Enter Amount", Toast.LENGTH_SHORT).show()
        }
            TextUtils.isEmpty(transactionType) -> {
                Toast.makeText(this, "Pls Enter TransactionType", Toast.LENGTH_SHORT).show()
            }
            TextUtils.isEmpty(tags) -> {
                Toast.makeText(this, "Pls Enter Tags", Toast.LENGTH_SHORT).show()
            }
            TextUtils.isEmpty(transactionDate) -> {
                Toast.makeText(this, "Pls Enter Transaction Date", Toast.LENGTH_SHORT).show()

            }
            TextUtils.isEmpty(note) -> {
                Toast.makeText(this, "Pls Enter Note", Toast.LENGTH_SHORT).show()

            }
            else -> {

                    var transactionId=0
                    transactionDetails?.let {
                        if (it.id!=0){
                            transactionId=it.id
                        }
                    }


                    val transactionDetails=Transaction(
                     title,
                     parseDouble(amount),
                     transactionType,
                     tags,
                     transactionDate,
                     note,
                     createdAt(),
                     transactionId
                 )

                if (transactionId==0){
                    transactionViewModel.insert(transactionDetails)
                    Toast.makeText(this, "Transaction Added", Toast.LENGTH_SHORT).show()
                }
                else{
                    transactionViewModel.update(transactionDetails)
                    Toast.makeText(this, "Transaction Updated", Toast.LENGTH_SHORT).show()

                }
                finish()

            }
        }





    }

    @SuppressLint("SimpleDateFormat")
    private fun showDatePickerDialog() {


        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in TextView
                val dateString: String = "$dayOfMonth/${monthOfYear + 1}/$year"
                //Formatting selected date like Thu,13-Apr-2015

                val date1 = SimpleDateFormat("dd/MM/yyyy").parse(dateString)
                val dateFormat: DateFormat = SimpleDateFormat("MMM dd,yyyy")
                val dayOfWeek = dateFormat.format(date1)
                binding.etDateOfTransaction.setText(dayOfWeek)

            },
            year,
            month,
            day
        )
        dpd.show()
    }

    @SuppressLint("SimpleDateFormat")
    private fun createdAt(): String {
        val formatter = SimpleDateFormat("E, dd-MM-yyyy HH:mm:ss")
        val date = Date()
        return formatter.format(date)
    }

}