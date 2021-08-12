package com.simfund.pdfmanager.entities

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

data class PdfMetadata(
    val clientName: String,
    val countryCode: String,
    val inputFilename: String? = null,
    val reportName: String,
    val reportType: ReportType = ReportType.PLATFORMS,
)

@Entity
data class PdfReference(
    val clientName: String,
    val countryCode: String,
    val inputFilename: String,
    val reportName: String,
    val reportType: ReportType = ReportType.PLATFORMS,
    @Id
    @GeneratedValue
    val id: Long = -1
) {
    constructor() : this("", "", "", "")
}

data class UploadInput(
    val meta: List<PdfMetadata>
)

enum class ReportType {
    PLATFORMS,
    SUBADVISORY_OPPORTUNITY_BY_CATEGORY,
    SUBADVISED_WATCHLIST,
    TOP_SUBADVISORS,
    VA_CONTRACTS
}
