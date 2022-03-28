package com.example.kora.util

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.kora.R
import com.example.kora.databinding.FragmentNotesBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NotesBottomSheetFragment : BottomSheetDialogFragment() {
    private var notesBottomSheetBinding : FragmentNotesBottomSheetBinding? = null
    var selectedColor = "#F1A503"


    companion object {
        var noteId = -1
        fun newInstance(id: Int): NotesBottomSheetFragment {
            val args = Bundle()
            val fragment = NotesBottomSheetFragment()
            fragment.arguments = args
            noteId = id
            return fragment
        }
    }
    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)

        val view = LayoutInflater.from(context).inflate(R.layout.fragment_notes_bottom_sheet, null)
        dialog.setContentView(view)

        val param = (view.parent as View).layoutParams as CoordinatorLayout.LayoutParams

        val behavior: CoordinatorLayout.Behavior<View>? = param.behavior

        if (behavior is BottomSheetBehavior<*>) {
            behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    var state = ""
                    when(newState){
                        BottomSheetBehavior.STATE_DRAGGING ->{
                            state = "DRAGGING"
                        }
                        BottomSheetBehavior.STATE_SETTLING ->{
                            state = "SETTLING"
                        }
                        BottomSheetBehavior.STATE_EXPANDED ->{
                            state = "EXPANDED"
                        }
                        BottomSheetBehavior.STATE_COLLAPSED ->{
                            state = "COLLAPSED"
                        }
                        BottomSheetBehavior.STATE_HIDDEN ->{
                            state = "HIDDEN"
                            dismiss()
                            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
                        }
                        BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                            TODO()
                        }
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    TODO("Not yet implemented")
                }


            })


        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        notesBottomSheetBinding = FragmentNotesBottomSheetBinding.inflate(inflater, container, false)
        return notesBottomSheetBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (noteId != -1) {
        notesBottomSheetBinding?.layoutDeleteNote!!.visibility = View.VISIBLE
        } else {
            notesBottomSheetBinding?.layoutDeleteNote!!.visibility = View.GONE
        }
        setListener()
    }

    private fun setListener() {
        notesBottomSheetBinding?.fNote1?.setOnClickListener {
            notesBottomSheetBinding?.imgNote1?.setImageResource(R.drawable.ic_tick)
            notesBottomSheetBinding?.imgNote2?.setImageResource(0)
            notesBottomSheetBinding?.imgNote3?.setImageResource(0)
            notesBottomSheetBinding?.imgNote4?.setImageResource(0)
            notesBottomSheetBinding?.imgNote5?.setImageResource(0)
            notesBottomSheetBinding?.imgNote6?.setImageResource(0)
            selectedColor = "#ADD8E6"

            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action", "Blue")
            intent.putExtra("selectedColor", selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
            dismiss()

        }

        notesBottomSheetBinding?.fNote2?.setOnClickListener {
            notesBottomSheetBinding?.imgNote1?.setImageResource(0)
            notesBottomSheetBinding?.imgNote2?.setImageResource(R.drawable.ic_tick)
            notesBottomSheetBinding?.imgNote3?.setImageResource(0)
            notesBottomSheetBinding?.imgNote4?.setImageResource(0)
            notesBottomSheetBinding?.imgNote5?.setImageResource(0)
            notesBottomSheetBinding?.imgNote6?.setImageResource(0)
            selectedColor = "#FFD633"

            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action", "Yellow")
            intent.putExtra("selectedColor", selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)

        }

        notesBottomSheetBinding?.fNote3?.setOnClickListener {
            notesBottomSheetBinding?.imgNote1?.setImageResource(0)
            notesBottomSheetBinding?.imgNote2?.setImageResource(0)
            notesBottomSheetBinding?.imgNote3?.setImageResource(R.drawable.ic_tick)
            notesBottomSheetBinding?.imgNote4?.setImageResource(0)
            notesBottomSheetBinding?.imgNote5?.setImageResource(0)
            notesBottomSheetBinding?.imgNote6?.setImageResource(0)
            selectedColor = "#FE7062"

            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action", "Red")
            intent.putExtra("selectedColor", selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)

        }

        notesBottomSheetBinding?.fNote4?.setOnClickListener {
            notesBottomSheetBinding?.imgNote1?.setImageResource(0)
            notesBottomSheetBinding?.imgNote2?.setImageResource(0)
            notesBottomSheetBinding?.imgNote3?.setImageResource(0)
            notesBottomSheetBinding?.imgNote4?.setImageResource(R.drawable.ic_tick)
            notesBottomSheetBinding?.imgNote5?.setImageResource(0)
            notesBottomSheetBinding?.imgNote6?.setImageResource(0)
            selectedColor = "#D0A2D5"

            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action", "Purple")
            intent.putExtra("selectedColor", selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }
        notesBottomSheetBinding?.fNote5?.setOnClickListener {
            notesBottomSheetBinding?.imgNote1?.setImageResource(0)
            notesBottomSheetBinding?.imgNote2?.setImageResource(0)
            notesBottomSheetBinding?.imgNote3?.setImageResource(0)
            notesBottomSheetBinding?.imgNote4?.setImageResource(0)
            notesBottomSheetBinding?.imgNote5?.setImageResource(R.drawable.ic_tick)
            notesBottomSheetBinding?.imgNote6?.setImageResource(0)
            selectedColor = "#FFD5FCB9"

            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action", "Green")
            val putExtra = intent.putExtra("selectedColor", selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }
        notesBottomSheetBinding?.fNote6?.setOnClickListener {
            notesBottomSheetBinding?.imgNote1?.setImageResource(0)
            notesBottomSheetBinding?.imgNote2?.setImageResource(0)
            notesBottomSheetBinding?.imgNote3?.setImageResource(0)
            notesBottomSheetBinding?.imgNote4?.setImageResource(0)
            notesBottomSheetBinding?.imgNote5?.setImageResource(0)
            notesBottomSheetBinding?.imgNote5?.setImageResource(0)
            notesBottomSheetBinding?.imgNote6?.setImageResource(R.drawable.ic_tick)
            selectedColor = "#C4CFCE"

            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action", "Gray")
            intent.putExtra("selectedColor", selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }


        notesBottomSheetBinding?.layoutImage?.setOnClickListener {
            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action", "Image")
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)

        }
        notesBottomSheetBinding?.layoutWebUrl?.setOnClickListener {
            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action", "WebUrl")
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)

        }
        notesBottomSheetBinding?.layoutDeleteNote?.setOnClickListener {
            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action", "DeleteNote")
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)

        }


    }

}
