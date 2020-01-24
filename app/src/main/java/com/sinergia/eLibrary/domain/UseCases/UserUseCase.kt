package com.sinergia.eLibrary.domain.UseCases

import com.sinergia.eLibrary.data.Model.User
import com.sinergia.eLibrary.data.NeLS_DataBase.NelsDataBase

class UserUseCase {

    val nelsDB = NelsDataBase()

    suspend fun addUserDB(
        name: String,
        lastName1: String,
        lastName2: String,
        email: String,
        nif: String,
        loans: MutableList<String>,
        favorites: MutableList<String>,
        admin: Boolean
    ){
        nelsDB.addUser(name, lastName1, lastName2, email, nif, loans, favorites, admin)
    }

    suspend fun getUser(email: String): User{
        return nelsDB.getUser(email)
    }

}