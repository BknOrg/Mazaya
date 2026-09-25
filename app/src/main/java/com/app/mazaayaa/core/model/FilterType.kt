package com.app.mazaayaa.core.model

enum class FilterType(val label: String, val tableName: String?) {
    ALL("Semua", null),
    ISM("Ism","ism" ),
    FIIL("Fiil", "fiil")
}