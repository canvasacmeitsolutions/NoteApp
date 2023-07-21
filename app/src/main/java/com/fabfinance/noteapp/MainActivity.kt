package com.fabfinance.noteapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fabfinance.noteapp.adapter.NoteAdapter
import com.fabfinance.noteapp.databinding.ActivityMainBinding
import com.fabfinance.noteapp.model.Note
import com.fabfinance.noteapp.viewmodel.NoteViewModal

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    lateinit var viewModal: NoteViewModal
    var position :Int = 0
    var noteList = listOf<Note>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModal = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(NoteViewModal::class.java)

        showList()

        binding.swipe.setOnRefreshListener {
            //binding.recyclerNote.adapter?.notifyItemRemoved(position)
            showList()
            binding.swipe.isRefreshing = false
        }

        binding.btnAddNote.setOnClickListener {
            startActivity(Intent(this@MainActivity,AddNoteActvity::class.java)
                .putExtra("noteType", "Save"))
            finish()
        }

    }
    fun showList(){
        viewModal.allNotes.observe(this, Observer { list ->

            if (list.isEmpty()){
                Toast.makeText(this,"No Data is available",Toast.LENGTH_SHORT).show()
                binding.recyclerNote.adapter = NoteAdapter(this,list, onItemClicked = { note: Note, i: Int ->
                })
            }else{
                Log.e("TAG", "onCreate: note list = $list")
                binding.recyclerNote.adapter = NoteAdapter(this,list, onItemClicked = { note: Note, i: Int ->
                    viewModal.deleteNote(note)
                    Toast.makeText(this,"Note remove from database",Toast.LENGTH_SHORT).show()
                    /*startActivity(Intent(this,MainActivity::class.java))
                    finish()*/
                })

            }
        })

    }
}