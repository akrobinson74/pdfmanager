package com.simfund.pdfmanager.resolvers

import com.simfund.pdfmanager.entities.PdfReference
import com.simfund.pdfmanager.entities.ReportType
import com.simfund.pdfmanager.entities.UploadTuple
import com.simfund.pdfmanager.repositories.PgPdfRepository
import graphql.kickstart.tools.GraphQLMutationResolver
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.security.InvalidParameterException


private const val BASE_DIR = "file:///opt/pdf/data"

@Component
class PdfReferenceMutationResolver(val pdfReferenceRepository: PgPdfRepository) : GraphQLMutationResolver {
    private val logger = LoggerFactory.getLogger(PdfReferenceMutationResolver::class.java)

    fun uploadPdf(
        clientName: String,
        countryCode: String,
        data: String,
        reportName: String,
        reportType: ReportType
    ): PdfReference =
        PdfReference(clientName, countryCode, data, reportName, reportType).let {
            pdfReferenceRepository.save(it)
        }

    fun uploadPdfs(
        uploadInput: UploadTuple
    ): List<PdfReference> = uploadInput.let {
        val resultList = mutableListOf<PdfReference>()

        it.upload.forEach { mpf ->
            try {
                val fileName =
                    mpf.originalFilename ?: throw InvalidParameterException("Upload 'originalFilename' cannot be null")
                val copyLocation: Path = Paths
                    .get(BASE_DIR + File.separator + StringUtils.cleanPath(fileName))
                Files.copy(mpf.inputStream, copyLocation, StandardCopyOption.REPLACE_EXISTING)

                val (client, country, name, type) = parseFilename(fileName)
                resultList.add(
                    pdfReferenceRepository.save(
                        PdfReference(client, country, "", name, ReportType.valueOf(type))
                    )
                )
            } catch (ex: Exception) {
                logger.error("Exception encountered uploading multiple files: ${ex.message}")
                throw ex
            }
        }

        resultList.toList()
    }

    private fun parseFilename(fileName: String): List<String> =
        fileName.split(".").let { nameParts ->
            listOf(nameParts[1], nameParts[2], nameParts[0], nameParts[3])
        }
}