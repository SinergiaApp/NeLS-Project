package com.sinergia.eLibrary.presentation.Libraries

import com.sinergia.eLibrary.data.Model.Library

interface LibrariesContract {

    interface LibrariesView{

        fun showError(errorMsg: String?)
        fun showMessage(message: String?)
        fun showLibrariesProgressBar()
        fun hideLibrariesProgressBar()

        fun initLibrariesContent(librariesList: List<Library>)

    }

    interface LibrariesPresenter{

        fun attachView()
        fun dettachView()
        fun isViewAttached(view: LibrariesContract.LibrariesView): Boolean
        fun dettachJob()

        fun getAllLibraries()

    }

}