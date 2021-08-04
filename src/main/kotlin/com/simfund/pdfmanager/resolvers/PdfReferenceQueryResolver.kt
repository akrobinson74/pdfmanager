package com.simfund.pdfmanager.resolvers

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.simfund.pdfmanager.entities.PdfReference
import com.simfund.pdfmanager.repositories.PgPdfRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class PdfReferenceQueryResolver(val pdfReferenceRepository: PgPdfRepository) : GraphQLQueryResolver {
//    @Autowired
//    lateinit var pdfReferenceRepository: PgPdfRepository

    fun pdfs(): List<PdfReference> = pdfReferenceRepository.findAll().toList()
    fun getPdf(id: Long) = pdfReferenceRepository.findById(id)
}