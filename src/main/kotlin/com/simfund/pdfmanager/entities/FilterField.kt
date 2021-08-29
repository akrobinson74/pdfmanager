package com.simfund.pdfmanager.entities

import arrow.core.Either
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Path
import javax.persistence.criteria.Predicate

data class FilterField(val fieldName: FilterFieldName, val operator: FilterOperator, val value: String) {

    fun generateCriteria(builder: CriteriaBuilder, field: Path<Any>): Either<Throwable, Predicate> = Either.catch {
        @Suppress("UNCHECKED_CAST")
        when (operator) {
            FilterOperator.EQ -> builder.equal(field as Path<Int>, value.toInt())
            FilterOperator.GE -> builder.ge(field as Path<Int>, value.toInt())
            FilterOperator.GT -> builder.gt(field as Path<Int>, value.toInt())
            FilterOperator.LE -> builder.le(field as Path<Int>, value.toInt())
            FilterOperator.LT -> builder.lt(field as Path<Int>, value.toInt())
            FilterOperator.CONTAINS -> builder.like(field as Path<String>, "%$value%")
            FilterOperator.ENDS_WITH -> builder.like(field as Path<String>, "%$value")
            FilterOperator.EQUALS ->
                // kinda hacky...find a better solution
                if (fieldName == FilterFieldName.REPORT_TYPE)
                    builder.equal(field as Path<ReportType>, ReportType.valueOf(value))
                else builder.equal(field as Path<String>, value)
            FilterOperator.STARTS_WITH -> builder.like(field as Path<String>, "$value%")
        }
    }
}
