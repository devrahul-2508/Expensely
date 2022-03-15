package com.example.expensely.view.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expensely.R
import com.example.expensely.adapters.TransactionAdapter
import com.example.expensely.application.TransactionApplication
import com.example.expensely.databinding.FragmentDashBoardBinding
import com.example.expensely.entities.Transaction
import com.example.expensely.view.activities.AddUpdateTransactionActivity
import com.example.expensely.view.activities.MainActivity
import com.example.expensely.viewmodels.TransactionViewModel
import com.example.expensely.viewmodels.TransactionViewModelFactory


class DashBoardFragment : Fragment() {

    private lateinit var binding: FragmentDashBoardBinding

    private val transactionViewModel: TransactionViewModel by viewModels {
        TransactionViewModelFactory((requireActivity().application as TransactionApplication).repository)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDashBoardBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.btnAdd.setOnClickListener {
            val intent = Intent(requireActivity(), AddUpdateTransactionActivity::class.java)
            startActivity(intent)
        }
        observeTransaction()




        binding.idRVTransactionList.layoutManager = LinearLayoutManager(context)
        val transactionAdapter = TransactionAdapter(this)
        binding.idRVTransactionList.adapter = transactionAdapter


        transactionViewModel.allTransactions.observe(viewLifecycleOwner) { transactions ->
            transactions.let {
                if (it.isNotEmpty()) {
                    binding.idRVTransactionList.visibility = View.VISIBLE
                    binding.noTransactionsTV.visibility = View.GONE
                    transactionAdapter.transactionList(it)
                } else {
                    binding.idRVTransactionList.visibility = View.GONE
                    binding.noTransactionsTV.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun observeTransaction()=lifecycleScope.launchWhenStarted {



        transactionViewModel.allTransactions.observe(viewLifecycleOwner) { transactions ->
            displayBalance(transactions)

        }
    }

    @SuppressLint("SetTextI18n")
    private fun displayBalance(list: List<Transaction>){

        val transactionList: List<Transaction> = list
        var income = 0.0
        var expense = 0.0

        for (i in transactionList){
            if (i.transactionType == "Income") {
                income += i.amount
            } else {
                expense += i.amount
            }

        }
        binding.totalBalance.text = "$${income-expense}"
        binding.totalIncome.text = "$$income"
        binding.totalExpense.text = "$$expense"

    }



    override fun onResume() {
        super.onResume()
        if (requireActivity() is MainActivity) {
            (activity as MainActivity).showBottomNavigation()
        }
    }

    fun transactionDetails(transaction: Transaction) {
        findNavController().navigate(
            DashBoardFragmentDirections.actionDashboardToTransactionDetailsFragment2(
                transaction
            )
        )
        if (requireActivity() is MainActivity) {
            (activity as MainActivity).hideBottomNavigation()
        }
    }


}