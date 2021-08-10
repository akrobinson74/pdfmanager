package com.simfund.pdfmanager.resolvers

import com.simfund.pdfmanager.entities.PdfReference
import com.simfund.pdfmanager.repositories.PgPdfRepository
import graphql.kickstart.tools.GraphQLQueryResolver
import org.springframework.stereotype.Component

@Component
class PdfReferenceQueryResolver(val pdfReferenceRepository: PgPdfRepository) : GraphQLQueryResolver {
    fun pdfs(): List<PdfReference> = pdfReferenceRepository.findAll().toList()
    fun getPdf(id: Long) = pdfReferenceRepository.findById(id)
}
