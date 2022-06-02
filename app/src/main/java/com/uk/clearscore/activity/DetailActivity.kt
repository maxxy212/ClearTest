package com.uk.clearscore.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.uk.clearscore.R
import com.uk.clearscore.databinding.ActivityDetailBinding
import com.uk.clearscore.network.api.CreditCall
import com.uk.clearscore.utility.DialogUtil
import com.uk.clearscore.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm
import javax.inject.Inject

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    @Inject
    lateinit var ui: DialogUtil
    @Inject
    lateinit var  creditCall: CreditCall

    private lateinit var binding: ActivityDetailBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        realm = Realm.getDefaultInstance()
        mainViewModel = ViewModelProvider(this,
            MainViewModel.MainViewFactory(creditCall, ui, realm))[MainViewModel::class.java]
        binding.report = mainViewModel.getReport()
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, DetailActivity::class.java))
        }
    }
}