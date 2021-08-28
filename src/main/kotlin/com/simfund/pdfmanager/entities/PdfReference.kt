package com.simfund.pdfmanager.entities

import javax.persistence.*

data class PdfMetadata(
    val clientName: String,
    val countryCode: String,
    val inputFilename: String? = null,
    val reportName: String,
    val reportType: ReportType = ReportType.PLATFORMS,
)

@Entity
@Table(name = "pdfs")
open class PdfReference {
    @get:Id
    @get:GeneratedValue
    @get:Column(name = "id")
    open var id: Long = -1

    @get:Column(name = "client_name")
    open var clientName: String = ""

    @get:Column(name = "country_code")
    open var countryCode: String = ""

    @get:Column(name = "client_name")
    open var inputFilename: String = ""

    @get:Column(name = "client_name")
    open var reportName: String = ""

    @get:Column(name = "report_type")
    open var reportType: ReportType = ReportType.PLATFORMS

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false

        val another = other as PdfReference
        
        if (!this.reportName.equals(another.reportName)) return false

        if (!this.reportType.equals(another.reportType)) return false
        
        if (!this.clientName.equals(another.clientName)) return false

        if (!this.countryCode.equals(another.countryCode)) return false

        return true
    }

    override fun hashCode(): Int = if (!id.equals(-1)) id.hashCode() else super.hashCode()
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
