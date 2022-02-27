package com.example.kora

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.database.Cursor
import android.graphics.BitmapFactory
import android.graphics.BitmapFactory.decodeFile
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.kora.database.NotesDatabase
import com.example.kora.databinding.FragmentCreateNoteBinding
import com.example.kora.entities.Notes
import com.example.kora.util.NotesBottomSheetFragment
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.text.SimpleDateFormat
import java.util.*


class CreateNoteFragment : BaseFragment(), EasyPermissions.PermissionCallbacks,EasyPermissions.RationaleCallbacks{
    private var webLink = ""
    private var noteId = -1
    private var createNoteBinding: FragmentCreateNoteBinding? = null
    private var READ_STORAGE_PERM = 123
    private var REQUEST_CODE_IMAGE = 456
    var selectedColor = "#F1A503"
    private var selectedImagePath = ""
    var currentDate:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        noteId = requireArguments().getInt("noteId",-1)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        createNoteBinding = FragmentCreateNoteBinding.inflate(inflater,container, false)
        return createNoteBinding?.root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            CreateNoteFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createNoteBinding = FragmentCreateNoteBinding.bind(view)

        if (noteId != -1) {

            launch {
                context?.let {
                    var notes = NotesDatabase.getDatabase(it).noteDao().getSpecificNote(noteId)
                    createNoteBinding?.colorView?.setBackgroundColor(Color.parseColor(notes.color))
                    createNoteBinding?.etNoteTitle?.setText(notes.title)
                    createNoteBinding?.etNoteSubTitle?.setText(notes.subTitle)
                    createNoteBinding?.etNoteDesc?.setText(notes.noteText)
                    if (notes.imgPath != ""){
                        selectedImagePath = notes.imgPath!!
                        createNoteBinding?.imgNote?.setImageBitmap(decodeFile(notes.imgPath))
                        createNoteBinding?.layoutImage?.visibility = View.VISIBLE
                        createNoteBinding?.imgNote?.visibility = View.VISIBLE
                        createNoteBinding?.imgDelete?.visibility = View.VISIBLE
                    }else{
                        createNoteBinding?.layoutImage?.visibility = View.GONE
                        createNoteBinding?.imgNote?.visibility = View.GONE
                        createNoteBinding?.imgDelete?.visibility = View.GONE
                    }

                    if (notes.webLink != ""){
                        webLink = notes.webLink!!
                        createNoteBinding?.tvWebLink?.text = notes.webLink
                        createNoteBinding?.layoutWebUrl?.visibility = View.VISIBLE
                        createNoteBinding?.etWebLink?.setText(notes.webLink)
                        createNoteBinding?.imgUrlDelete?.visibility = View.VISIBLE
                    }else{
                        createNoteBinding?.imgUrlDelete?.visibility = View.GONE
                        createNoteBinding?.layoutWebUrl?.visibility = View.GONE
                    }
                }
            }
        }
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
            BroadcastReceiver, IntentFilter("bottom_sheet_action")
        )

        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        currentDate = sdf.format(Date())
        createNoteBinding!!.tvDateTime.text = currentDate

        createNoteBinding!!.colorView.setBackgroundColor(Color.parseColor(selectedColor))

        createNoteBinding!!.imgDone.setOnClickListener {
            if (noteId != -1){
                updateNote()
            }else{
                saveNote()
            }
        }

        //Backbutton
        createNoteBinding!!.imgBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        //bottom sheet dialog
        createNoteBinding!!.imgMore.setOnClickListener{
            val noteBottomSheetFragment = NotesBottomSheetFragment.newInstance(noteId)
            noteBottomSheetFragment.show(requireActivity().supportFragmentManager,"Note Bottom Sheet Fragment")
        }

        //delete image
        createNoteBinding!!.imgDelete.setOnClickListener {
            selectedImagePath = ""
            createNoteBinding!!.layoutImage.visibility = View.GONE

        }

        createNoteBinding!!.btnOk.setOnClickListener {
            if (createNoteBinding!!.etWebLink.text.toString().trim().isNotEmpty()){
                checkWebUrl()
            }else{
                Toast.makeText(requireContext(),"Url is Required",Toast.LENGTH_SHORT).show()
            }
        }

        createNoteBinding!!.btnCancel.setOnClickListener {
            if (noteId != -1){
                createNoteBinding!!.tvWebLink.visibility = View.VISIBLE
                createNoteBinding!!.layoutWebUrl.visibility = View.GONE
            }else{
                createNoteBinding!!.layoutWebUrl.visibility = View.GONE
            }

        }

        createNoteBinding!!.imgUrlDelete.setOnClickListener {
            webLink = ""
            createNoteBinding!!.tvWebLink.visibility = View.GONE
            createNoteBinding!!.imgUrlDelete.visibility = View.GONE
            createNoteBinding!!.layoutWebUrl.visibility = View.GONE
        }

        createNoteBinding!!.tvWebLink.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(createNoteBinding!!.etWebLink.text.toString()))
            startActivity(intent)
        }

    }

    //code for updating note
    private fun updateNote() {
        launch {

            context?.let {
                var notes = NotesDatabase.getDatabase(it).noteDao().getSpecificNote(noteId)

                notes.title = createNoteBinding?.etNoteTitle?.text.toString()
                notes.subTitle = createNoteBinding?.etNoteSubTitle?.text.toString()
                notes.noteText = createNoteBinding?.etNoteDesc?.text.toString()
                notes.dateTime = currentDate
                notes.color = selectedColor
                notes.imgPath = selectedImagePath
                notes.webLink = webLink

                NotesDatabase.getDatabase(it).noteDao().updateNote(notes)
                createNoteBinding?.etNoteTitle?.setText("")
                createNoteBinding?.etNoteSubTitle?.setText("")
                createNoteBinding?.etNoteDesc?.setText("")
                createNoteBinding?.layoutImage?.visibility = View.GONE
                createNoteBinding?.imgNote?.visibility = View.GONE
                createNoteBinding?.tvWebLink?.visibility = View.GONE
                requireActivity().supportFragmentManager.popBackStack()
            }
        }

    }

    //code for saving note
    private fun saveNote(){
        if (createNoteBinding?.etNoteTitle?.text.isNullOrEmpty()){
            Toast.makeText(context,"Note Title is Required", Toast.LENGTH_SHORT).show()
        }
        else if (createNoteBinding?.etNoteSubTitle?.text.isNullOrEmpty()){

            Toast.makeText(context,"Note Sub Title is Required",Toast.LENGTH_SHORT).show()
        }

        else if (createNoteBinding?.etNoteDesc?.text.isNullOrEmpty()){

            Toast.makeText(context,"Note Description is Required",Toast.LENGTH_SHORT).show()
        }
        else {
           launch {
                val notes = Notes()
                notes.title = createNoteBinding?.etNoteTitle?.text.toString()
                notes.subTitle = createNoteBinding?.etNoteSubTitle?.text.toString()
                notes.noteText = createNoteBinding?.etNoteDesc?.text.toString()
                notes.dateTime = currentDate
                notes.color = selectedColor
                notes.imgPath = selectedImagePath
                notes.webLink = webLink

                context?.let {
                    NotesDatabase.getDatabase(it).noteDao().insertNotes(notes)
                    createNoteBinding?.etNoteTitle?.setText("")
                    createNoteBinding?.etNoteSubTitle?.setText("")
                    createNoteBinding?.etNoteDesc?.setText("")
                    createNoteBinding?.layoutImage?.visibility = View.GONE
                    createNoteBinding?.imgNote?.visibility = View.GONE
                    createNoteBinding?.tvWebLink?.visibility = View.GONE
                    requireActivity().supportFragmentManager.popBackStack()
                }
            }
        }

    }
    //code for deleting note
    private fun deleteNote(){
        launch {
            context?.let {
                NotesDatabase.getDatabase(it).noteDao().deleteSpecificNote(noteId)
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }
    //function for checking url
    private fun checkWebUrl(){
        if (Patterns.WEB_URL.matcher(createNoteBinding!!.etWebLink.text.toString()).matches()){
            createNoteBinding!!.layoutWebUrl.visibility = View.GONE
            createNoteBinding!!.etWebLink.isEnabled = false
            webLink = createNoteBinding!!.etWebLink.text.toString()
            createNoteBinding!!.tvWebLink.visibility = View.VISIBLE
            createNoteBinding!!.tvWebLink.text = createNoteBinding!!.etWebLink.text.toString()
        }else{
            Toast.makeText(requireContext(),"Url is not valid",Toast.LENGTH_SHORT).show()
        }
    }
    //Broadcast Receiver Code
    private val BroadcastReceiver : BroadcastReceiver = object :BroadcastReceiver(){
        override fun onReceive(p0: Context?, p1: Intent?) {
            var actionColor = p1!!.getStringExtra("action")
            when(actionColor!!){
                "Blue" -> {
                    selectedColor = p1.getStringExtra("selectedColor")!!
                    createNoteBinding!!.colorView.setBackgroundColor(Color.parseColor(selectedColor))
                }
                "Yellow" -> {
                    selectedColor = p1.getStringExtra("selectedColor")!!
                    createNoteBinding!!.colorView.setBackgroundColor(Color.parseColor(selectedColor))
                }
                "Red" -> {
                    selectedColor = p1.getStringExtra("selectedColor")!!
                    createNoteBinding!!.colorView.setBackgroundColor(Color.parseColor(selectedColor))
                }
                "Purple" -> {
                    selectedColor = p1.getStringExtra("selectedColor")!!
                    createNoteBinding!!.colorView.setBackgroundColor(Color.parseColor(selectedColor))
                }
                "Green" -> {
                    selectedColor = p1.getStringExtra("selectedColor")!!
                    createNoteBinding!!.colorView.setBackgroundColor(Color.parseColor(selectedColor))
                }
                "Gray" -> {
                    selectedColor = p1.getStringExtra("selectedColor")!!
                    createNoteBinding!!.colorView.setBackgroundColor(Color.parseColor(selectedColor))
                }
                "Image" ->{
                    readStorageTask()
                    createNoteBinding!!.layoutWebUrl.visibility = View.GONE
                }

                "WebUrl" ->{
                    createNoteBinding!!.layoutWebUrl.visibility = View.VISIBLE
                }
                "DeleteNote" -> {
                    //delete note
                    deleteNote()
                    Toast.makeText(requireContext(),"Note Deleted",Toast.LENGTH_SHORT).show()
                }


                else -> {
                    createNoteBinding!!.layoutImage.visibility = View.GONE
                    createNoteBinding!!.imgNote.visibility = View.GONE
                    createNoteBinding!!.layoutWebUrl.visibility = View.GONE
                    selectedColor = p1.getStringExtra("selectedColor")!!
                    createNoteBinding!!.colorView.setBackgroundColor(Color.parseColor(selectedColor))

                }
            }
        }

    }

    override fun onDestroy() {
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(BroadcastReceiver)
        super.onDestroy()


    }

    private fun hasReadStoragePerm():Boolean{
        return EasyPermissions.hasPermissions(requireContext(),Manifest.permission.READ_EXTERNAL_STORAGE)
    }


    private fun readStorageTask(){
        if (hasReadStoragePerm()){


            pickImageFromGallery()
        }else{
            EasyPermissions.requestPermissions(
                requireActivity(),
                getString(R.string.storage_permission_text),
                READ_STORAGE_PERM,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun pickImageFromGallery(){
        val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        if (intent.resolveActivity(requireActivity().packageManager) != null){
            startActivityForResult(intent,REQUEST_CODE_IMAGE)
        }
    }

    private fun getPathFromUri(contentUri: Uri): String? {
        var filePath:String? = null
        val cursor = requireActivity().contentResolver.query(contentUri,null,null,null,null)
        if (cursor == null){
            filePath = contentUri.path
        }else{
            cursor.moveToFirst()
            val index = cursor.getColumnIndex("_data")
            filePath = cursor.getString(index)
            cursor.close()
        }
        return filePath
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK && data != null) {
            val selectedImageUrl = data.data
            if (selectedImageUrl != null){
                try {
                    val inputStream = requireActivity().contentResolver.openInputStream(selectedImageUrl)
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    createNoteBinding?.imgNote?.setImageBitmap(bitmap)
                    createNoteBinding?.imgNote?.visibility = View.VISIBLE
                    createNoteBinding?.layoutImage?.visibility = View.VISIBLE

                    selectedImagePath = getPathFromUri(selectedImageUrl)!!
                }catch (e:Exception){
                    Toast.makeText(requireContext(),e.message,Toast.LENGTH_SHORT).show()
                }

            }
        }
    }




    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,requireActivity())
    }


    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(requireActivity(),perms)){
            AppSettingsDialog.Builder(requireActivity()).build().show()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onRationaleDenied(requestCode: Int) {

    }

    override fun onRationaleAccepted(requestCode: Int) {

    }

//class end tag
}