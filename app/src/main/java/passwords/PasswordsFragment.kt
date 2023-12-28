package passwords

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aswindev.remindersapp.databinding.FragmentPasswordsBinding
import editvalue.EditValueFragment

class PasswordsFragment : Fragment() {
    companion object {
        const val FILENAME = "passwords"
        const val WIFI_STR = "Wifi Password"
        const val BIKE_STR = "Bike Lock"
        const val TABLET_STR = "Tablet PIN"
    }
    private lateinit var binding: FragmentPasswordsBinding

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
        binding.textViewWifiValue.text = valuePreferences.getString(WIFI_STR, null)
        binding.textViewBikeValue.text = valuePreferences.getString(BIKE_STR, null)
        binding.textViewTabletValue.text = valuePreferences.getString(TABLET_STR, null)
    }
}