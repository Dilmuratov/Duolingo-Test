package com.example.picturetestgame

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.Fragment
import com.example.picturetestgame.databinding.FragmentImagesBinding
import com.example.picturetestgame.models.ImageData
import com.example.picturetestgame.models.ImageTestData
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class ImagesFragment : Fragment(R.layout.fragment_images) {
    private lateinit var binding: FragmentImagesBinding
    private lateinit var imageTestList: List<ImageTestData>
    private var currentIndex = 0
    private var selectedAnswerId = -1
    private lateinit var question: ImageTestData
    private lateinit var answers: List<ImageData>
    private lateinit var buttonList: List<View>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentImagesBinding.bind(view)

        initVariables()

        setData()

        initListeners()

    }

    private fun setData() {
        if (currentIndex < imageTestList.size) {
            question = imageTestList[currentIndex]
            binding.tvQuestion.text = "Which of these is «${question.answer.string}»"
            answers = question.answers.shuffled()
            MainScope().launch {
                binding.ivAnswer1.setImage(answers[0].image)
                binding.ivAnswer2.setImage(answers[1].image)
                binding.ivAnswer3.setImage(answers[2].image)
                binding.ivAnswer4.setImage(answers[3].image)
            }

            val all = imageTestList.size
            val weight = ((currentIndex + 1) * 100).toFloat() / all

            val layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                LinearLayoutCompat.LayoutParams.MATCH_PARENT
            )

            layoutParams.weight = weight

            binding.current.layoutParams = layoutParams
        } else {
            binding.btnCheck.text = "Finish"
        }
    }

    private fun initVariables() {
        imageTestList = Constants.imageTestDataList.shuffled()
        buttonList = listOf(
            binding.strokeAnswer1,
            binding.strokeAnswer2,
            binding.strokeAnswer3,
            binding.strokeAnswer4
        )

    }

    private fun clearData() {
        binding.strokeAnswer1.setBackgroundResource(R.color.default_color_answer)
        binding.strokeAnswer2.setBackgroundResource(R.color.default_color_answer)
        binding.strokeAnswer3.setBackgroundResource(R.color.default_color_answer)
        binding.strokeAnswer4.setBackgroundResource(R.color.default_color_answer)
        selectedAnswerId = -1
    }

    private fun checkAnswer() {
        if (selectedAnswerId != -1 && selectedAnswerId < imageTestList.size) {
//            if (question.answer.id == answers[selectedAnswerId].id) {
//                buttonList[selectedAnswerId]
//            }
            buttonList[selectedAnswerId].setBackgroundResource(R.color.uncorrect_answer)
            for (i in answers.indices) {
                if (answers[i].id == question.answer.id) {
                    buttonList[i].setBackgroundResource(R.color.correct_answer)
                }
            }
        }
    }

    private fun initListeners() {
        binding.apply {
            cvAnswer1.setOnClickListener {
                optionSelected(strokeAnswer1)
                selectedAnswerId = 0
                checkStateButton()
            }

            cvAnswer2.setOnClickListener {
                optionSelected(strokeAnswer2)
                selectedAnswerId = 1
                checkStateButton()
            }

            cvAnswer3.setOnClickListener {
                optionSelected(strokeAnswer3)
                selectedAnswerId = 2
                checkStateButton()
            }

            cvAnswer4.setOnClickListener {
                optionSelected(strokeAnswer4)
                selectedAnswerId = 3
                checkStateButton()
            }
        }

        binding.btnCheck.setOnClickListener {
            when (binding.btnCheck.text) {
                "Check" -> {
                    if (selectedAnswerId != -1) {
                        checkAnswer()
                        binding.btnCheck.text = "Next"
                        selectedAnswerId = -1
                    } else return@setOnClickListener
                }

                "Finish" -> {
                    showDialog()
                }

                else -> {
                    if (currentIndex != imageTestList.size - 1) {
                        currentIndex++
                        setData()
                        clearData()
                        binding.btnCheck.text = "Check"
                    } else {
                        binding.btnCheck.text = "Finish"
                    }
                }
            }
        }
    }

    private fun showDialog() {
        val dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_dialog)

        val btn = dialog.findViewById<Button>(R.id.btn_exit)

        btn.setOnClickListener {
            requireActivity().finishAffinity()
        }

        dialog.show()
    }

    private fun optionSelected(strokeAnswerView: View) {
        binding.apply {
            strokeAnswer1.setBackgroundResource(R.color.default_color_answer)
            strokeAnswer2.setBackgroundResource(R.color.default_color_answer)
            strokeAnswer3.setBackgroundResource(R.color.default_color_answer)
            strokeAnswer4.setBackgroundResource(R.color.default_color_answer)
        }
        strokeAnswerView.setBackgroundResource(R.color.selected_color_answer)
    }

    private fun checkStateButton() {
        if (selectedAnswerId != -1) {
            binding.btnCheck.isClickable = true
            binding.btnCheck.setBackgroundResource(R.drawable.bg_btn_green)
        } else {
            binding.btnCheck.isClickable = false
            binding.btnCheck.setBackgroundResource(R.drawable.bg_btn_tint)
        }
    }

}