package com.example.android.roomwordssample

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/*@Entity(tableName = "note_table")
data class Note(@ColumnInfo(name = "text") val text: String) {
    @PrimaryKey(autoGenerate = true) var id = 0
}*/

@Entity(tableName = "note_table")
data class Note(
        @PrimaryKey(autoGenerate = true)
        var id: Long,
        val text: String,
        val createdDate: Date,
        val isCompleted: Int,    //0 for  not complete, 1 for complete
        val isImportant: Int
)
