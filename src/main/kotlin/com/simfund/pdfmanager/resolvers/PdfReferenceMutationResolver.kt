package com.simfund.pdfmanager.resolvers

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.simfund.pdfmanager.entities.PdfReference
import com.simfund.pdfmanager.entities.ReportType
import com.simfund.pdfmanager.repositories.PgPdfRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class PdfReferenceMutationResolver(val pdfReferenceRepository: PgPdfRepository) : GraphQLMutationResolver {

//    @Autowired
//    lateinit var pdfReferenceRepository: PgPdfRepository

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