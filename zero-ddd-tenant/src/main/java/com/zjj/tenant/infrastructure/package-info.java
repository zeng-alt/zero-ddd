
@InfrastructureLayer
@ApplicationModule(allowedDependencies = {"domain", "domain::tenant", "domain::menu", "application", "interfaces"})
package com.zjj.tenant.infrastructure;

import org.jmolecules.architecture.layered.InfrastructureLayer;
import org.jmolecules.architecture.onion.simplified.InfrastructureRing;
import org.springframework.modulith.ApplicationModule;