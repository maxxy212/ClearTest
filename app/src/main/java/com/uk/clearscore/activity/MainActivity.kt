package com.uk.clearscore.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.uk.clearscore.R
import com.uk.clearscore.databinding.ActivityMainBinding
import com.uk.clearscore.network.api.CreditCall
import com.uk.clearscore.utility.DialogUtil
import com.uk.clearscore.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var ui: DialogUtil
    @Inject
    lateinit var  creditCall: CreditCall

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        realm = Realm.getDefaultInstance()
        mainViewModel = ViewModelProvider(this,
            MainViewModel.MainViewFactory(creditCall, ui, realm))[MainViewModel::class.java]
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel
    }

    override fun onStart() {
        super.onStart()
        mainViewModel.getCreditScore()
    }
}