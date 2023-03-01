package com.example.myapplication

import org.junit.Assert
import org.junit.Test

class PalindomrTest {

    fun isPalindromeRecursiv(value: String, i: Int): Boolean {
        return if (i < (value.length / 2)) {
            val awal = i
            val akhir = value.length - i - 1
            println("${i} ${value[awal]}: ${value.length - i - 1} ${value[akhir]}")

            if (!(value[awal].equals(value[akhir]))) {
                println("Not")
                return false
            } else {
                isPalindromeRecursiv(value, i + 1)
            }
        } else {
            println("Yes")
            return true
        }
    }

    fun isPalindrome(value: String): Boolean {
        return isPalindromeRecursiv(value, 0)


        // dengan recursif

        /*
        // tanpa variable
        for (i in 0..(value.length - 1)) {
            val awal = i
            val akhir = value.length - i - 1
            println("${i} : ${value.length - i - 1}")

            if (!value[awal].equals(value[akhir])) return false
        }
        return true
         */

        /*
        // dengan optimasi
        for (i in 0 until  (value.length / 2) ) {
            val awal = i
            val akhir = value.length - i - 1
            println("${i}: ${value.length - i - 1}")

            if (!value[awal].equals(value[akhir])) return false
        }
        return true
         */

        /* dengan reverse bantuan temp variable
        var temp = ""
        for (i in (value.length-1) downTo 0) {
            temp = temp + value[i]
        }
        println(temp)
        println(value)

        return temp.equals(value)
         */
    }

    @Test
    fun test() {
        isPalindrome("kodok")
    }

    @Test
    fun testPalindrome() {
        Assert.assertTrue(isPalindrome("aa"))
        Assert.assertTrue(isPalindrome("ada"))
        Assert.assertTrue(isPalindrome("kodok"))
        Assert.assertTrue(isPalindrome(""))


        Assert.assertFalse(isPalindrome("ab"))
        Assert.assertFalse(isPalindrome("abab"))
        Assert.assertFalse(isPalindrome("kodcok"))

    }

}