package com.example.data.user.implementation

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import arrow.core.Either
import com.example.data.authentication.UnknownError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

internal class UserDatastore(private val context: Context) {

    private val userId: Flow<String> =
        context.datastore.data.map { preferences -> preferences[USER_ID] ?: "" }

    suspend fun saveUserIdToDataStore(userId: String) {
        context.datastore.edit { preferences -> preferences[USER_ID] = userId }
    }

    suspend fun getUserId(): Either<String, String> =
        Either.catch { userId.first() }.mapLeft { it.message ?: UnknownError }

    companion object {
        private val Context.datastore: DataStore<Preferences> by
            preferencesDataStore(name = "users_preferences")

        private val USER_ID = stringPreferencesKey(name = "user_id")
    }
}
