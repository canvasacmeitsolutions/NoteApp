package com.fabfinance.noteapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fabfinance.noteapp.AddNoteActvity
import com.fabfinance.noteapp.databinding.ItemNoteRowBinding
import com.fabfinance.noteapp.model.Note
/*noteList[position]*/
class NoteAdapter(var context : Context, var noteList: List<Note>, private var onItemClicked: ((note: Note, position: Int) -> Unit)) : RecyclerView.Adapter<NoteAdapter.NoteView>() {

    class NoteView(var binding : ItemNoteRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteView {
        val view = ItemNoteRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NoteView(view)
    }

    override fun onBindViewHolder(holder: NoteView, position: Int) {
        holder.binding.txtTitle.text = noteList[position].noteTitle
        holder.binding.txtNote.text = noteList[position].noteDescription
        holder.binding.imgEdit.setOnClickListener {
           // onItemClicked(noteList[position])
          context.startActivity(Intent(context,AddNoteActvity::class.java).putExtra("noteType","Edit")
              .putExtra("noteTitle", noteList[position].noteTitle)
            .putExtra("noteDescription", noteList[position].noteDescription)
            .putExtra("noteId", noteList[position].id))
        }

        holder.binding.imgDelete.setOnClickListener {
            onItemClicked(noteList[position],position)
            notifyItemRemoved(position)
        }
    }

    override fun getItemCount(): Int {
        return noteList.size
    }
}