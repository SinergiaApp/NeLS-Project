package com.sinergia.eLibrary.presentation.Dialogs.ConfirmDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.sinergia.eLibrary.R
import kotlinx.android.synthetic.main.confirm_dialog.*

class ConfirmDialog private constructor(

    private val acceptButtonText: String?,
    private val cancelButtonText: String?,
    private val titleText: String?,
    private val descriptionText: String?

): DialogFragment() {

    data class Buider(
        private var acceptButtonText: String? = null,
        private var cancelButtonText: String? = null,
        private var titleText: String? = null,
        private var descriptionText: String? = null
    ){
        fun setAcceptButtonText(acceptButtonText: String) = apply { this.acceptButtonText = acceptButtonText }
        fun setCancelButtonText(cancelButtonText: String) = apply { this.cancelButtonText = cancelButtonText }
        fun setTitleText(titleText: String) = apply { this.titleText = titleText }
        fun setDescriptionText(descriptionText: String) = apply { this.descriptionText = descriptionText }

        fun buid() = ConfirmDialog(acceptButtonText, cancelButtonText, titleText, descriptionText)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.confirm_dialog, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        confirm_dialog_accept_btn.text = acceptButtonText
        confirm_dialog_cancel_btn.text = cancelButtonText
        confirm_dialog_title.text = titleText
        confirm_dialog_description.text = descriptionText
    }

}