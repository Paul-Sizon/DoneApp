package com.example.mytodo.network.model

//data class Post(
//    val userId: Int,
//    val id: Int,
//    val title: String,
//    val body: String,
//
//)
data class Post(
    val content: String,
    val author: String,
    val noInt: String = "no internet"


    )