package uz.texnopos.mybuilderapp.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.GsonBuilder
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.texnopos.mybuilderapp.core.Constants.BASE_URL
import uz.texnopos.mybuilderapp.data.AuthHelper
import uz.texnopos.mybuilderapp.data.FirebaseHelper
import uz.texnopos.mybuilderapp.network.RestApis
import uz.texnopos.mybuilderapp.repository.UserRepository
import uz.texnopos.mybuilderapp.ui.login.LoginViewModel
import uz.texnopos.mybuilderapp.ui.profile.ProfileViewModel
import uz.texnopos.mybuilderapp.ui.resume.homeMain.ResumeViewModel
import uz.texnopos.mybuilderapp.ui.resume.personalInfo.PersonalInfoViewModel
import uz.texnopos.mybuilderapp.ui.resume.professions.JobsViewModel
import uz.texnopos.mybuilderapp.ui.shortinfo.ShortInfoViewModel

val viewModelModule = module {
    viewModel { ProfileViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { ShortInfoViewModel(get()) }
    viewModel { ResumeViewModel(get()) }
    viewModel { JobsViewModel(get()) }
    viewModel { PersonalInfoViewModel(get()) }
}
val repositoryModule = module {
    single { UserRepository(get()) }
}

val apiModule = module {
    fun provideUseApi(retrofit: Retrofit): RestApis {
        return retrofit.create(RestApis::class.java)
    }
    single { provideUseApi(get()) }
}

val retrofitModule = module {
    val gson = GsonBuilder()
        .setLenient()
        .create()

    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
    single { provideRetrofit() }
}
val firebaseModule = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single { FirebaseHelper(get(), get()) }
    single { AuthHelper(get(), get()) }
}