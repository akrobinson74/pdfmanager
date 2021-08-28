package com.simfund.pdfmanager.entities

import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Path
import javax.persistence.criteria.Predicate

data class FilterField(val operator: String, val value: String) {

    fun generateCriteria(builder: CriteriaBuilder, field: Path<String>): Predicate? {
        try {
            val v = value.toInt()
            val f = field as Path<Int>
            return when (operator) {
                "lt" -> builder.lt(f, v)
                "le" -> builder.le(f, v)
                "gt" -> builder.gt(f, v)
                "ge" -> builder.ge(f, v)
                "eq" -> builder.equal(f, v)
                else -> throw Exception()
            }
        } catch (e: NumberFormatException) {
            return when (operator) {
                "endsWith" -> builder.like(field, "%$value")
                "startsWith" -> builder.like(field, "$value%")
                "contains" -> builder.like(field, "%$value%")
                "eq" -> builder.equal(field, value)
                else -> throw Exception()
            }
        } catch (ex: Exception) {
            throw Exception("Unable to cast Path as Path<Int>: $field.")
        }
    }
//    fun generateCriteria(builder: CriteriaBuilder, field: Path): Predicate? {
//        try {
//            val query = builder.createQuery(PdfReference::class.java)
//            val root = query.from(PdfReference::class.java)
//            val intValue = Integer.parseInt(value)
//            return when (operator) {
//                "eq" -> builder.equal(root.get<Long>("id"), intValue)
//                "ge" -> builder.ge(field, intValue)
//                "gt" -> builder.gt(field, intValue)
//                else -> throw Exception("u is bad")
//            }
//        } catch (e: Exception) {
//
//        }
//
//        return null
//    }
}