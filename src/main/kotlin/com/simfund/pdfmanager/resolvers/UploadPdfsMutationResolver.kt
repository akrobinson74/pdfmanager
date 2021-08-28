package com.simfund.pdfmanager.resolvers

import com.simfund.pdfmanager.entities.PdfMetadata
import com.simfund.pdfmanager.entities.PdfReference
import com.simfund.pdfmanager.entities.ReportType
import com.simfund.pdfmanager.entities.UploadInput
import com.simfund.pdfmanager.repositories.PgPdfRepository
import graphql.kickstart.servlet.context.DefaultGraphQLServletContext
import graphql.kickstart.tools.GraphQLMutationResolver
import graphql.schema.DataFetchingEnvironment
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

private const val BASE_DIR = "/opt/pdf/data"

@Component
class UploadPdfsMutationResolver(val pdfReferenceRepository: PgPdfRepository) : GraphQLMutationResolver {
    private val logger = LoggerFactory.getLogger(UploadPdfsMutationResolver::class.java)

    fun uploadPdfs(dfe: DataFetchingEnvironment): List<PdfReference> =
        storeAndSavePdf(dfe.getContext(), null)

    fun uploadPdfsWithMetadata(
        uploadMetadata: UploadInput,
        dfe: DataFetchingEnvironment
    ): List<PdfReference> =
        storeAndSavePdf(dfe.getContext(), uploadMetadata)

    private fun storeAndSavePdf(
        context: DefaultGraphQLServletContext,
        uploadMetadata: UploadInput? = null
    ): List<PdfReference> {
        val pdfList = mutableListOf<PdfReference>()

        try {
            context.fileParts.forEachIndexed { index, mpf ->
                val fileName = mpf.submittedFileName

                val (client, country, _, reportName, type) = getMetadataOrParseFilename(
                    fileName,
                    uploadMetadata?.meta?.get(index)
                )

                val newDirPath = listOf(BASE_DIR, client, country, type.name).joinToString(File.separator)
                Files.createDirectories(Paths.get(newDirPath))
                val copyLocation: Path = Paths
                    .get(listOf(newDirPath, StringUtils.cleanPath("$reportName.pdf")).joinToString(File.separator))

                logger.info("Copying inputStream named {} to: {} ({} bytes)", fileName, copyLocation, mpf.size)
                Files.copy(mpf.inputStream, copyLocation, StandardCopyOption.REPLACE_EXISTING)

                logger.debug("Saving .pdf metaData for file: {}", fileName)
                pdfList.add(
                    pdfReferenceRepository.save(
                        getPdfReferenceForValues(client, country, fileName, reportName, type)
                    )
                )
            }
        } catch (ex: Exception) {
            logger.error("Exception encountered uploading file(s): {}", ex.localizedMessage)
            throw ex
        }

        return pdfList.toList()
    }

    private fun getPdfReferenceForValues(
        client: String,
        country: String,
        fileName: String,
        reportName: String,
        type: ReportType
    ): PdfReference =
        PdfReference().let {
            it.clientName = client
            it.countryCode = country
            it.inputFilename = fileName
            it.reportName = reportName
            it.reportType = type
            it
        }

    private fun getMetadataOrParseFilename(fileName: String, metaData: PdfMetadata? = null): PdfMetadata =
        metaData?.copy(inputFilename = fileName) ?: parseFilename(fileName)

    private fun parseFilename(fileName: String): PdfMetadata =
        fileName.split(".").let { nameParts ->
            PdfMetadata(nameParts[0], nameParts[1], fileName, nameParts[2], ReportType.valueOf(nameParts[3]))
        }
}
