package com.qubiz.farm

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@EnableTransactionManagement
class FarmApplication

fun main(args: Array<String>) {
	runApplication<FarmApplication>(*args)
}
