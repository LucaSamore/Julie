package com.example.data

interface Identifier

interface Entity<I> where I : Identifier {
    val id: I
}
