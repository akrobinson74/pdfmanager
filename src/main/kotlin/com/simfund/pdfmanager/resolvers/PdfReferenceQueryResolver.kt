package com.simfund.pdfmanager.resolvers

import arrow.core.getOrElse
import com.simfund.pdfmanager.entities.FilterField
import com.simfund.pdfmanager.entities.PdfReference
import com.simfund.pdfmanager.repositories.PgPdfRepository
import graphql.kickstart.tools.GraphQLQueryResolver
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Component

@Component
class PdfReferenceQueryResolver(val pdfReferenceRepository: PgPdfRepository) : GraphQLQueryResolver {
    fun pdfs(): List<PdfReference> = pdfReferenceRepository.findAll().toList()
    fun getPdf(id: Long) = pdfReferenceRepository.findById(id)

    fun pdfsWithFilter(filterList: List<FilterField>): Iterable<PdfReference> = filterList.let { it ->
        var pdfSpecification: Specification<PdfReference>? = null
        it.forEach { ff ->
            pdfSpecification = pdfSpecification?.and(applyFilter(ff)) ?: applyFilter(ff)
        }
        return if (pdfSpecification == null) pdfReferenceRepository.findAll()
        else pdfReferenceRepository.findAll(pdfSpecification)
    }

    private fun applyFilter(filter: FilterField): Specification<PdfReference>? =
        Specification<PdfReference> { root, _, builder ->
            val attribute = filter.fieldName.toCamelCase()
            filter.generateCriteria(builder, root.get(attribute)).getOrElse { null }
        }
}
