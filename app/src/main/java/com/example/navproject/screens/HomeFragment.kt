package com.example.navproject.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.example.navproject.R
import com.example.navproject.constants.TodoEnum
import com.example.navproject.models.UpdateAlertModel
import com.example.navproject.screens.common.TodoDialogFragment
import com.example.navproject.screens.common.TodoDialogFragment.Companion.TODO_OPTION_DIALOG
import com.example.navproject.viewmodels.TodoViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), View.OnClickListener {
    private lateinit var mTodoViewModel: TodoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mTodoViewModel = TodoViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDefaultValue()
        initObserver()
        initListener()
    }

    private fun initObserver() {
        mTodoViewModel.mListAlertFieldLiveData.observe(viewLifecycleOwner) {
            edtPriceUp.setText(it.first().type.alertValue.toString())
            swPriceUp.isChecked = mTodoViewModel.mIsPriceUp
            edtPriceDown.setText(it.last().type.alertValue.toString())
            swPriceDown.isChecked = mTodoViewModel.mIsPriceDown
        }
    }

    private fun setupDefaultValue() {
        TodoEnum.AlertType.LAST_DONE_UP.type.alertField?.let { mTodoViewModel.getAlertType(it) }
    }

    private fun initListener() {
        tvPriceDone.setOnClickListener(this)
        btnCreate.setOnClickListener(this)
        swPriceUp.setOnClickListener(this)
        swPriceDown.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            tvPriceDone -> {
                mTodoViewModel.mListOptionTodoLiveData.value?.let { list ->
                    TodoDialogFragment(list) { alertDisplay, alertField ->
                        tvPriceDone.text = alertDisplay
                        mTodoViewModel.getAlertType(alertField)
                    }.show(childFragmentManager, TODO_OPTION_DIALOG)
                }
                Log.d("hoangnd", "Rs ${mTodoViewModel.mListAlertFieldLiveData.value}")
            }
            swPriceUp -> mTodoViewModel.mIsPriceUp = swPriceUp.isChecked
            swPriceDown -> mTodoViewModel.mIsPriceDown = swPriceDown.isChecked
            btnCreate -> updateAlert()
        }
    }

    private fun updateAlert() {
        val dataAlert = ArrayList<UpdateAlertModel>()
        val firstItem = mTodoViewModel.mListAlertFieldLiveData.value?.first()
        val lastItem = mTodoViewModel.mListAlertFieldLiveData.value?.last()
        dataAlert.add(
            UpdateAlertModel(
                firstItem?.type?.alertID,
                mTodoViewModel.mIsPriceUp.toString(),
                firstItem?.type?.alertType.toString(),
                edtPriceUp.text.toString()
            )
        )
        dataAlert.add(
            UpdateAlertModel(
                lastItem?.type?.alertID,
                mTodoViewModel.mIsPriceDown.toString(),
                lastItem?.type?.alertType.toString(),
                edtPriceDown.text.toString()
            )
        )
        Log.d("hoangnd", "rs$dataAlert")
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}