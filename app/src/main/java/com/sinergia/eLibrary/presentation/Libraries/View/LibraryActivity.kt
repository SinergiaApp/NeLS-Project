package com.sinergia.eLibrary.presentation.Libraries.View

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.sinergia.eLibrary.R
import com.sinergia.eLibrary.base.BaseActivity
import com.sinergia.eLibrary.data.Model.Library
import com.sinergia.eLibrary.presentation.Libraries.LibraryContract
import com.sinergia.eLibrary.presentation.Libraries.Model.LibraryViewModel
import com.sinergia.eLibrary.presentation.Libraries.Model.LibraryViewModelImpl
import com.sinergia.eLibrary.presentation.Libraries.Presenter.LibraryPresenter
import com.sinergia.eLibrary.presentation.Main.View.MainActivity
import com.sinergia.eLibrary.presentation.MainMenu.View.MainMenuActivity
import com.sinergia.eLibrary.presentation.NeLSProject
import kotlinx.android.synthetic.main.activity_library.*
import kotlinx.android.synthetic.main.layout_headder_bar.*

class LibraryActivity : BaseActivity(), LibraryContract.LibraryView {

    private lateinit var libraryPresenter: LibraryContract.LibraryPresenter
    private lateinit var libraryViewModel: LibraryViewModel


    //BASE ACTIVITY METHODS
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        libraryPresenter = LibraryPresenter(LibraryViewModelImpl())
        libraryPresenter.attachView(this)
        libraryViewModel= LibraryViewModelImpl()

        page_title.text = getPageTitle()
        menu_button.setOnClickListener { startActivity(Intent(this, MainMenuActivity::class.java)) }

        libraryPresenter.getLibrary(NeLSProject.currentLibrary!!.id)


    }

    override fun getLayout(): Int {
        return R.layout.activity_library
    }

    override fun getPageTitle(): String {
        return NeLSProject.currentLibrary!!.name
    }

    override fun backButton() {
        if(NeLSProject.backButtonPressedTwice){
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra("EXIT", true)
            startActivity(intent)
        } else {
            toastL(this, getString(R.string.BTN_BACK))
            NeLSProject.backButtonPressedTwice = true
        }
    }


    //LIBRARY CONTRACT METHODS
    override fun showMessage(message: String?) {
        toastL(this, message)
    }

    override fun showError(errorMsg: String?) {
        toastL(this, errorMsg)
    }

    override fun showLibraryProgressBar() {
        library_progressBar.visibility = View.VISIBLE
    }

    override fun hideLibraryProgressBar() {
        library_progressBar.visibility = View.GONE
    }

    override fun showLibraryContent() {
        library_content.visibility = View.VISIBLE
    }

    override fun hideLibraryContent() {
        library_content.visibility = View.GONE
    }

    override fun initLibraryContent(library: Library?) {

        library_name.text =  library?.name
        library_address.text = library?.address

        if(library!!.imageUri != "noImage"){
            Glide
                .with(this)
                .load(Uri.parse(library.imageUri))
                .fitCenter()
                .centerCrop()
                .into(library_image)
        }

    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        libraryPresenter.dettachView()
    }

    override fun onDestroy() {
        super.onDestroy()
        libraryPresenter.dettachView()
    }

}
