package com.simfund.pdfmanager.repositories

import com.simfund.pdfmanager.entities.PdfReference
import com.simfund.pdfmanager.entities.ReportType
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PgPdfRepository : CrudRepository<PdfReference, Long> {
    fun findByClientName(name: String): List<PdfReference>
    fun findByCountryCode(code: String): List<PdfReference>
    fun findByReportName(name: String): List<PdfReference>
    fun findByReportType(reportType: ReportType): List<PdfReference>
}