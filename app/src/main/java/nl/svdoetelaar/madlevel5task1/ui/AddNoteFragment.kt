package nl.svdoetelaar.madlevel5task1.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import nl.svdoetelaar.madlevel5task1.R
import nl.svdoetelaar.madlevel5task1.databinding.FragmentAddNoteBinding
import nl.svdoetelaar.madlevel5task1.viewmodel.NoteViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AddNoteFragment : Fragment() {

    private val viewModel: NoteViewModel by viewModels()

    private lateinit var binding: FragmentAddNoteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeNote()

        binding.btnSave.setOnClickListener {
            saveNote()
        }
    }

    private fun observeNote() {
        viewModel.note.observe(viewLifecycleOwner, { note ->
            note?.let {
                binding.tilNoteTitle.editText?.setText(it.title)
                binding.tilNoteText.editText?.setText(it.text)
            }
        })

        viewModel.error.observe(viewLifecycleOwner, { message ->
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
        })

        viewModel.succes.observe(viewLifecycleOwner, {
            findNavController().popBackStack()
        })
    }

    private fun saveNote() {
        viewModel.updateNote(
            binding.tilNoteTitle.editText?.text.toString(),
            binding.tilNoteText.editText?.text.toString()
        )
    }
}