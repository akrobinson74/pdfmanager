package com.simfund.pdfmanager.resolvers

import com.simfund.pdfmanager.entities.PdfReference
import com.simfund.pdfmanager.entities.ReportType
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

    fun uploadPdfs(dfe: DataFetchingEnvironment): List<PdfReference> = dfe.run {
        val pdfList = mutableListOf<PdfReference>()
        val context = this.getContext<DefaultGraphQLServletContext>()

        context .fileParts.forEach { mpf ->
            try {
                val fileName = mpf.submittedFileName

                val copyLocation: Path = Paths
                    .get(BASE_DIR + File.separator + StringUtils.cleanPath(fileName))
                
                logger.info("Copying inputStream to: {} ({} bytes)", copyLocation, mpf.size)
                Files.copy(mpf.inputStream, copyLocation, StandardCopyOption.REPLACE_EXISTING)

                logger.debug("Saving .pdf metaData for file: {}", fileName)
                val (client, country, name, type) = parseFilename(fileName)
                pdfList.add(
                    pdfReferenceRepository.save(
                        PdfReference(client, country, "", name, ReportType.valueOf(type))
                    )
                )
            } catch (ex: Exception) {
                logger.error("Exception encountered uploading multiple files: {}", ex.localizedMessage)
                throw ex
            }
        }

        pdfList.toList()
    }

    private fun parseFilename(fileName: String): List<String> =
        fileName.split(".").let { nameParts ->
            listOf(nameParts[1], nameParts[2], nameParts[0], nameParts[3])
        }
}
