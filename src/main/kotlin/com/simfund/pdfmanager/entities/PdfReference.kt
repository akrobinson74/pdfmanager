package com.simfund.pdfmanager.entities

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

data class PdfFilter(
    val clientNameFilter: FilterField? = null,
    val countryCodeFilter: FilterField? = null,
    val inputFilenameFilter: FilterField? = null,
    val reportNameFilter: FilterField? = null,
    val reportTypeFilter: FilterField? = null
)

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

    @get:Column(name = "input_file_name")
    open var inputFilename: String = ""

    @get:Column(name = "report_name")
    open var reportName: String = ""

    @get:Column(name = "report_type")
    open var reportType: ReportType = ReportType.PLATFORMS

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false

        val another = other as PdfReference

        if (this.reportName != another.reportName) return false

        if (this.reportType != another.reportType) return false

        if (this.clientName != another.clientName) return false

        if (this.countryCode != another.countryCode) return false

        return true
    }

    override fun hashCode(): Int = if (id != -1L) id.hashCode() else super.hashCode()
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
