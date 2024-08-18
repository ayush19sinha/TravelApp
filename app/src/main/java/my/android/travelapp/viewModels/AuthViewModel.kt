package my.android.travelapp.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest

class AuthViewModel: ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    private val _userData = MutableLiveData<UserData?>()
    val userData: LiveData<UserData?> = _userData


    init {
        checkAuthStatus()
        fetchUserInfo()
    }


    fun checkAuthStatus() {
        if (auth.currentUser == null) {
            _authState.value = AuthState.Unauthenticated
        } else {
            _authState.value = AuthState.Authenticated

        }
    }

    fun login(email: String, password: String) {
        if (email.isEmpty() && password.isEmpty()) {
            _authState.value = AuthState.Error("Email and password cannot be empty")
            return

        }

        _authState.value = AuthState.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Authenticated
                    fetchUserInfo()
                } else {
                    _authState.value =
                        AuthState.Error(task.exception?.message ?: "Something went wrong")
                }
            }

    }

    fun signUp(email: String, password: String, name: String) {
        if (email.isEmpty() || password.isEmpty() || name.isEmpty()) {
            _authState.value = AuthState.Error("Email, password and name cannot be empty")
            return
        }


        _authState.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Authenticated
                    val user = auth.currentUser
                    val profileUpdates = userProfileChangeRequest {
                        displayName = name
                    }
                    user?.updateProfile(profileUpdates)
                        ?.addOnCompleteListener { profileUpdateTask ->
                            if (profileUpdateTask.isSuccessful) {
                                _authState.value = AuthState.Authenticated
                                fetchUserInfo()
                            } else {
                                _authState.value =
                                    AuthState.Error(profileUpdateTask.exception?.message ?: "Something went wrong")
                            }
                }
                } else {
                    _authState.value =
                        AuthState.Error(task.exception?.message ?: "Something went wrong")
                }
            }
    }

    fun emailVerification(){
        val user = Firebase.auth.currentUser

        user!!.sendEmailVerification()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("EmailVerification", "OTP is sent to your email")
                }
            }
    }

    fun updateUserInfo(email: String, password: String, name: String){
        val user = auth.currentUser
        val profileUpdates = userProfileChangeRequest {
            displayName = name
        }
        user!!.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("UpdateProfile", "User profile updated.")
                }
            }
    }

    fun logout() {
        auth.signOut()
        _authState.value = AuthState.Unauthenticated
        _userData.value = null
    }

    fun deleteAccount(email: String, password: String){
        val user = Firebase.auth.currentUser!!

        val credential = EmailAuthProvider.getCredential(email, password)
        user.reauthenticate(credential)
            .addOnCompleteListener { reauthTask ->
                if (reauthTask.isSuccessful) {

                    user.delete()
                        .addOnCompleteListener { deleteTask ->
                            if (deleteTask.isSuccessful) {
                                _authState.value = AuthState.Unauthenticated
                                Log.d("DeleteAccount", "Account deleted successfully.")
                            } else {
                                Log.w("DeleteAccount", "Account deletion failed: ${deleteTask.exception?.message}")
                            }
                        }
                } else {
                    Log.w("DeleteAccount", "Re-authentication failed: ${reauthTask.exception?.message}")
                }
            }
    }

    private fun fetchUserInfo(){
        val user = Firebase.auth.currentUser
        user?.let {
            val name = it.displayName
            val email = it.email
            _userData.value = UserData(name, email)

        }
    }
}

sealed class AuthState{
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    object Loading : AuthState()
    data class Error (val message : String) : AuthState()
}

data class UserData(
    val name: String?,
    val email: String?,
)