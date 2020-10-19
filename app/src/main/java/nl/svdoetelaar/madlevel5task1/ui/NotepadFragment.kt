package nl.svdoetelaar.madlevel5task1.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import nl.svdoetelaar.madlevel5task1.R
import nl.svdoetelaar.madlevel5task1.databinding.FragmentNotepadBinding
import nl.svdoetelaar.madlevel5task1.viewmodel.NoteViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class NotepadFragment : Fragment() {

    private val viewModel: NoteViewModel by viewModels()

    private lateinit var binding: FragmentNotepadBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotepadBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeAddNoteResult()
    }

    private fun observeAddNoteResult() {
        viewModel.note.observe(viewLifecycleOwner, { note ->
            note?.let {
                binding.tvNoteTitle.text = it.title
                binding.tvLastUpdated.text =
                    getString(R.string.last_updated, it.lastUpdate.toString())
                binding.tvNoteText.text = it.text
            }
        })
    }
}