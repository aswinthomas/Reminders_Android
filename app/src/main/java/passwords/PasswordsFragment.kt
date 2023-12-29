package passwords

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.aswindev.remindersapp.databinding.DialogUpdateBinding
import com.aswindev.remindersapp.databinding.FragmentPasswordsBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class PasswordsFragment : Fragment() {
    companion object {
        const val FILENAME = "passwords"
        const val WIFI_STR = "Wifi Password"
        const val BIKE_STR = "Bike Lock"
        const val TABLET_STR = "Tablet PIN"
    }

    private lateinit var binding: FragmentPasswordsBinding
    private val preferences: SharedPreferences by lazy {
        requireActivity().getSharedPreferences(FILENAME, Context.MODE_PRIVATE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPasswordsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.containerWifi.setOnClickListener { launchPasswordEditScreen(WIFI_STR) }
        binding.containerBike.setOnClickListener { launchPasswordEditScreen(BIKE_STR) }
        binding.containerTablet.setOnClickListener { launchPasswordEditScreen(TABLET_STR) }
    }

    private fun launchPasswordEditScreen(record: String) {
        val title = "Update $record"
        val dialogBinding = DialogUpdateBinding.inflate(requireActivity().layoutInflater)
        dialogBinding.editText.setText(preferences.getString(record, null))
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
        binding.textViewWifiValue.text = preferences.getString(WIFI_STR, null)
        binding.textViewBikeValue.text = preferences.getString(BIKE_STR, null)
        binding.textViewTabletValue.text = preferences.getString(TABLET_STR, null)
    }
}