package uz.texnopos.mybuilderapp.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.GsonBuilder
import com.google.firebase.storage.FirebaseStorage
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.texnopos.mybuilderapp.data.retrofit.CacheInterceptor
import uz.texnopos.mybuilderapp.core.Constants.BASE_URL
import uz.texnopos.mybuilderapp.core.Constants.appTimeOut
import uz.texnopos.mybuilderapp.core.isNetworkAvailable
import uz.texnopos.mybuilderapp.core.myCache
import uz.texnopos.mybuilderapp.data.firebase.AuthHelper
import uz.texnopos.mybuilderapp.data.firebase.FirebaseHelper
import uz.texnopos.mybuilderapp.data.repository.Repository
import uz.texnopos.mybuilderapp.data.retrofit.RestApis
import uz.texnopos.mybuilderapp.ui.login.LoginViewModel
import uz.texnopos.mybuilderapp.ui.login.verify.VerifyViewModel
import uz.texnopos.mybuilderapp.ui.main.builder.BuilderViewModel
import uz.texnopos.mybuilderapp.ui.main.builder.feedback.FeedbackViewModel
import uz.texnopos.mybuilderapp.ui.main.builder.feedback.post.PostViewModel
import uz.texnopos.mybuilderapp.ui.main.homeIn.HomeInViewModel
import uz.texnopos.mybuilderapp.ui.main.profile.ProfileViewModel
import uz.texnopos.mybuilderapp.ui.main.profile.resume.address.AddressViewModel
import uz.texnopos.mybuilderapp.ui.main.profile.resume.portfolio.PortfolioViewModel
import uz.texnopos.mybuilderapp.ui.main.profile.resume.professions.JobsViewModel
import uz.texnopos.mybuilderapp.ui.main.profile.resume.resumeMain.ResumeViewModel
import uz.texnopos.mybuilderapp.ui.main.trades.TradeViewModel
import uz.texnopos.mybuilderapp.ui.shortinfo.ShortInfoViewModel
import java.util.concurrent.TimeUnit

val viewModelModule = module {
    viewModel { ProfileViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { ShortInfoViewModel(get()) }
    viewModel { ResumeViewModel(get()) }
    viewModel { JobsViewModel(get()) }
    viewModel { AddressViewModel(get()) }
    viewModel { HomeInViewModel(get()) }
    viewModel { TradeViewModel(get()) }
    viewModel { VerifyViewModel(get()) }
    viewModel { BuilderViewModel(get()) }
    viewModel { FeedbackViewModel(get()) }
    viewModel { PostViewModel(get()) }
    viewModel { PortfolioViewModel(get()) }
    viewModel { uz.texnopos.mybuilderapp.ui.main.builder.profile.PortfolioViewModel(get()) }
}
val repositoryModule = module {
    single { Repository(get()) }
}


val networkModule = module {
    single {
        GsonBuilder().setLenient().create()
    }

    single {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        OkHttpClient.Builder()
            .cache(myCache)
            .addInterceptor { chain ->
                var request = chain.request()
                request = if (isNetworkAvailable())
                    request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
                else request.newBuilder().header(
                    "Cache-Control",
                    "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
                ).build()
                chain.proceed(request)
            }
            .addNetworkInterceptor(CacheInterceptor())
//            .addInterceptor(ForceCacheInterceptor())
            .addInterceptor(loggingInterceptor)
            .connectTimeout(appTimeOut, TimeUnit.SECONDS)
            .readTimeout(appTimeOut, TimeUnit.SECONDS)
            .writeTimeout(appTimeOut, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }
    single { get<Retrofit>().create(RestApis::class.java) }
}
val firebaseModule = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single { FirebaseStorage.getInstance() }
    single { FirebaseHelper(get(), get(),get()) }
    single { AuthHelper(get(), get()) }
}