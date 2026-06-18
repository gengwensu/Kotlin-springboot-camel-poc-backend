package com.hnp.poc

import com.hnp.submgmt.SubMgmtApplication
import com.hnp.submgmt.TestcontainersConfiguration
import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
	fromApplication<SubMgmtApplication>().with(TestcontainersConfiguration::class).run(*args)
}
