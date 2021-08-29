package com.simfund.pdfmanager.resolvers

import com.simfund.pdfmanager.entities.FilterField
import com.simfund.pdfmanager.entities.PdfFilter
import com.simfund.pdfmanager.entities.PdfReference
import com.simfund.pdfmanager.repositories.PgPdfRepository
import graphql.kickstart.tools.GraphQLQueryResolver
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Component

@Component
class PdfReferenceQueryResolver(val pdfReferenceRepository: PgPdfRepository) : GraphQLQueryResolver {
    fun pdfs(): List<PdfReference> = pdfReferenceRepository.findAll().toList()
    fun getPdf(id: Long) = pdfReferenceRepository.findById(id)

    fun pdfsWithFilter(pdfFilter: PdfFilter): Iterable<PdfReference> {
        var pdfSpecification: Specification<PdfReference>? = null

        filterFieldTofieldNameMapping(pdfFilter)
            .forEach { (k, v) ->
                if (k != null) pdfSpecification = pdfSpecification?.and(applyFilter(k, v)) ?: applyFilter(k, v)
            }

        return if (pdfSpecification == null) pdfReferenceRepository.findAll()
        else pdfReferenceRepository.findAll(pdfSpecification)
    }

    private fun filterFieldTofieldNameMapping(pdfFilter: PdfFilter) = mapOf(
        pdfFilter.clientNameFilter to "clientName",
        pdfFilter.countryCodeFilter to "countryCode",
        pdfFilter.inputFilenameFilter to "inputFilename",
        pdfFilter.reportNameFilter to "reportName",
        pdfFilter.reportTypeFilter to "reportType"
    )

    private fun applyFilter(filter: FilterField, fieldName: String): Specification<PdfReference> =
        Specification<PdfReference> { root, _, builder ->
            filter.generateCriteria(builder, root.get(fieldName))
        }
}
