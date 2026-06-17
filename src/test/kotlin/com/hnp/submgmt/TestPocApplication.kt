package com.hnp.poc

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
	fromApplication<PocApplication>().with(TestcontainersConfiguration::class).run(*args)
}
