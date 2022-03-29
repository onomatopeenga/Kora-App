package com.example.kora.ui.notes

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.kora.*
import com.example.kora.ui.tasks.TasksHomeFragment
import com.example.kora.adapter.NotesAdapter
import com.example.kora.database.NotesDatabase
import com.example.kora.databinding.FragmentNotesHomeBinding
import com.example.kora.entities.Notes
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class NotesHomeFragment : BaseFragment(), BottomNavigationView.OnNavigationItemSelectedListener {

    private var notesHomeBinding: FragmentNotesHomeBinding? = null
    var arrNotes = ArrayList<Notes>()
    var notesAdapter: NotesAdapter = NotesAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notesHomeBinding = FragmentNotesHomeBinding.inflate(inflater, container, false)
        return notesHomeBinding?.root


    }

    companion object {
        @JvmStatic
        fun newInstance() =
            NotesHomeFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notesHomeBinding = FragmentNotesHomeBinding.bind(view)
        notesHomeBinding!!.bottomNavigationView.setOnNavigationItemSelectedListener(this)

        notesHomeBinding!!.recyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            notesHomeBinding!!.recyclerView.adapter = notesAdapter

        }

        launch {
            context?.let {
                var notes = NotesDatabase.getDatabase(it).noteDao().getAllNotes()
                notesAdapter.setData(notes)
                arrNotes = notes as ArrayList<Notes>
                notesHomeBinding!!.recyclerView.adapter = notesAdapter
            }
        }
        notesAdapter.setOnClickListener(onClicked)


        //main fab button
        notesHomeBinding!!.fabbutton.setOnClickListener {
         replaceFragment(CreateNoteFragment.newInstance(), false)
        }

        //switch to task button
        notesHomeBinding!!.taskHeaderBtn.setOnClickListener {
            val intent = Intent(requireActivity(),TasksActivity::class.java)
            startActivity(intent)

        }

        //create code for options menu when pressed

        notesHomeBinding!!.searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {

                val tempArr = ArrayList<Notes>()

                for (arr in arrNotes) {
                    if (arr.title!!.lowercase(Locale.getDefault()).contains(p0.toString())) {
                        tempArr.add(arr)
                    }
                }

                notesAdapter.setData(tempArr)
                notesAdapter.notifyDataSetChanged()
                return true
            }

        })
        //error on this part idk how to fix -pia



    }

    private val onClicked = object : NotesAdapter.OnItemClickListener {
        override fun onClicked(noteId: Int) {
            val fragment: Fragment
            val bundle = Bundle()
            bundle.putInt("noteId", noteId)
            fragment = CreateNoteFragment.newInstance()
            fragment.arguments = bundle
            replaceFragment(fragment, false)
        }

    }


    //replaces displayed fragment
    private fun replaceFragment(fragment: Fragment, istransition: Boolean) {
        val fragmentTransition = requireActivity().supportFragmentManager.beginTransaction()

        if (istransition) {
            fragmentTransition.setCustomAnimations(
                android.R.anim.slide_out_right,
                android.R.anim.slide_in_left
            )
        }
        fragmentTransition.replace(R.id.frame_layout, fragment)
            .addToBackStack(fragment.javaClass.simpleName).commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.profile -> {
                Toast.makeText(requireContext(), "Profile", Toast.LENGTH_SHORT).show()
                val intent = Intent(requireActivity(), ProfileActivity::class.java)
                startActivity(intent)

            }
            R.id.home -> {
                Toast.makeText(requireContext(), "Home", Toast.LENGTH_SHORT).show()
                val intent = Intent(requireActivity(), HomeActivity::class.java)
                startActivity(intent)


            }
        }
        return false
    }


}
