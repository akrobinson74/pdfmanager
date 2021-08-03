package com.simfund.pdfmanager

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PdfmanagerApplication

fun main(args: Array<String>) {
    runApplication<PdfmanagerApplication>(*args)
}
