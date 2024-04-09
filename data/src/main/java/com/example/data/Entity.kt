package com.example.data

internal interface Entity<I> where I : Identifier {
    val id: I
}
