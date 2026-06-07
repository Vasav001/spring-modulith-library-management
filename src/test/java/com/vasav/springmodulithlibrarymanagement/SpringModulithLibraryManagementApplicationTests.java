package com.vasav.springmodulithlibrarymanagement;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;

@SpringBootTest
class SpringModulithLibraryManagementApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void verifyModularStructure() {
		ApplicationModules.of(SpringModulithLibraryManagementApplication.class).verify();
	}
}
