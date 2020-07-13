package com.example.hotmarttest.bases

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.ViewDataBinding
import com.example.hotmarttest.R

class BaseActivity<T : ViewDataBinding, V : BaseViewModel> : AppCompatActivity() {

    var toolbar: Toolbar? = null
    protected lateinit var binding: T
    protected lateinit var viewModel: V

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun setUpToolbar(title: String?) {
        toolbar = findViewById(R.id.toolbar)
        toolbar?.let {
            setSupportActionBar(it)
            supportActionBar?.setDisplayShowTitleEnabled(false)
            val textView = it.findViewById<TextView>(R.id.tvTitle)
            textView.text = title
        }
    }
}