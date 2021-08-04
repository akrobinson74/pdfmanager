package com.simfund.pdfmanager.resolvers

import com.simfund.pdfmanager.entities.PdfReference
import com.simfund.pdfmanager.entities.ReportType
import com.simfund.pdfmanager.repositories.PgPdfRepository
import graphql.kickstart.tools.GraphQLMutationResolver
import org.springframework.stereotype.Component

@Component
class PdfReferenceMutationResolver(val pdfReferenceRepository: PgPdfRepository) : GraphQLMutationResolver {

    fun uploadPdf(
        clientName: String,
        countryCode: String,
        data: String,
        reportName: String,
        reportType: ReportType
    ): PdfReference =
        PdfReference(clientName, countryCode, data, reportName, reportType).let {
            pdfReferenceRepository.save(it)
        }
}