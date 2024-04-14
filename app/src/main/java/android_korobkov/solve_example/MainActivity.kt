package android_korobkov.solve_example

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android_korobkov.solve_example.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var summary: TextView
    private lateinit var primer: LinearLayout

    private lateinit var correct: TextView
    private lateinit var incorrect: TextView

    private lateinit var percent: TextView

    private lateinit var first: TextView
    private lateinit var second: TextView
    private lateinit var operation: TextView

    private lateinit var answer: TextView

    private lateinit var check: Button
    private lateinit var start: Button

    private var correctAns = 0
    private var incorrectAns = 0

    private val operations = listOf("+", "-", "*", "/")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        summary = binding.totalSolvedExamples
        primer = binding.primer

        correct = binding.countRight
        incorrect = binding.countError

        percent = binding.percentCorrectAnswers

        first = binding.firstNum
        second = binding.secondNumb
        operation = binding.operator

        answer = binding.inputResultExample

        check = binding.btnCheck
        check.setOnClickListener {
            checkAnswer()
        }

        start = binding.btnStart
        start.setOnClickListener {
            start()
        }

        answer.isEnabled = false
        check.isEnabled = false
    }

    @SuppressLint("SetTextI18n")
    private fun checkAnswer() {
        check.isEnabled = false
        start.isEnabled = true
        answer.isEnabled = false

        val ans = getAnswer().toInt().toString()

        if (answer.text.toString() == ans) {
            correctAns++
            primer.setBackgroundColor(Color.GREEN)
        } else {
            incorrectAns++
            primer.setBackgroundColor(Color.RED)
        }
        correct.text = correctAns.toString()
        incorrect.text = incorrectAns.toString()
        summary.text = (correctAns + incorrectAns).toString()
        percent.text =
            String.format("%.2f", (correctAns.toDouble() / (correctAns + incorrectAns) * 100)) + "%"
    }

    private fun start() {
        start.isEnabled = false
        check.isEnabled = true
        answer.isEnabled = true
        primer.setBackgroundColor(Color.TRANSPARENT)
        answer.text = ""

        makeNewExpr()
    }

    private fun makeNewExpr() {
        first.text = Random.nextInt(10, 100).toString()
        second.text = Random.nextInt(10, 100).toString()
        operation.text = operations[Random.nextInt(0, 4)]

        while (operation.text == "/" && first.text.toString().toDouble() % second.text.toString()
                .toDouble() != 0.0
        ) {
            first.text = Random.nextInt(10, 100).toString()
            second.text = Random.nextInt(10, 100).toString()
        }
    }

    private fun getAnswer(): Double {
        when (operation.text) {
            "+" -> {
                return first.text.toString().toDouble() + second.text.toString().toDouble()
            }

            "-" -> {
                return first.text.toString().toDouble() - second.text.toString().toDouble()
            }

            "*" -> {
                return first.text.toString().toDouble() * second.text.toString().toDouble()
            }

            "/" -> {
                return first.text.toString().toDouble() / second.text.toString().toDouble()
            }
        }
        return 0.0
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Сохраняем данные, которые нужно восстановить при повороте экрана
        outState.putInt("CORRECT_ANSWERS", correctAns)
        outState.putInt("INCORRECT_ANSWERS", incorrectAns)
        outState.putString("FIRST_NUM", first.text.toString())
        outState.putString("SECOND_NUM", second.text.toString())
        outState.putString("OPERATION", operation.text.toString())
        outState.putBoolean("ANSWER_ENABLED", answer.isEnabled)
        outState.putString("ANSWER_TEXT", answer.text.toString())
        outState.putBoolean("CHECK_ENABLED", check.isEnabled)
        outState.putBoolean("START_ENABLED", start.isEnabled)
        outState.putInt("TOTAL_SOLVED_EXAMPLES", summary.text.toString().toInt())
        outState.putInt("PRIMER_BACKGROUND_COLOR", (primer.background as ColorDrawable).color)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Восстанавливаем сохраненные данные
        correctAns = savedInstanceState.getInt("CORRECT_ANSWERS", 0)
        incorrectAns = savedInstanceState.getInt("INCORRECT_ANSWERS", 0)
        correct.text = correctAns.toString()
        incorrect.text = incorrectAns.toString()
        first.text = savedInstanceState.getString("FIRST_NUM", "00")
        second.text = savedInstanceState.getString("SECOND_NUM", "00")
        operation.text = savedInstanceState.getString("OPERATION", "+")
        answer.isEnabled = savedInstanceState.getBoolean("ANSWER_ENABLED", false)
        answer.text = savedInstanceState.getString("ANSWER_TEXT", "")
        check.isEnabled = savedInstanceState.getBoolean("CHECK_ENABLED", false)
        start.isEnabled = savedInstanceState.getBoolean("START_ENABLED", true)
        summary.text = savedInstanceState.getInt("TOTAL_SOLVED_EXAMPLES").toString()
        primer.setBackgroundColor(savedInstanceState.getInt("PRIMER_BACKGROUND_COLOR"))
    }

}