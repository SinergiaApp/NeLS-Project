package com.sinergia.eLibrary.data.Model

import com.google.firebase.firestore.GeoPoint

data class Article (

    var title: String = "Desconcido",
    var authors: List<String> = emptyList(),
    var year: Int = 0,
    var source: String = "Desconcido",
    var issn: String = "Desconcido",
    var descriptiom: String = "Desconcido",
    var category: String = "Desconocido",
    var owner: String = "Desconocido",
    var downloadURI: String = "Desconcido",
    var id: String = "Identificador Desconocido"

)