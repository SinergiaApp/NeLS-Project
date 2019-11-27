package com.sinergia.eLibrary.domain.interactors.RegisterInteractor

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.sinergia.eLibrary.domain.UseCases.UserUseCase

class RegisterInteractorImpl: RegisterInteractor {

    override fun register(name: String, lastName: String, email: String, password: String, listener: RegisterInteractor.RegisterCallBack) {

        val userUseCase = UserUseCase()

        FirebaseAuth
            .getInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{ registerTask ->
                if(registerTask.isSuccessful){

                    var profileUpdates = UserProfileChangeRequest
                        .Builder()
                        .setDisplayName(name)
                        .build()

                    FirebaseAuth.getInstance().currentUser?.updateProfile(profileUpdates)!!
                        .addOnCompleteListener{updateProfileTask ->
                            if(updateProfileTask.isSuccessful) {
                                listener.onRegisterSuccess()
                                userUseCase.addUserDB(name, lastName, email, password)
                            }
                        }

                } else {
                    listener.onRegisterFailure(registerTask.exception?.message.toString())
                }
            }

    }

}