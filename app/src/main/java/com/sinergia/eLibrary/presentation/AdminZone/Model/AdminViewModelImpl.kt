package com.sinergia.eLibrary.presentation.AdminZone.Model

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.GeoPoint
import com.sinergia.eLibrary.data.Model.Library
import com.sinergia.eLibrary.data.Model.Resource
import com.sinergia.eLibrary.domain.UseCases.LibraryUseCases
import com.sinergia.eLibrary.domain.UseCases.ResourcesUseCases

class AdminViewModelImpl: ViewModel(), AdminViewModel {

    val resourceUseCase = ResourcesUseCases()
    val libraryUseCases = LibraryUseCases()

    //CREATE NEW RESOURCE FUNCTIONS
    override suspend fun addNewResource(
        titulo: String,
        autores: List<String>,
        isbn: String,
        edicion: String,
        editorial: String,
        sinopsis: String,
        disponibility: MutableMap<String, Integer>,
        likes: MutableList<String>,
        dislikes: MutableList<String>,
        isOnline: Boolean,
        urlOnline: String)
    {
        resourceUseCase.addResource(titulo, autores, isbn, edicion, editorial, sinopsis, disponibility, likes, dislikes, isOnline, urlOnline)
    }


    override suspend fun getResourceToModify(isbn: String): Resource? {
        return resourceUseCase.getResource(isbn)
    }
    override suspend fun setResource(resource: Resource) {
        resourceUseCase.setResource(resource)
    }

    override suspend fun getAllLibraries(): ArrayList<Library> {
        return libraryUseCases.getAllLibraries()
    }

    //CREATE NEW LIBRARY FUNCTION
    override suspend fun addNewLibrary(nombre: String, direccion: String, geopoint: GeoPoint) {
        libraryUseCases.addLibrary(nombre, direccion, geopoint)
    }

}