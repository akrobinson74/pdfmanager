package com.simfund.pdfmanager.repositories

import com.simfund.pdfmanager.entities.PdfReference
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface PdfReferenceRepository : MongoRepository<PdfReference, String>