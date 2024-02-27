package bae.part2.a3dify
import android.app.Application
import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class LoginEncryptSharedPreference(context : Context) {
    private val masterKey : String = MasterKeys.getOrCreate(
        MasterKeys.AES256_GCM_SPEC
    )

    private val loginEncryptSharedPreference = EncryptedSharedPreferences.create(
        SECRET_USER_INFO,
        masterKey,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    
}
