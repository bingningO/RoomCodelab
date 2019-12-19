package com.example.codelabroom.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_table")
data class Word(
    @PrimaryKey(autoGenerate = true)
    private val id: Int,
    @ColumnInfo(name = "realWord")
    private val word: String
)