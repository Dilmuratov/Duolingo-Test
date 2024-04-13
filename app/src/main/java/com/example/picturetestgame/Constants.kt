package com.example.picturetestgame

import com.example.picturetestgame.models.ImageData
import com.example.picturetestgame.models.ImageTestData
import com.example.picturetestgame.models.WordTestData

object Constants {
    val testList = listOf(
        WordTestData(
            0,
            "Mening ismim Damir",
            "My name is Damir",
            listOf("My", "name", "is", "Damir", "are", "Mine", "surname", "names")
        ),
        WordTestData(
            1,
            "Men maktabga boraman",
            "I go to school",
            listOf("I", "go", "to", "school", "went", "Me", "going", "goes")
        ),
        WordTestData(
            2,
            "Men studentman",
            "I am a student",
            listOf("I", "am", "a", "student", "You", "are", "an", "is")
        ),
        WordTestData(
            3,
            "Men 21 yoshdaman",
            "I am 21",
            listOf("I", "am", "21", "an", "old", "Mine", "name", "have")
        ),
        WordTestData(
            4,
            "Dasturchi",
            "Developer",
            listOf(
                "Developer",
                "Computer",
                "Engineer",
                "Doctor",
                "Student",
                "Android",
                "Kotlin",
                "Java"
            )
        )
    )

    private val imageDataList = listOf(
        ImageData(0, "Apple", R.drawable.ic_apple),
        ImageData(1, "Orange", R.drawable.ic_orange),
        ImageData(2, "Flower", R.drawable.ic_flower),
        ImageData(3, "Dog", R.drawable.ic_dog),
        ImageData(4, "Cat", R.drawable.ic_cat)
    )

    val imageTestDataList = listOf(
        ImageTestData(0, imageDataList[0], listOf(imageDataList[0], imageDataList[1], imageDataList[2], imageDataList[3])),
        ImageTestData(1, imageDataList[1], listOf(imageDataList[1], imageDataList[2], imageDataList[3], imageDataList[4])),
        ImageTestData(2, imageDataList[2], listOf(imageDataList[2], imageDataList[3], imageDataList[4], imageDataList[1])),
        ImageTestData(3, imageDataList[3], listOf(imageDataList[3], imageDataList[4], imageDataList[1], imageDataList[2])),
        ImageTestData(4, imageDataList[4], listOf(imageDataList[4], imageDataList[1], imageDataList[2], imageDataList[3])),
    )
}