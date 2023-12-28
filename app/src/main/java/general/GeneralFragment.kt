package general

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aswindev.remindersapp.databinding.FragmentGeneralBinding
import editvalue.EditValueFragment
import passwords.PasswordsFragment

class GeneralFragment : Fragment() {
    companion object {
        const val FILENAME = "passwords"
        const val BIN_STR = "Bin Day"
        const val INSURANCE_STR = "National Insurance No"
        const val ANNIVERSARY_STR = "Wedding Anniversary"
    }
    private lateinit var binding: FragmentGeneralBinding
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

    private fun launchPasswordEditScreen(title: String) {
        val dialog = EditValueFragment.newInstance(EditValueFragment.ScreenData(title, FILENAME))
        dialog.show(parentFragmentManager, "UpdateDialog")
    }

    override fun onResume() {
        super.onResume()
        displayValues()
    }

    private fun displayValues() {
        val valuePreferences = requireContext().getSharedPreferences(FILENAME, Context.MODE_PRIVATE)
        binding.textViewBinValue.text = valuePreferences.getString(BIN_STR, null)
        binding.textViewInsuranceValue.text = valuePreferences.getString(INSURANCE_STR, null)
        binding.textViewAnniversaryValue.text = valuePreferences.getString(ANNIVERSARY_STR, null)
    }
}