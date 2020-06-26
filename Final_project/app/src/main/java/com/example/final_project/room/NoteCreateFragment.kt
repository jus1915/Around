package com.example.final_project.room

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.example.final_project.R
import com.kroegerama.imgpicker.BottomSheetImagePicker
import com.kroegerama.imgpicker.ButtonType
import kotlinx.android.synthetic.main.dialog_note_create.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class NoteCreateFragment : DialogFragment(),BottomSheetImagePicker.OnImagesSelectedListener {
    private var note =
        NoteEntity(
            noteContent = "",
            noteTitle = "",
            noteImage = null,
            noteTime = "",
            noteLocation = ""
        )
    var currentLocation : String = "위치정보 없음"

    private val dao by lazy { AppDatabase.getDatabase(requireContext()).noteDao() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.dialog_note_create, container, false)

        rootView.img_profile.setOnClickListener {
            BottomSheetImagePicker.Builder("빌드")
                .cameraButton(ButtonType.Button)
                .galleryButton(ButtonType.Button)
                .singleSelectTitle(R.string.select_title)
                .show(childFragmentManager)
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tag?.toLongOrNull()?.let{noteId ->
            viewLifecycleOwner.lifecycleScope.launch{
                var saveNote: NoteEntity? = null

                withContext(Dispatchers.IO){
                    saveNote=dao.selectNote(noteId)
                }

                saveNote?.let{
                    note=it
                    view.txt_title.setText(it.noteTitle)
                    view.txt_content.setText(it.noteContent)
                    view.txt_location.setText(it.noteLocation)
                    it.noteImage?.let{noteImage ->
                        view.img_profile.setImageURI(Uri.parse(noteImage))
                    }
                }
            }
        }
        view.btn_save.setOnClickListener {
            val now = System.currentTimeMillis()
            val mDate = Date(now)
            val sdfNow =
                SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
            note.noteTitle = view.txt_title.text.toString()
            note.noteContent = view.txt_content.text.toString()
            note.noteLocation=view.txt_location.text.toString()
            note.noteTime = sdfNow.format(mDate)
            if (note.noteTitle.isBlank() && note.noteContent.isBlank()){
                Toast.makeText(requireContext(),"제목과 내용을 입력해주세요",Toast.LENGTH_LONG).show()
            }else{
                viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO){
                    val note= NoteEntity(
                        noteIdx = note.noteIdx,
                        noteTitle = note.noteTitle,
                        noteContent = note.noteContent,
                        noteImage = note.noteImage,
                        noteLocation = note.noteLocation,
                        noteTime = note.noteTime
                    )
                    dao.insertNotes(note)
                    dismiss()
                }
            }
        }
    }

    override fun onImagesSelected(uris: List<Uri>, tag: String?) {
        if (uris.isNotEmpty()){
            note.noteImage=uris[0].toString()
            view?.img_profile?.setImageURI(uris[0]);
        }
    }
}