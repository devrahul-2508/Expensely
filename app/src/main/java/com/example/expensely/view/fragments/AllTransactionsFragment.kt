package com.example.expensely.view.fragments

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expensely.R
import com.example.expensely.adapters.CustomListAdapter
import com.example.expensely.adapters.TransactionAdapter
import com.example.expensely.application.TransactionApplication
import com.example.expensely.databinding.CustomDialogBinding
import com.example.expensely.databinding.FragmentAllTransactionsBinding
import com.example.expensely.entities.Transaction
import com.example.expensely.utils.Constants
import com.example.expensely.view.activities.MainActivity
import com.example.expensely.viewmodels.TransactionViewModel
import com.example.expensely.viewmodels.TransactionViewModelFactory

class AllTransactionsFragment : Fragment() {

    lateinit var binding:FragmentAllTransactionsBinding

    lateinit var customListDialog:Dialog
    lateinit var transactionAdapter:TransactionAdapter

    private val transactionViewModel : TransactionViewModel by viewModels {
        TransactionViewModelFactory((requireActivity().application as TransactionApplication).repository)
    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentAllTransactionsBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnFilter.setOnClickListener {
            filterTransactionsDialog()
        }
        binding.idRVTransactionList.layoutManager=LinearLayoutManager(requireContext())
        transactionAdapter=TransactionAdapter(this)
        binding.idRVTransactionList.adapter=transactionAdapter

        observeAllTransactions()
    }

    private fun observeAllTransactions() {

        transactionViewModel.allTransactions.observe(viewLifecycleOwner){
                transactions->
            run {
                transactions.let {
                    if (it.isNotEmpty()) {
                        binding.idRVTransactionList.visibility = View.VISIBLE
                        binding.noTransactionsTV.visibility=View.GONE
                        transactionAdapter.transactionList(it)

                    }
                    else{
                        binding.idRVTransactionList.visibility = View.GONE
                        binding.noTransactionsTV.visibility=View.VISIBLE
                    }
                }
            }
        }

    }

    private fun filterTransactionsDialog() {
        customListDialog= Dialog(requireActivity())
        val mBinding=CustomDialogBinding.inflate(layoutInflater)
        customListDialog.setContentView(mBinding.root)
        val transactionTypes=Constants.transactionType()
        transactionTypes.add(0,"All")
        mBinding.rvList.layoutManager=LinearLayoutManager(requireContext())
        val customListAdapter=CustomListAdapter(this,transactionTypes)
        mBinding.rvList.adapter=customListAdapter
        customListDialog.show()

    }
    fun filterTransactions(item:String){
        customListDialog.dismiss()
        if (item=="All"){
            binding.toolTV.text=item
            observeAllTransactions()
        }
        else{
            Log.i("Filter clicked", item)
            binding.toolTV.text=item
            transactionViewModel.getFilteredTransactions(item).observe(viewLifecycleOwner){
                transactions->
                run {
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
        }
    }


    override fun onResume() {
        super.onResume()
        if(requireActivity() is MainActivity){
            (activity as MainActivity).showBottomNavigation()
        }
    }
    fun transactionDetails(transaction:Transaction){
            findNavController().navigate(AllTransactionsFragmentDirections.actionAllTransactionsToTransactionDetailsFragment(transaction))
            if(requireActivity() is MainActivity){
                (activity as MainActivity).hideBottomNavigation()
            }
    }






}