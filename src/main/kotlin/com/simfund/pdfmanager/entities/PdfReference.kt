package com.simfund.pdfmanager.entities

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class PdfReference(
    val clientName: String,
    val countryCode: String,
    val data: String,
    val reportName: String,
    val reportType: ReportType = ReportType.PLATFORMS,
    @Id
    @GeneratedValue
    val id: Long = -1
) {
    constructor(): this("", "", "", "")
}

enum class ReportType {
    PLATFORMS,
    SUBADVISORY_OPPORTUNITY_BY_CATEGORY,
    SUBADVISED_WATCHLIST,
    TOP_SUBADVISORS,
    VA_CONTRACTS
}
