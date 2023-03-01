package com.example.bossku.utils

import androidx.navigation.NavOptions
import com.example.myapplication.R

fun getDefaultNavOptions(): NavOptions {
    return NavOptions.Builder()
        .setEnterAnim(R.anim.anim_slide_in_right)
        .setExitAnim(R.anim.anim_slide_out_left)
        .setPopEnterAnim(R.anim.anim_slide_in_right)
        .setPopExitAnim(R.anim.anim_slide_out_left)
        .build()
}