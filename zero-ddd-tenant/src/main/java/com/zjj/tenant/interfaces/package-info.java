@InterfaceLayer
@ApplicationModule(allowedDependencies = {"domain", "domain::menu", "domain::tenant", "domain::tenant::cmd", "application::service", "application::form"})
package com.zjj.tenant.interfaces;

import org.jmolecules.architecture.layered.InterfaceLayer;
import org.jmolecules.ddd.annotation.Module;
import org.springframework.modulith.ApplicationModule;