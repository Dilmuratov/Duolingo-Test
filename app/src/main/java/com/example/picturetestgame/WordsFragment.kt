package com.example.picturetestgame

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.picturetestgame.databinding.FragmentWordsBinding
import com.example.picturetestgame.models.WordData
import com.example.picturetestgame.models.WordTestData
import kotlin.random.Random

class WordsFragment : Fragment(R.layout.fragment_words) {
    private lateinit var binding: FragmentWordsBinding
    private lateinit var variantsAdapter: WordAdapter
    private lateinit var answersAdapter: WordAdapter
    private var currentIndex = 0
    private lateinit var testList: List<WordTestData>
    private var clickCount = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWordsBinding.bind(view)
        checkButtonState()

        initVariables()

        initListeners()

        setData()

    }

    private fun setData() {
        val testData = testList[currentIndex]
        binding.tvQuestion.text = testData.question
        val wordDataList = mutableListOf<WordData>()
        testData.answers.forEach {
            wordDataList.add(WordData(Random.nextInt(0, 25), it))
        }
        variantsAdapter.submitList(wordDataList.shuffled())

        binding.btnCheck.setBackgroundResource(R.drawable.bg_btn_tint)
        binding.btnCheck.text = "Check"
        binding.llResult.gone()
        binding.ivCorrect.gone()
        binding.ivWrong.gone()
        binding.llCa.gone()
        binding.view.gone()
        binding.view.isClickable = false

        val all = testList.size
        val weight = ((currentIndex + 1) * 100).toFloat() / all

        val layoutParams = LinearLayoutCompat.LayoutParams(
            LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
            LinearLayoutCompat.LayoutParams.MATCH_PARENT
        )

        layoutParams.weight = weight

        binding.current.layoutParams = layoutParams
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initListeners() {
        variantsAdapter.setOnItemClickListeners {
            variantsAdapter.removeItem(it)
            answersAdapter.addItem(it)
            clickCount++
            checkButtonState()
        }

        answersAdapter.setOnItemClickListeners {
            answersAdapter.removeItem(it)
            variantsAdapter.addItem(it)
            clickCount--
            checkButtonState()
        }

        binding.btnCheck.setOnClickListener {
            when (binding.btnCheck.text) {
                "Check" -> {
                    if (clickCount != 0) checkAnswers()
                }

                "Finish" -> {
                    findNavController().navigate(R.id.action_wordsFragment_to_imagesFragment)
                }

                "Got it!", "Continue" -> {
                    setData()
                }

                else -> {
                    if (currentIndex != testList.size - 1) {
                        currentIndex++
                        clearData()
                        setData()
                    }
                }
            }
        }

    }

    private fun clearData() {
        answersAdapter.clearAdapter()
    }

    private fun checkAnswers() {
        val answer = testList[currentIndex].answer
        var userAnswer = ""
        for (i in answersAdapter.currentList.indices) {
            userAnswer += if (i != 0) {
                " ${answersAdapter.currentList[i].word}"
            } else {
                answersAdapter.currentList[i].word
            }
        }
        Log.d("TTTT", "checkAnswers: $userAnswer")
        if (answer == userAnswer) {
            binding.btnCheck.setBackgroundResource(R.drawable.bg_btn_green)
            binding.btnCheck.text = "Continue"
            binding.llResult.show()
            binding.ivCorrect.show()
            binding.ivWrong.gone()
            binding.llCa.gone()
            binding.tvResultOfTest.text = "Nicely done!"
            binding.tvResultOfTest.setTextColor(Color.parseColor("#92D42D"))
            binding.view.show()
            binding.view.isClickable = true
        } else {
            binding.btnCheck.setBackgroundResource(R.drawable.bg_btn_red)
            binding.btnCheck.text = "Got it"
            binding.llResult.show()
            binding.ivCorrect.gone()
            binding.ivWrong.show()
            binding.llCa.show()
            binding.tvCorrectAnswer.text = answer
            binding.tvResultOfTest.text = "Incorrect"
            binding.tvResultOfTest.setTextColor(Color.parseColor("#EE5655"))
            binding.view.show()
            binding.view.isClickable = true
        }
        clickCount = 0
        if (currentIndex == testList.size - 1) {
            binding.btnCheck.text = "Finish"
        }
    }

    private fun checkButtonState() {
        if (clickCount > 0) {
            binding.btnCheck.setBackgroundResource(R.drawable.bg_btn_green)
//            binding.btnCheck.isClickable = true
        } else {
            binding.btnCheck.setBackgroundResource(R.drawable.bg_btn_tint)
//            binding.btnCheck.isClickable = false
        }
    }

    private fun initVariables() {
        variantsAdapter = WordAdapter()
        answersAdapter = WordAdapter()
        binding.rvVariants.adapter = variantsAdapter
        binding.rvAnswer.adapter = answersAdapter
        testList = Constants.testList.shuffled()
    }
}