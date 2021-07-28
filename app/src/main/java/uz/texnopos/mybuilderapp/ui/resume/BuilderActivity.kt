package uz.texnopos.mybuilderapp.ui.resume

import android.os.Bundle
import uz.texnopos.mybuilderapp.base.AppBaseActivity
import uz.texnopos.mybuilderapp.databinding.ActivityCreateRezyumeBinding

class BuilderActivity : AppBaseActivity() {
    lateinit var binding: ActivityCreateRezyumeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateRezyumeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


}