package com.simfund.pdfmanager.entities

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.Locale

@Document(collection="pdfs")
data class PdfReference(
    val clientName: String,
    val countryCode: String,
    val data: String,
    val reportName: String,
    val reportType: ReportType
) {
    @Id
    var id: String = "";
}

enum class ReportType {
    PLATFORMS,
    SUBADVISORY_OPPORTUNITY_BY_CATEGORY,
    SUBADVISED_WATCHLIST,
    TOP_SUBADVISORS,
    VA_CONTRACTS
}
