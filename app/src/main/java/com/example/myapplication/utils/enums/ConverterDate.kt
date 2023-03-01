package com.example.bossku.utils.enums

enum class ConverterDate(val formatter: String) {
    TIME_ONLY("HH:mm aa"),
    FULL_DATE("dd MMMM yyyy"),
    SHORT_DATE("dd MMM yyyy"),
    SIMPLE_DATE("dd-mm-yyyy"),
    SIMPLE_DATE2("yyyy-MM-dd"),
    SIMPLE_DATE_TIME("dd/MM/yyyy HH:mm"),
    SQL_DATE("yyyy-MM-dd"),
    SIMPLE_DAY("EEE"),
    SIMPLE_MONTH("MMM"),
    SIMPLE_DAY_MONTH("dd MMMM"),
    UTC2("yyyy-MM-dd'T'HH:mm:SSS'Z'"),
    UTCDETAIL("yyyy-MM-dd'T'HH:mm:ss.SSS+HH:mm'Z'"),
    UTC("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"),
    SIMPLE_DAY_DATE("EEEE, dd MMM yyyy"),
    SIMPLE_DAY_DATE_TIME("dd MMM yyyy • HH:mm")
}