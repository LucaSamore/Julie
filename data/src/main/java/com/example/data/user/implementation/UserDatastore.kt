package com.example.data.user.implementation

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import arrow.core.Either
import arrow.core.flatMap
import com.example.data.Problem
import com.example.data.authentication.UnknownError
import com.example.data.report.DateOfRecordingProblem
import com.example.data.user.UserId
import com.example.data.user.UserIdProblem
import com.example.data.user.UserProblem
import com.example.data.util.prettyFormat
import java.time.LocalDateTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class UserDatastore(private val context: Context) {

    private val userId: Flow<String> =
        context.datastore.data.map { preferences -> preferences[USER_ID] ?: "" }

    private val dateTimeOfRecording: Flow<String> =
        context.datastore.data.map { preferences -> preferences[DATETIME_OF_RECORDING] ?: "" }

    suspend fun saveUserIdToDataStore(userId: String) {
        context.datastore.edit { preferences -> preferences[USER_ID] = userId }
    }

    suspend fun saveDateTimeOfRecordingToDataStore(dateTimeOfRecording: LocalDateTime) {
        context.datastore.edit { preferences ->
            preferences[DATETIME_OF_RECORDING] = dateTimeOfRecording.prettyFormat()
        }
    }

    suspend fun getUserId(): Either<UserProblem, UserId> {
        return Either.catch { userId.first() }
            .mapLeft { UserIdProblem(it.message ?: UnknownError) }
            .flatMap { UserId(it) }
    }

    suspend fun getDateTimeOfRecording(): Either<Problem, String> {
        return Either.catch { dateTimeOfRecording.first() }
            .mapLeft { DateOfRecordingProblem(it.message ?: UnknownError) }
    }

    companion object {
        private val Context.datastore: DataStore<Preferences> by
            preferencesDataStore(name = "users_preferences")

        private val USER_ID = stringPreferencesKey(name = "user_id")

        private val DATETIME_OF_RECORDING = stringPreferencesKey(name = "datetime_of_recording")
    }
}
