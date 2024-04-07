package com.example.data

interface Entity<I> where I : Identifier {
    val id: I
}
