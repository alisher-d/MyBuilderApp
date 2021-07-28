package uz.texnopos.mybuilderapp.core

object Constants {
    const val myPreferences="MyPreferences"
    const val TAG = "GoogleActivity"
    const val RC_SIGN_IN = 9001
    const val PERMISSION_ID=1010
    const val BASE_URL="https://us-central1-my-builder-app.cloudfunctions.net/api/"
    const val USER_EXISTS="user_exists"
    object SharedPref{
        const val IS_LOGGED_IN = "isLoggedIn"
        const val HAS_USERNAME="hasUsername"
        const val USER_DISPLAY_NAME = "user_display_name"
        const val USER_EMAIL = "user_email"
        const val USER_FULL_NAME = "user_first_name"
        const val USER_LAST_NAME = "user_last_name"
        const val USER_PHONE_NUMBER="user_phone_number"
        const val CREATED="created"
        const val PUBLISH="publish"
        const val PROFESSION="profession"
        const val DESCRIPTION="description"
        const val ADDRESS="address"
        const val SELECTABLE_JOBS="selectable_jobs"
    }

}
