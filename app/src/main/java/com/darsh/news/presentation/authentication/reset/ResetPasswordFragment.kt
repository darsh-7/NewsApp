import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.darsh.news.databinding.FragmentResetPasswordBinding
import com.darsh.news.firebaseLogic.AuthViewModel
import kotlin.getValue

class ResetPasswordFragment : Fragment() {

    private lateinit var binding: FragmentResetPasswordBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResetPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.resetButton.setOnClickListener {
            val email = binding.emailInput.text.toString().trim()
            if (email.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter your email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Call the ViewModel to handle the password reset
            viewModel.resetPassword(email)
        }

        // Observe the result from the ViewModel
        viewModel.resetResult.observe(viewLifecycleOwner) { result ->
            result.onSuccess {
                Toast.makeText(requireContext(), "Password reset email sent. Check your inbox.", Toast.LENGTH_LONG).show()
                // Navigate back to the previous screen (e.g., SignInFragment)
                findNavController().popBackStack()
            }
            result.onFailure { exception ->
                Toast.makeText(requireContext(), "Failed: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
        }

//        // Handle the back button press
//        binding.backButton.setOnClickListener {
//            findNavController().popBackStack()
//        }
    }
}
