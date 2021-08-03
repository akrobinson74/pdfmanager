package com.simfund.pdfmanager.resolvers

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.simfund.pdfmanager.entities.PdfReference
import com.simfund.pdfmanager.repositories.PdfReferenceRepository
import org.springframework.stereotype.Component

@Component
class PdfReferenceQueryResolver(val pdfReferenceRepository: PdfReferenceRepository) : GraphQLQueryResolver {
    fun pdfs(): List<PdfReference> = pdfReferenceRepository.findAll()
    fun getPdf(id: String) = pdfReferenceRepository.findById(id)
}