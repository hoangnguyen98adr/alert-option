package com.example.navproject.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.example.navproject.R
import com.example.navproject.constants.AlertType
import com.example.navproject.extensions.watchText
import com.example.navproject.models.AlertModel
import com.example.navproject.models.UpdateAlertModel
import com.example.navproject.screens.common.TodoDialogFragment
import com.example.navproject.screens.common.TodoDialogFragment.Companion.TODO_OPTION_DIALOG
import com.example.navproject.viewmodels.TodoViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.layout_alert_field.view.*

class HomeFragment : Fragment(), View.OnClickListener {
    private lateinit var mTodoViewModel: TodoViewModel
    private var mCurrentOptionPrice: Int? = 0

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
        initObserver()
        initListener()
    }

    private fun initObserver() {
        mTodoViewModel.mListDataMockAPI.observe(viewLifecycleOwner) {
            updateOptionAlert()
            layoutPriceDoneUp.apply {
                initViewsFromAlertData(this, mTodoViewModel.getDataFromAlert(PRICE_DONE_UP))
            }
            layoutPriceDoneDown.apply {
                initViewsFromAlertData(this, mTodoViewModel.getDataFromAlert(PRICE_DONE_DOWN))
            }
            layoutChangeUp.apply {
                initViewsFromAlertData(this, mTodoViewModel.getDataFromAlert(type = AlertType.CHANGE_UP.type.alertType))
            }
            layoutChangeDown.apply {
                initViewsFromAlertData(this, mTodoViewModel.getDataFromAlert(type = AlertType.CHANGE_DOWN.type.alertType))
            }
            layoutVolUp.apply {
                initViewsFromAlertData(this, mTodoViewModel.getDataFromAlert(type = AlertType.VOL_UP.type.alertType))
            }
        }
    }

    private fun updateOptionAlert() {
        val optionPriceDone = when (mTodoViewModel.getCurrentKeyAlert()) {
            BID_PRICE -> "bid price"
            ASK_PRICE -> "ask price"
            else -> "last done"
        }
        tvAlertOption.text = optionPriceDone
        mCurrentOptionPrice = mTodoViewModel.getCurrentKeyAlert()
    }

    private fun initViewsFromAlertData(view: View, dataPriceDone: AlertModel?) {
        view.edtPrice.setText(dataPriceDone?.alertValue)
        view.swPrice.isChecked = dataPriceDone != null
    }

    private fun initAlertUpdateModel(
        view: View,
        alertKey: Int? = null,
        type: String? = null
    ): UpdateAlertModel {
        return UpdateAlertModel(
            if (type == null) mTodoViewModel.getDataFromAlert(alertKey)?.alertID
            else mTodoViewModel.getDataFromAlert(type = type)?.alertID,
            view.swPrice.isChecked,
            (type ?: mTodoViewModel.getAlertType(alertKey, mCurrentOptionPrice)?.type?.alertType),
            view.edtPrice.text.toString()
        )
    }

    private fun getDataFromViews() {
        val alertList = ArrayList<UpdateAlertModel>()
        layoutPriceDoneUp.apply {
            initAlertUpdateModel(this, PRICE_DONE_UP).let { alertList.add(it) }
        }
        layoutPriceDoneDown.apply {
            initAlertUpdateModel(this, PRICE_DONE_DOWN).let { alertList.add(it) }
        }
        layoutChangeUp.apply {
            initAlertUpdateModel(this, type =  AlertType.CHANGE_UP.type.alertType).let { alertList.add(it) }
        }
        layoutChangeDown.apply {
            initAlertUpdateModel(this, type =  AlertType.CHANGE_DOWN.type.alertType).let { alertList.add(it) }
        }
        layoutVolUp.apply {
            initAlertUpdateModel(this, type =  AlertType.VOL_UP.type.alertType).let { alertList.add(it) }
        }
        val result = alertList
    }

    private fun initListener() {
        tvAlertOption.setOnClickListener(this)
        btnCreate.setOnClickListener(this)
        layoutPriceDoneUp.apply {
            initListenerWithWatchText(this)
            initCheckedChangeListener(this)
        }
        layoutPriceDoneDown.apply {
            initListenerWithWatchText(this)
            initCheckedChangeListener(this)
        }
        layoutChangeUp.apply {
            initListenerWithWatchText(this)
            initCheckedChangeListener(this)
        }
        layoutChangeDown.apply {
            initListenerWithWatchText(this)
            initCheckedChangeListener(this)
        }
        layoutVolUp.apply {
            initListenerWithWatchText(this)
            initCheckedChangeListener(this)
        }
    }

    private fun initCheckedChangeListener(view: View) {
        view.swPrice.setOnCheckedChangeListener { _, isChecked -> if(!isChecked) view.edtPrice.setText("") }
    }

    private fun initListenerWithWatchText(view: View) {
        view.edtPrice.watchText { view.swPrice.isChecked = !it.isNullOrEmpty() && it.toString().toDouble() > 0 }
    }

    override fun onClick(v: View?) {
        when (v) {
            tvAlertOption -> {
                mTodoViewModel.mListOptionAlertLiveData.value?.let {
                    TodoDialogFragment(it) { alertKey, alertField ->
                        tvAlertOption.text = alertField
                        mCurrentOptionPrice = alertKey
                    }.show(childFragmentManager, TODO_OPTION_DIALOG)
                }
            }
            btnCreate -> getDataFromViews()
        }
    }

    companion object {
        const val PRICE_DONE_UP = 0
        const val PRICE_DONE_DOWN = 1
        private const val BID_PRICE = 1
        private const val ASK_PRICE = 2

        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}