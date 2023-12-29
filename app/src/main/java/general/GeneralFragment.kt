package general

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.aswindev.remindersapp.databinding.DialogUpdateBinding
import com.aswindev.remindersapp.databinding.FragmentGeneralBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class GeneralFragment : Fragment() {
    companion object {
        const val FILENAME = "general"
        const val BIN_STR = "Bin Day"
        const val INSURANCE_STR = "National Insurance No"
        const val ANNIVERSARY_STR = "Wedding Anniversary"
    }

    private lateinit var binding: FragmentGeneralBinding
    private val preferences: SharedPreferences by lazy {
        requireActivity().getSharedPreferences(
            FILENAME,
            Context.MODE_PRIVATE
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentGeneralBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.containerBin.setOnClickListener { launchPasswordEditScreen(BIN_STR) }
        binding.containerInsurance.setOnClickListener { launchPasswordEditScreen(INSURANCE_STR) }
        binding.containerAnniversary.setOnClickListener { launchPasswordEditScreen(ANNIVERSARY_STR) }
    }

    private fun launchPasswordEditScreen(record: String) {
        val dialogBinding = DialogUpdateBinding.inflate(requireActivity().layoutInflater)
        val title = "Update $record"
        dialogBinding.editText.setText(preferences.getString(record,null))

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(title)
            .setView(dialogBinding.root)
            .setPositiveButton("Save") { _, _ ->
                preferences.edit {
                    putString(
                        record,
                        dialogBinding.editText.text.toString()
                    )
                }
                displayValues()
            }
            .setNegativeButton("Cancel") { _, _ -> }
            .show()
    }

    override fun onResume() {
        super.onResume()
        displayValues()
    }

    private fun displayValues() {
        binding.textViewBinValue.text = preferences.getString(BIN_STR, null)
        binding.textViewInsuranceValue.text = preferences.getString(INSURANCE_STR, null)
        binding.textViewAnniversaryValue.text = preferences.getString(ANNIVERSARY_STR, null)
    }
}