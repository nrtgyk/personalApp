package com.example.personalapp


import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.personalapp.databinding.MainActivityBinding



class MainActivity : ComponentActivity() {
    private lateinit var binding: MainActivityBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }

}

