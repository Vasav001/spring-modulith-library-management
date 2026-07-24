@ApplicationModule(
        allowedDependencies = {"address::api", "common :: web"}
)
package com.vasav.springmodulithlibrarymanagement.user;

import org.springframework.modulith.ApplicationModule;

// TODO api,internal is not good, need restructuring