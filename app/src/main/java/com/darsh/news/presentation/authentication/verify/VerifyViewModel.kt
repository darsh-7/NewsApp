//
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.darsh.news.firebaseLogic.AuthRepository
//import kotlinx.coroutines.launch
//
//class ResetPasswordViewModel : ViewModel() {
//
//    private val authRepository = AuthRepository()
//
//    val resetResult: LiveData<Result<Unit>> = authRepository.resetResult
//
//    fun resetPassword(email: String) {
//        viewModelScope.launch {
//            authRepository.resetPassword(email)
//        }
//    }
//}
