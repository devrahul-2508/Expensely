package com.example.expensely.view.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.expensely.application.TransactionApplication
import com.example.expensely.databinding.FragmentStatsBinding
import com.example.expensely.entities.Transaction
import com.example.expensely.viewmodels.TransactionViewModel
import com.example.expensely.viewmodels.TransactionViewModelFactory
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate


class StatsFragment : Fragment() {


    lateinit var binding: FragmentStatsBinding

    private val transactionViewModel: TransactionViewModel by viewModels {
        TransactionViewModelFactory((requireActivity().application as TransactionApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentStatsBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPieChart()
        observeTransaction()
    }

    private fun observeTransaction() {
        transactionViewModel.allTransactions.observe(viewLifecycleOwner){
            transactions->
               displayInPieChart(transactions);
        }
    }

    private fun displayInPieChart(transactions: List<Transaction>?) {


            var housing=0.0
            var transportation=0.0
            var food=0.0
            var utilities=0.0
            var insurance=0.0
            var healthCare=0.0
            var savingDebts=0.0
            var personalSpending=0.0
            var entertainment=0.0
            var miscellaneous=0.0

        for (i in transactions!!){
           when(i.tags){
               "Housing" -> {
                   housing += i.amount
               }
               "Transportation" -> {
                   transportation+=i.amount
               }
               "Food" -> {
                   food+=i.amount
               }
               "Utilities" -> {
                   utilities+=i.amount
               }
               "Insurance" -> {
                   insurance+=i.amount
               }
               "Healthcare" -> {
                   healthCare+=i.amount
               }
               "Saving & Debts" -> {
                   savingDebts+=i.amount
               }
               "Personal Spending" -> {
                   personalSpending+=i.amount
               }
               "Entertainment" -> {
                   entertainment+=i.amount
               }
               "Miscellaneous" -> {
                   miscellaneous+=i.amount
               }

           }
        }
        loadPieChart(housing,transportation,food,utilities,insurance,healthCare,savingDebts,personalSpending,entertainment,miscellaneous)
    }


    private fun setupPieChart() {
        binding.pieChart.isDrawHoleEnabled = true
        binding.pieChart.setUsePercentValues(true)
        binding.pieChart.setEntryLabelTextSize(12F)
        binding.pieChart.setEntryLabelColor(Color.BLACK)
        binding.pieChart.centerText = "Spending by Category"
        binding.pieChart.setCenterTextSize(24F)
        binding.pieChart.description.isEnabled = false
        val l: Legend = binding.pieChart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.isEnabled = true
    }

    private fun loadPieChart(
        housing: Double,
        transportation: Double,
        food: Double,
        utilities: Double,
        insurance: Double,
        healthCare: Double,
        savingDebts: Double,
        personalSpending: Double,
        entertainment: Double,
        miscellaneous: Double
    ) {
     val entries:ArrayList<PieEntry> = arrayListOf()
        entries.add(PieEntry(housing.toFloat(), "Housing"))
        entries.add(PieEntry(transportation.toFloat(), "Transportation"))
        entries.add(PieEntry(food.toFloat(), "Food"))
        entries.add(PieEntry(utilities.toFloat(), "Utilities"))
        entries.add(PieEntry(insurance.toFloat(), "Insurance"))
        entries.add(PieEntry(healthCare.toFloat(), "HealthCare"))
        entries.add(PieEntry(savingDebts.toFloat(), "Saving & Debts"))
        entries.add(PieEntry(personalSpending.toFloat(), "Personal Spending"))
        entries.add(PieEntry(entertainment.toFloat(), "Entertainment"))
        entries.add(PieEntry(miscellaneous.toFloat(), "Miscellaneous"))

        val colors: ArrayList<Int> = ArrayList()
        for (color in ColorTemplate.JOYFUL_COLORS) {
            colors.add(color)
        }
        val dataSet = PieDataSet(entries, "Expense Category")
        dataSet.colors = colors

        val data = PieData(dataSet)
        data.setDrawValues(true)
        data.setValueFormatter(PercentFormatter(binding.pieChart))
        data.setValueTextSize(12f)
        data.setValueTextColor(Color.BLACK)

        binding.pieChart.data=data
        binding.pieChart.invalidate()

        binding.pieChart.animateY(1400, Easing.EaseInOutQuad)

    }



}