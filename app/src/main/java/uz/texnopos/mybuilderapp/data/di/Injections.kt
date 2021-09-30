package uz.texnopos.mybuilderapp.data.di

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
import uz.texnopos.mybuilderapp.data.RestApis
import uz.texnopos.mybuilderapp.data.repository.Repository
import uz.texnopos.mybuilderapp.ui.login.LoginViewModel
import uz.texnopos.mybuilderapp.ui.login.verify.VerifyViewModel
import uz.texnopos.mybuilderapp.ui.main.builder.BuilderViewModel
import uz.texnopos.mybuilderapp.ui.main.builder.feedback.FeedbackViewModel
import uz.texnopos.mybuilderapp.ui.main.builder.feedback.post.PostViewModel
import uz.texnopos.mybuilderapp.ui.main.home.HomeViewModel
import uz.texnopos.mybuilderapp.ui.main.trades.TradeViewModel
import uz.texnopos.mybuilderapp.ui.main.profile.ProfileViewModel
import uz.texnopos.mybuilderapp.ui.main.profile.resume.address.AddressViewModel
import uz.texnopos.mybuilderapp.ui.main.profile.resume.homeMain.ResumeViewModel
import uz.texnopos.mybuilderapp.ui.main.profile.resume.professions.JobsViewModel
import uz.texnopos.mybuilderapp.ui.shortinfo.ShortInfoViewModel

val viewModelModule = module {
    viewModel { ProfileViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { ShortInfoViewModel(get()) }
    viewModel { ResumeViewModel(get()) }
    viewModel { JobsViewModel(get()) }
    viewModel { AddressViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { TradeViewModel(get()) }
    viewModel { VerifyViewModel(get()) }
    viewModel { BuilderViewModel(get()) }
    viewModel { FeedbackViewModel(get()) }
    viewModel { PostViewModel(get()) }
}
val repositoryModule = module {
    single { Repository(get()) }
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