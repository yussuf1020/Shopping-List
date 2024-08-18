package com.example.shoppinglistrev

data class ShoppingItem(
    val id : Int,
    var quantity : Int,
    var isEditing : Boolean = false
    )
