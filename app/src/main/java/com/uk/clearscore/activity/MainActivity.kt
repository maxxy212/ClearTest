package com.uk.clearscore.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.uk.clearscore.R
import com.uk.clearscore.databinding.ActivityMainBinding
import com.uk.clearscore.model.Report
import com.uk.clearscore.network.ApiCallHandler
import com.uk.clearscore.network.api.CreditCall
import com.uk.clearscore.utility.DialogUtil
import com.uk.clearscore.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm
import io.realm.kotlin.where
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var ui: DialogUtil
    @Inject
    lateinit var  creditCall: CreditCall
    private val TAG =  MainActivity::class.java.simpleName

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        // Added this here just incase user has offline data while fetching continues on start
        binding.report = mainViewModel.report
        binding.viewWrapper.setOnClickListener{ DetailActivity.start(this) }
    }

    override fun onStart() {
        super.onStart()
        mainViewModel.getCreditScore(ui, creditCall, binding)
    }
}