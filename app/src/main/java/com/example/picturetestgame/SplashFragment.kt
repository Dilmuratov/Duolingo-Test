package com.example.picturetestgame

import android.animation.Animator
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.picturetestgame.databinding.FragmentSplashBinding

class SplashFragment : Fragment(R.layout.fragment_splash) {
    private lateinit var binding: FragmentSplashBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSplashBinding.bind(view)

        binding.lottieAnimation.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {

            }

            override fun onAnimationEnd(animation: Animator) {

            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {
                findNavController().navigate(R.id.action_splashFragment_to_wordsFragment)
            }
        })

    }
}