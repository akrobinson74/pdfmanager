package com.simfund.pdfmanager.entities

enum class FilterFieldName {
    CLIENT_NAME,
    COUNTRY_CODE,
    INPUT_FILE_NAME,
    REPORT_NAME,
    REPORT_TYPE;

    fun toCamelCase() = this.name.lowercase().split("_").mapIndexed { index, s ->
        if (index == 0) s
        else s[0].uppercase() + s.substring(1)
    }
        .joinToString("")
}
