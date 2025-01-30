package com.bae.sso.util

import android.app.Activity
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CreatePasswordRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialRequest.Builder
import androidx.credentials.GetPasswordOption
import androidx.credentials.PasswordCredential
import androidx.credentials.exceptions.CreateCredentialCancellationException
import androidx.credentials.exceptions.CreateCredentialException
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential

class AccountHelper(
    private val activity: Activity
) {
    private val credentialManager = CredentialManager.create(activity)

    suspend fun signUpWithSavePassword(
        email: String,
        password: String
    ): SignUpResult {
        return try {
            credentialManager.createCredential(
                context = activity,
                request = CreatePasswordRequest(
                    id = email,
                    password = password
                )
            )
            SignUpResult.Success(email)
        } catch (e: CreateCredentialCancellationException) {
            e.printStackTrace()
            SignUpResult.Cancelled
        } catch (e: CreateCredentialException) {
            e.printStackTrace()
            SignUpResult.Failure
        }
    }

    // Dialog
    suspend fun googleSingleSignOn(): GoogleSingleSignOnResult {
        return try {
            // Web Client ID
            val googleIdOption = GetSignInWithGoogleOption.Builder(
                serverClientId = "542748531980-b0mj0mgahs60mdjp474cskb0nqp9nei1.apps.googleusercontent.com"
            ).build()

            val response = credentialManager.getCredential(
                context = activity,
                request = Builder()
                    .addCredentialOption(googleIdOption)
                    .build()
            )

            if (response.credential is CustomCredential &&
                response.credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                val googleData = GoogleIdTokenCredential.createFrom(response.credential.data)
                GoogleSingleSignOnResult.Success(
                    email = googleData.id,
                    token = googleData.idToken,
                    givenName = googleData.givenName,
                    familyName = googleData.familyName,
                    userImage = googleData.profilePictureUri?.toString(),
                    phoneNumber = googleData.phoneNumber
                )
            } else {
                GoogleSingleSignOnResult.Failure
            }

        } catch (e: GetCredentialException) {
            e.printStackTrace()
            GoogleSingleSignOnResult.Failure
        }
        catch (e: Exception) {
            GoogleSingleSignOnResult.Failure
        }
    }

    /*
    BottomSheet（One Tap Sign On）
    * setFilterByAuthorizedAccounts
	    - true: Show only accounts that are already approved
	    - false: Displays all Google accounts logged into your device

    * setAutoSelectEnabled
        - true: If there is only one account available, automatically select that account without the Select Account screen.
        - false: Always display the account selection screen.

    * setNonce(資料参考）
    */
    suspend fun googleOneTapSignOn(): GoogleSingleSignOnResult {
        return try {
            // Web Client ID
            val googleIdOption = GetGoogleIdOption.Builder()
                .setServerClientId("542748531980-b0mj0mgahs60mdjp474cskb0nqp9nei1.apps.googleusercontent.com")
                .setFilterByAuthorizedAccounts(false)
                .setAutoSelectEnabled(true)
                .setNonce("")
                .build()

            val response = credentialManager.getCredential(
                context = activity,
                request = Builder()
                    .addCredentialOption(googleIdOption)
                    .build()
            )

            if (response.credential is CustomCredential &&
                response.credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                val googleData = GoogleIdTokenCredential.createFrom(response.credential.data)
                GoogleSingleSignOnResult.Success(
                    email = googleData.id,
                    token = googleData.idToken,
                    givenName = googleData.givenName,
                    familyName = googleData.familyName,
                    userImage = googleData.profilePictureUri?.toString(),
                    phoneNumber = googleData.phoneNumber
                )
            } else {
                GoogleSingleSignOnResult.Failure
            }

        } catch (e: GetCredentialException) {
            e.printStackTrace()
            GoogleSingleSignOnResult.Failure
        }
        catch (e: Exception) {
            GoogleSingleSignOnResult.Failure
        }
    }

    suspend fun getSavedPassword(): SavedPasswordResult {
        return try {
            val request = GetCredentialRequest(
                listOf(GetPasswordOption())
            )
            val credential = credentialManager.getCredential(
                context = activity,
                request = request
            )
            val result = credential.credential as? PasswordCredential
                ?: return SavedPasswordResult.Failure

            val email = result.id
            val password = result.password
            SavedPasswordResult.Success(email, password)
        } catch (e: GetCredentialCancellationException) {
            e.printStackTrace()
            SavedPasswordResult.Cancelled
        } catch (e: GetCredentialException) {
            e.printStackTrace()
            SavedPasswordResult.Failure
        }
    }

    suspend fun clearCredentialData() {
        credentialManager.clearCredentialState(
            request = ClearCredentialStateRequest()
        )
    }
}

sealed interface SignUpResult {
    data class Success(val email: String) : SignUpResult
    data object Cancelled : SignUpResult
    data object Failure : SignUpResult
}

sealed interface GoogleSingleSignOnResult {
    data class Success(
        val email: String,
        val token: String,
        val givenName: String? = null,
        val familyName: String? = null,
        val userImage: String? = null,
        val phoneNumber: String? = null
    ) : GoogleSingleSignOnResult
    data object Failure : GoogleSingleSignOnResult
}

sealed class SavedPasswordResult {
    data class Success(val email: String, val password: String) : SavedPasswordResult()
    data object Cancelled : SavedPasswordResult()
    data object Failure : SavedPasswordResult()
}