package com.example.turtine

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.turtine.databinding.ActivityRoutineBinding

class RoutineActivity : AppCompatActivity() {
    private val binding by lazy { ActivityRoutineBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.button.setOnClickListener { addRoutine() }
    }

    private fun addRoutine() = with(binding) {
        val title = titleEditText.text.toString().trim()
        val minute = minuteEditText.text.toString().toIntOrNull() ?: 0
        val second = secondEditText.text.toString().toIntOrNull() ?: 5

        val routine = Routine(title, minute, second)

        val intent = Intent(this@RoutineActivity, TimerActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            putExtra("routine", routine)
        }

        startActivity(intent)
    }
}