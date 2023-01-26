package com.example.turtine

import android.os.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.turtine.databinding.ActivityTimerBinding
import java.util.*
import kotlin.concurrent.timer

class TimerActivity : AppCompatActivity() {
    private val binding by lazy { ActivityTimerBinding.inflate(layoutInflater) }
    private var _routine: Routine? = null
    private val routine get() = _routine!!

    private var time = 0
    private var timerTask: Timer? = null

    //    private var isRunning = false
    //    private var lap = 1
    private val uiHandler by lazy { Handler(Looper.getMainLooper()) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _routine = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("routine", Routine::class.java)
        } else {
            intent.getParcelableExtra("routine")
        }

        if (savedInstanceState != null) {
            _routine = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                savedInstanceState.getParcelable("routine", Routine::class.java)
            } else {
                savedInstanceState.getParcelable("routine")
            }
        }

        if (_routine == null) {
            finish()
            return
        }

        setContentView(binding.root)

        time = (routine.minute * 60 + routine.second) * 1000

        with(binding) {
            toolbar.setNavigationOnClickListener { finish() }

            titleTextView.text = routine.title
            timerTextView.text = "%02d:%02d".format(Locale.US, routine.minute, routine.second)
            progressIndicator.max = time
            progressIndicator.progress = 0
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        outState.putParcelable("routine", _routine)
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onResume() {
        super.onResume()

        if (timerTask == null) {
            start()
        }

    }

    override fun onDestroy() {
        timerTask?.cancel()
        super.onDestroy()
    }

    private fun start() {
        fun setTime(time: Int) {
            uiHandler.post {
                val minute = (time / 1000) / 60
                val second = (time / 1000) % 60
                val progress = ((routine.minute * 60 + routine.second) * 1000) - time

                with(binding) {
                    timerTextView.text = "%02d:%02d".format(Locale.US, minute, second)
                    progressIndicator.progress = progress
                }
            }
        }

        setTime(time)

        timerTask = timer(period = 10) {
            time -= 10
            if (time <= 0) {
                uiHandler.post {
                    binding.doneButton.isVisible = true
                }

                cancel()
                return@timer
            }

            setTime(time)
        }
    }

//    private fun reset() {
//        timerTask?.cancel()
//        time = 0
//        isRunning = false
//
//        binding.timerTextView.text = "%02d:%02d".format(Locale.US, 0, 0)
//
//        //labLayout.removeAllViews()
//        lap = 1
//    }
}




