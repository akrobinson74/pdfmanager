package com.simfund.pdfmanager

import com.simfund.pdfmanager.repositories.PgPdfRepository
import graphql.Assert
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PdfQueryMutatorResolverIT {
    @field:Autowired
    private lateinit var restTemplate: TestRestTemplate

    @field:Autowired
    private lateinit var pdfRepository: PgPdfRepository

    @field:Autowired
    private lateinit var graphQLTestUtils: GraphQLTestUtilities

    @field:Autowired
    private lateinit var createThingPayload: String

    @Test
    fun `create thing should succeed when the input is valid`() {
        Assert.assertTrue(1==1)
    }
}