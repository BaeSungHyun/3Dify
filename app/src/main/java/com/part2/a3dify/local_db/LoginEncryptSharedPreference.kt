package com.part2.a3dify.local_db
import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class LoginEncryptSharedPreference(context : Context) {
    // MasterKey in the AndroidX Security library.
    // Same key is used if already exists for configuration.
    private val masterKey : String = MasterKeys.getOrCreate(
        MasterKeys.AES256_GCM_SPEC // Android Keystore. Encrypt files.
    )

    private val loginEncryptSharedPreference = EncryptedSharedPreferences.create(
        SECRET_USER_INFO,
        masterKey,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    
}
