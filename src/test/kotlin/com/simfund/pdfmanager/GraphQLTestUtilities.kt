package com.simfund.pdfmanager

import com.fasterxml.jackson.core.io.JsonStringEncoder
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Small utility component for GraphQL tests.
 * @author Sebastien Dubois
 */
@Component
class GraphQLTestUtilities {
    /**
     * Basic empty GraphQL query.
     */
    @field:Autowired
    private lateinit var queryWrapper: String

    private val jsonStringEncoder = JsonStringEncoder.getInstance()

    /**
     * Call this method with a valid GraphQL query/mutation String
     * This function will escape it properly and wrap it into a JSON query object
     */
    fun createJsonQuery(graphQL: String): String {
        // TODO add support for setting variables in the query
        return queryWrapper.replace("__payload__", escapeQuery(graphQL))
    }

    /**
     * Clean the given QraphQL query so that it can be embedded in a JSON string.
     */
    fun escapeQuery(graphQL: String): String {
        return jsonStringEncoder.quoteAsString(graphQL).joinToString("")
    }

    companion object {
        /**
         * Where to send GraphQL requests
         */
        const val ENDPOINT_LOCATION: String = "/graphql"
    }

    /**
     * Parse the given payload as a [JsonNode]
     * @return the payload parsed as JSON
     */
    fun parse(payload: String): JsonNode {
        return ObjectMapper().readTree(payload)
    }
}