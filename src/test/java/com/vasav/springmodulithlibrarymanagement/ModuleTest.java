package com.vasav.springmodulithlibrarymanagement;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

class ModuleTest {
    @Test
    void fn() {
        ApplicationModules modules = ApplicationModules.of(SpringModulithLibraryManagementApplication.class);
        modules.forEach(System.out::println);
        modules.verify();
    }
}
