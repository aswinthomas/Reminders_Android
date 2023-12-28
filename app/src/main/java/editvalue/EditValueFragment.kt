package editvalue

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.edit
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.aswindev.remindersapp.databinding.DialogUpdateBinding
import passwords.PasswordsFragment
import java.io.Serializable

const val INTENT_EXTRA_SCREEN_DATA = "screen_data"


fun <T : Serializable> Bundle.getSerializableCompat(key: String, clazz: Class<T>): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        this.getSerializable(key, clazz)
    } else {
        this.getSerializable(key) as T?
    }
}

class EditValueFragment : DialogFragment() {
    companion object {
        fun newInstance(screenData: ScreenData): EditValueFragment {
            val args = Bundle().apply {
                putSerializable(INTENT_EXTRA_SCREEN_DATA, screenData)
            }
            return EditValueFragment().apply {
                arguments = args
            }
        }
    }
    private lateinit var binding: DialogUpdateBinding
    private val screenData: ScreenData by lazy {
        arguments?.getSerializableCompat(INTENT_EXTRA_SCREEN_DATA, ScreenData::class.java) as ScreenData
    }
    private val valuePreferences: SharedPreferences? by lazy {
        activity?.getSharedPreferences(screenData.sharedPreferencesName, Context.MODE_PRIVATE)
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Inflate the layout for the dialog
        binding = DialogUpdateBinding.inflate(layoutInflater)

        // Use the Builder class for convenient dialog construction
        val builder = AlertDialog.Builder(requireActivity()).apply {
            setView(binding.root) // Set the custom layout
            setPositiveButton("Save") { dialog, id ->
                val newValue = binding.editText.text.toString()
                saveValue(newValue)
            }
            setNegativeButton("Cancel") { dialog, id ->
                dialog.cancel()
            }
        }
        val data = valuePreferences?.getString(screenData.title, null)
        binding.editText.setText(data)
        val fullTitle = "Update ${screenData.title}"
        binding.textViewTitle.text = fullTitle
        // Create the AlertDialog object and return it
        return builder.create()
    }

    private fun saveValue(newValue: String) {
        valuePreferences?.edit {
            putString(screenData.title, newValue)
        }
    }


    data class ScreenData(val title: String, val sharedPreferencesName: String) : Serializable
}