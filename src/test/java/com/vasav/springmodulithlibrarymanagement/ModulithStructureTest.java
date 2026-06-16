package com.vasav.springmodulithlibrarymanagement;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

public class ModulithStructureTest {

    @Test
    void verifyModularStructure() {
        ApplicationModules.of(SpringModulithLibraryManagementApplication.class).verify();
    }
}
