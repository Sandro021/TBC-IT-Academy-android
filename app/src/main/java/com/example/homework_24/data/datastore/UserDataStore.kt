package com.example.homework_24.data.datastore

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDataStore @Inject constructor(
    private val dataStore: DataStore<UserPrefs>
) {

    val usersFlow: Flow<List<UserInfo>> = dataStore.data.map { prefs ->
        prefs.usersList.map { protoUser ->
            UserInfo(
                id = protoUser.id,
                firstName = protoUser.firstName,
                lastName = protoUser.lastName,
                email = protoUser.email
            )
        }
    }

    suspend fun addNewUser(user: UserInfo) {
        dataStore.updateData { prefs ->
            val newUser = User.newBuilder().setId(user.id).setFirstName(user.firstName)
                .setLastName(user.lastName).setEmail(user.email).build()

            prefs.toBuilder().addUsers(newUser).build()
        }
    }

    suspend fun deleteUser(id: Int) {
        dataStore.updateData { prefs ->
            val filtered = prefs.usersList.filter { it.id != id }
            prefs.toBuilder().clearUsers().addAllUsers(filtered).build()
        }
    }

    suspend fun clearAll() {
        dataStore.updateData { prefs -> prefs.toBuilder().clearUsers().build() }
    }
}