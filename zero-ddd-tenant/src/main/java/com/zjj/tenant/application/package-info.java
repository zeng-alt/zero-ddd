
//@ApplicationModule(allowedDependencies = {"domain.tenant", "domain.menu"})
@ApplicationRing
@Application
package com.zjj.tenant.application;

import org.jmolecules.architecture.hexagonal.Application;
import org.jmolecules.architecture.layered.ApplicationLayer;
import org.jmolecules.architecture.onion.simplified.ApplicationRing;
import org.springframework.modulith.ApplicationModule;