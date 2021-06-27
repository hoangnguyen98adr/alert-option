package com.example.navproject.screens.common

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.navproject.R
import com.example.navproject.adapters.DialogOptionItemAdapter
import com.example.navproject.models.AlertModel
import kotlinx.android.synthetic.main.dialog_option_todo.*

class TodoDialogFragment(var list: ArrayList<AlertModel>, private val callback: (Int, String) -> Unit) : DialogFragment() {
    private val mDialogOptionItemAdapter = DialogOptionItemAdapter()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        mDialogOptionItemAdapter.setList(list)
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_option_todo)
        setupRecyclerView(dialog)
        initListener(dialog)
        return dialog
    }

    private fun initListener(dialog: Dialog) {
        mDialogOptionItemAdapter.setCallback { alertKey, alertField ->
            callback.invoke(alertKey, alertField)
            dialog.dismiss()
        }
    }

    private fun setupRecyclerView(dialog: Dialog) {
        dialog.rvTodoDialog.apply {
            adapter = mDialogOptionItemAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    companion object {
        const val TODO_OPTION_DIALOG = "TODO_OPTION_DIALOG"
    }
}