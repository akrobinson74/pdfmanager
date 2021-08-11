package com.simfund.pdfmanager.resolvers

import com.simfund.pdfmanager.entities.PdfMetadata
import com.simfund.pdfmanager.entities.PdfReference
import com.simfund.pdfmanager.entities.ReportType
import com.simfund.pdfmanager.entities.UploadInput
import com.simfund.pdfmanager.repositories.PgPdfRepository
import graphql.kickstart.servlet.context.DefaultGraphQLServletContext
import graphql.kickstart.tools.GraphQLMutationResolver
import graphql.schema.DataFetchingEnvironment
import org.apache.logging.log4j.util.Strings
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

                val (client, country, name, type) = getMetadataOrParseFilename(
                    fileName,
                    uploadMetadata?.meta?.get(index)
                )

                val newDirPath = listOf(BASE_DIR, client, country, type.name).joinToString(File.separator)
                val copyLocation: Path = Paths
                    .get(listOf(newDirPath, StringUtils.cleanPath(fileName)).joinToString(File.separator))
                Files.createDirectories(Paths.get(newDirPath))

                logger.info("Copying inputStream to: {} ({} bytes)", copyLocation, mpf.size)
                Files.copy(mpf.inputStream, copyLocation, StandardCopyOption.REPLACE_EXISTING)

                logger.debug("Saving .pdf metaData for file: {}", fileName)
                pdfList.add(
                    pdfReferenceRepository.save(
                        PdfReference(client, country, "", name, type)
                    )
                )
            }
        } catch (ex: Exception) {
            logger.error("Exception encountered uploading file(s): {}", ex.localizedMessage)
            throw ex
        }

        return pdfList.toList()
    }

    private fun getMetadataOrParseFilename(fileName: String, metaData: PdfMetadata? = null): PdfMetadata =
        metaData ?: parseFilename(fileName)

    private fun parseFilename(fileName: String): PdfMetadata =
        fileName.split(".").let { nameParts ->
            PdfMetadata(nameParts[1], nameParts[2], nameParts[0], ReportType.valueOf(nameParts[3]))
        }
}
