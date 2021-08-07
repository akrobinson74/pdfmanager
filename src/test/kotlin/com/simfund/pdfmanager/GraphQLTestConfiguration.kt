package com.simfund.pdfmanager

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import org.springframework.util.StreamUtils
import java.nio.charset.StandardCharsets

@Configuration
class GraphQLTestConfiguration {
    @Value("classpath:graphql/query-wrapper.json")
    private lateinit var queryWrapperFile: Resource

    @Value("classpath:pdfreference.graphql")
    private lateinit var pdfReferenceGraphQLFile: Resource

    @Bean
    fun createThingPayload(): String {
        return StreamUtils.copyToString(pdfReferenceGraphQLFile.inputStream, StandardCharsets.UTF_8)
    }

    @Bean
    fun queryWrapper(): String {
        return StreamUtils.copyToString(queryWrapperFile.inputStream, StandardCharsets.UTF_8)
    }
}