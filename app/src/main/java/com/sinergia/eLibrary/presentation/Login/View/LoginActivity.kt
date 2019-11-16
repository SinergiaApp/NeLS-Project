package com.sinergia.eLibrary.presentation.Login.View

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.sinergia.eLibrary.R
import com.sinergia.eLibrary.base.BaseActivity
import com.sinergia.eLibrary.domain.interactors.LoginInteractor.LoginInteractorImpl
import com.sinergia.eLibrary.presentation.ForgotPassword.View.ForgotPasswordActivity
import com.sinergia.eLibrary.presentation.Login.LoginContract
import com.sinergia.eLibrary.presentation.Login.Presenter.LoginPresenter
import com.sinergia.eLibrary.presentation.Main.View.MainActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(), LoginContract.LoginView {

    //BASEACTIVITY METHODS
    lateinit var presenter: LoginPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = LoginPresenter(LoginInteractorImpl())
        presenter.attachView(this)

        login_pass_forgotten.setOnClickListener() { forgotPass() }
        main_login_btn.setOnClickListener(){ login() }

    }

    override fun getLayout(): Int {
        return R.layout.activity_login
    }


    //LOGIN CONTRACT METHODS
    override fun showError(error: String) {
        toastS(this, error)
    }

    override fun showMessage(message: String) {
        toastS(this, message)
    }

    override fun showProgressBar() {
        login_progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        login_progressBar.visibility = View.INVISIBLE
    }

    override fun forgotPass() {
        var forgotPasswordIntent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
        startActivity(forgotPasswordIntent)
    }

    override fun navigateToMainPage() {
        val intentBack = Intent(this, MainActivity::class.java)
        intentBack.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intentBack)
    }

    override fun login(){

        val email = login_user.text.toString().trim()
        val password = login_pass.text.toString().trim()

        if (presenter.checkEmptyLoginFields(email, password)){
            showError("Todos los campos son oblicatorios, por favor completa el formulario completo.")
        } else {
            presenter.logInWithEmailAndPassword(email, password)
        }

    }

    override fun googleLogin() {
        toastL(this, "Esto aun no va!! =(, no le des mas al botón pesadilla !!")
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        presenter.dettachView()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dettachView()
    }

}