package com.sinergia.eLibrary.presentation.Account.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import com.sinergia.eLibrary.R
import com.sinergia.eLibrary.base.BaseActivity
import com.sinergia.eLibrary.data.Model.User
import com.sinergia.eLibrary.domain.interactors.AccountInteractor.AccountInteractor
import com.sinergia.eLibrary.domain.interactors.AccountInteractor.AccountInteractorImpl
import com.sinergia.eLibrary.presentation.Account.AccountContract
import com.sinergia.eLibrary.presentation.Account.Model.AccountViewModel
import com.sinergia.eLibrary.presentation.Account.Model.AccountViewModelImpl
import com.sinergia.eLibrary.presentation.Account.Presenter.AccountPresenter
import com.sinergia.eLibrary.presentation.Dialogs.ConfirmDialog.ConfirmDialog
import com.sinergia.eLibrary.presentation.Main.View.MainActivity
import com.sinergia.eLibrary.presentation.MainMenu.View.MainMenuActivity
import com.sinergia.eLibrary.presentation.NeLSProject
import kotlinx.android.synthetic.main.activity_account.*
import kotlinx.android.synthetic.main.activity_account.main_page_title
import kotlinx.android.synthetic.main.activity_account.menu_button
import kotlinx.android.synthetic.main.activity_admin_zone.*
import net.glxn.qrgen.android.QRCode

class AccountActivity : BaseActivity(), AccountContract.AccountView {

    var TAG = "[ACCOUNT_ACTIVITY]"

    lateinit var accountPresenter: AccountPresenter
    lateinit var accountViewModel: AccountViewModel

    // BASE ACTIVITY METHODS
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        main_page_title.text = getPageTitle()
        menu_button.setOnClickListener { startActivity(Intent(this, MainMenuActivity::class.java)) }

        accountPresenter = AccountPresenter(AccountViewModelImpl(), AccountInteractorImpl())
        accountPresenter.attachView(this)
        accountViewModel = ViewModelProviders.of(this).get(AccountViewModelImpl::class.java)

        initAccountContent()

        account_logout.setOnClickListener { logOut() }
        account_update_btn.setOnClickListener { updateAccount() }
        account_delete_btn.setOnClickListener { deleteAccount() }

    }

    override fun getLayout(): Int {
        return R.layout.activity_account
    }

    override fun getPageTitle(): String {
        return "Mi Cuenta"
    }

    // ACOUNT VIEW METHODS
    override fun showError(error: String?) {
        toastL(this, error)
    }

    override fun showMessage(message: String) {
        toastL(this, message)
    }

    override fun showProgressBar() {
        accountProgressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        accountProgressBar.visibility = View.INVISIBLE
    }

    override fun enableAllButtons() {
        account_update_btn.isEnabled = true
        account_update_btn.isClickable = true
        account_delete_btn.isEnabled = true
        account_delete_btn.isClickable = true
    }

    override fun disableAllButtons() {
        account_update_btn.isEnabled = false
        account_update_btn.isClickable = false
        account_delete_btn.isEnabled = false
        account_delete_btn.isClickable = false
    }

    override fun initAccountContent() {
        account_nameHead.text = "${NeLSProject.currentUser.name} ${NeLSProject.currentUser.lastName1}"
        account_mailHead.text = NeLSProject.currentUser.email

        account_userName.setText(NeLSProject.currentUser.name)
        account_userLastName1.setText(NeLSProject.currentUser.lastName1)
        account_userLastName2.setText(NeLSProject.currentUser.lastName2)
        account_userMail.setText(NeLSProject.currentUser.email)
        account_userNIF.setText(NeLSProject.currentUser.nif)

        account_userQR.setImageBitmap(QRCode.from(NeLSProject.currentUser.email).bitmap())
    }

    override fun logOut() {
        accountPresenter.logOut()
    }

    override fun updateAccount() {

        var newName = account_userName.text.toString()
        var newLastName1 = account_userLastName1.text.toString()
        var newLastName2 = account_userLastName2.text.toString()
        var newEmail = account_userMail.text.toString()
        var newNIF = account_userNIF.text.toString()

        var newUserAccount = User(
            newName,
            newLastName1,
            newLastName2,
            newEmail,
            newNIF,
            NeLSProject.currentUser.reserves,
            NeLSProject.currentUser.loans,
            NeLSProject.currentUser.favorites,
            NeLSProject.currentUser.admin
        )

        accountPresenter.updateAccount(newUserAccount)

    }

    override fun deleteAccount() {

        val reserveDialog = ConfirmDialog
            .Buider()
            .setTitleText("Confirmar Eliminación Permanente de Cuenta")
            .setDescriptionText(
                "Está a punto de elimnar de forma permanente su cuenta de la aplicación NeLS  " +
                        "(Neurolingüistic eLibrary for Students), ¿Desea continuar?."
            )
            .setAcceptButtonText("CONTINUAR")
            .setCancelButtonText("CANCELAR")
            .buid()

        reserveDialog.show(supportFragmentManager!!, "ReserveDialog")
        reserveDialog.isCancelable = false
        reserveDialog.setDialogOnClickButtonListener(object: ConfirmDialog.DialogOnClickButtonListener{
            override fun clickAcceptButton() {
                if(!accountPresenter.userCanBeDeleted(NeLSProject.currentUser.reserves, NeLSProject.currentUser.loans)){

                    if(!accountPresenter.checkEmptyAcountReserves(NeLSProject.currentUser.reserves)) showError("Tienes reservas pendientes, no puedes eliminar tu cuenta.")
                    if(!accountPresenter.checkEmptyAcountLoans(NeLSProject.currentUser.loans)) showError("Tienes reservas pendientes, no puedes eliminar tu cuenta.")

                } else {
                    accountPresenter.deleteAccount(NeLSProject.currentUser)
                }
                reserveDialog.dismiss()
            }

            override fun clickCancelButton() {
                reserveDialog.dismiss()
            }

        })

    }

    override fun navigateToMainPage() {
        val intentMainPage = Intent(this, MainActivity::class.java)
        intentMainPage.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intentMainPage)
    }

}
