package com.example.android.roomwordssample

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.roomwordssample.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity(), INotesRVAdapter {

    private lateinit var viewModel: NoteViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*//how to access database and it's method without mvvm
        val database = NoteDatabase.getDatabase(this)
        GlobalScope.launch {
            //add two field in database
            database.getNoteDao().insert(Note(0, "Hello", Date()))
            database.getNoteDao().insert(Note(0, "World", Date()))
        }
        //run this code onClick of some view
        GlobalScope.launch {
            val notesList = database.getNoteDao().getAllNotes()
            Log.e("NoteList", "notes: $notesList")
        }*/

        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        val adapter = NotesRVAdapter(this, this)
        binding.recyclerview.adapter = adapter

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)
        viewModel.allNotes.observe(owner = this) { list ->
            adapter.updateList(list)
        }

        binding.btnSave.setOnClickListener {
            val noteText = binding.edtNote.text.toString()
            if (noteText.isNotEmpty()) {
                viewModel.insertNote(Note(0, noteText, Date(), 0, 0))
                Toast.makeText(this, "$noteText Inserted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onItemClicked(note: Note) {
        viewModel.deleteNote(note)
        Toast.makeText(this, "${note.text} Deleted", Toast.LENGTH_SHORT).show()
    }
}