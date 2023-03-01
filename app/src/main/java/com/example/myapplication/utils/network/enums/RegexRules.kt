package com.example.bossku.utils.network.enums

fun minMaxRule(min: Int, max: Int) = Regex("^.{$min,$max}\$")

fun optionalNikRule() = Regex("^\\s*(?:[0-9]{16}+\$|^)\\s*\$")

fun optionalRekeningRule(min: Int, max: Int) = Regex("^\\s*(?:[0-9]{$min,$max}+\$|^)\\s*\$")

fun mobileNumberOnlyRule() = Regex("$|^([+62]|08)[0-9]{8,14}\$")

fun optionalEmailRule() = Regex(
    "^$|^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})\$"
)