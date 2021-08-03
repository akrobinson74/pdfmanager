package com.simfund.pdfmanager.resolvers

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.simfund.pdfmanager.entities.PdfReference
import com.simfund.pdfmanager.entities.ReportType
import com.simfund.pdfmanager.repositories.PdfReferenceRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class PdfReferenceMutationResolver(val pdfReferenceRepository: PdfReferenceRepository) : GraphQLMutationResolver {
    fun uploadPdf(
        clientName: String,
        countryCode: String,
        data: String,
        reportName: String,
        reportType: ReportType
    ): PdfReference =
        PdfReference(clientName, countryCode, data, reportName, reportType).let {
            it.id = UUID.randomUUID().toString()
            pdfReferenceRepository.save(it)
        }
}