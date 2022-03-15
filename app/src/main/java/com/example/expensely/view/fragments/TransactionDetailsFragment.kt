package com.example.expensely.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.expensely.R
import com.example.expensely.application.TransactionApplication
import com.example.expensely.databinding.FragmentTransactionDetailsBinding
import com.example.expensely.utils.Constants
import com.example.expensely.view.activities.AddUpdateTransactionActivity
import com.example.expensely.viewmodels.TransactionViewModel
import com.example.expensely.viewmodels.TransactionViewModelFactory


class TransactionDetailsFragment : Fragment(), View.OnClickListener {


    lateinit var binding: FragmentTransactionDetailsBinding
    private val args: TransactionDetailsFragmentArgs by navArgs()

    private val transactionViewModel: TransactionViewModel by viewModels {
        TransactionViewModelFactory((requireActivity().application as TransactionApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTransactionDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        binding.title.text = args.transactionDetails.title
        binding.amount.text = args.transactionDetails.amount.toString()
        binding.type.text = args.transactionDetails.transactionType
        binding.tag.text = args.transactionDetails.tags
        binding.date.text = args.transactionDetails.date
        binding.note.text = args.transactionDetails.note
        binding.createdAt.text = args.transactionDetails.createdAt

        binding.btnEdit.setOnClickListener(this)
        binding.btnDelete.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.btnEdit -> {
                editTransaction(args);
                return
            }
            R.id.btnDelete ->{
                deleteTransaction(args);
                return
            }

        }
    }

    private fun deleteTransaction(args: TransactionDetailsFragmentArgs) {

        transactionViewModel.delete(args.transactionDetails)
        Toast.makeText(this.requireContext(),"Transaction Deleted",Toast.LENGTH_SHORT).show()
        findNavController().navigate(TransactionDetailsFragmentDirections.actionTransactionDetailsFragmentToDashboard())


    }

    private fun editTransaction(args: TransactionDetailsFragmentArgs) {
        val intent= Intent(requireActivity(),AddUpdateTransactionActivity::class.java)
          intent.putExtra(Constants.EXTRA_TRANSACTION_DETAILS,args.transactionDetails)
         startActivity(intent)





    }


}