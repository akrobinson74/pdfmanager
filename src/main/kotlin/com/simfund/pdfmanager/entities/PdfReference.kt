package com.simfund.pdfmanager.entities

import org.springframework.web.multipart.MultipartFile
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class PdfMetaData(
    val clientName: String,
    val countryCode: String,
    val reportName: String,
    val reportType: ReportType = ReportType.PLATFORMS,
    @Id
    @GeneratedValue
    val id: Long = -1
) {
    constructor() : this("", "", "")
}

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
    constructor() : this("", "", "", "")
}

data class UploadTuple(
    val meta: List<PdfMetaData>,
    val upload: List<MultipartFile>
)

enum class ReportType {
    PLATFORMS,
    SUBADVISORY_OPPORTUNITY_BY_CATEGORY,
    SUBADVISED_WATCHLIST,
    TOP_SUBADVISORS,
    VA_CONTRACTS
}
