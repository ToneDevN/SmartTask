package com.example.myfirstapp

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager(context: Context) {
    private val preferences: SharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)

    var isLoggedIn: Boolean
        get() = preferences.getBoolean(KEY_IS_LOGGED_IN, false)
        set(value) = preferences.edit().putBoolean(KEY_IS_LOGGED_IN, value).apply()

    var token: String?
        get() = preferences.getString(KEY_TOKEN, null)
        set(value) = preferences.edit().putString(KEY_TOKEN, value).apply()

    var userEmail: String?
        get() = preferences.getString(USER_EMAIL, null)
        set(value) = preferences.edit().putString(USER_EMAIL, value).apply()

    fun clearUserAll() {
        preferences.edit().remove(KEY_TOKEN).remove(KEY_IS_LOGGED_IN).apply()
    }

    fun clearUserLogin() {
        preferences.edit().remove(KEY_IS_LOGGED_IN).apply()
    }

    companion object {
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_TOKEN = "token"
        private const val USER_EMAIL = "user_email"
    }
}