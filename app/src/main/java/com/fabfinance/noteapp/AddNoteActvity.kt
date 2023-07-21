package com.fabfinance.noteapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.fabfinance.noteapp.adapter.NoteAdapter
import com.fabfinance.noteapp.databinding.ActivityAddNoteActvityBinding
import com.fabfinance.noteapp.model.Note
import com.fabfinance.noteapp.viewmodel.NoteViewModal
import java.text.SimpleDateFormat
import java.util.*

class AddNoteActvity : AppCompatActivity() {

    lateinit var binding : ActivityAddNoteActvityBinding
    lateinit var viewModal: NoteViewModal
    var noteID = -1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteActvityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var noteType : String = intent.getStringExtra("noteType").toString()
            var intentnoteId = intent.getIntExtra("noteId",0)
            var intent_noteTitle = intent.getStringExtra("noteTitle").toString()
            var intent_noteDescription = intent.getStringExtra("noteDescription").toString()



        if (intent_noteTitle.isNotEmpty() &&! intent_noteTitle.equals("null") && intent_noteTitle != null && intent_noteDescription.isNotEmpty() && intent_noteDescription != null && !intent_noteDescription.equals("null")){
            binding.edtNote.setText( intent_noteDescription)
            binding.edtNoteTitle.setText(intent_noteTitle)
        }else{
            binding.edtNote.setText("")
            binding.edtNoteTitle.setText("")
        }

        Log.e("TAG", "onCreate: noteType= $noteType" )
        if (noteType == "Save"){
            binding.btnSave.text = "Save Note"
        }else{
            binding.btnSave.text = "Update Note"
        }

        viewModal = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(NoteViewModal::class.java)

        binding.btnSave.setOnClickListener {
            val noteText : String = binding.edtNote.text.toString()
            val noteTitle : String = binding.edtNoteTitle.text.toString()

            Log.e("TAG", "onCreate: value of note title = $noteTitle note = $noteText")
            if (noteText.isEmpty() || noteText == ""){
                Log.e("TAG", "onCreate: come inside if" )
                Toast.makeText(this,"Please enter the note to save",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if (noteTitle.isEmpty() || noteTitle == ""){
                Log.e("TAG", "onCreate: come inside else if" )
                Toast.makeText(this,"Please enter the note title to save",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else{
                Log.e("TAG", "onCreate: come inside else" )

                if (noteType == "Save"){
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDateAndTime: String = sdf.format(Date())
                    viewModal.addNote(Note(noteTitle, noteText, currentDateAndTime))
                    Toast.makeText(this, "Note Added successfully", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()

                }else{
                    Log.e("TAG", "onCreate: value of note title = $noteTitle note = $noteText")
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDateAndTime: String = sdf.format(Date())
                    val updatedNote = Note(noteTitle, noteText, currentDateAndTime)
                    if (intentnoteId != null){
                        updatedNote.id = intentnoteId
                        viewModal.updateNote(updatedNote)
                        Toast.makeText(this, "Note Updated successfully", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this,MainActivity::class.java))
                        finish()
                    }else{
                      Toast.makeText(this,"Id not found",Toast.LENGTH_SHORT).show()
                    }

                }

            }


        }


    }
}