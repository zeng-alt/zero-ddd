package com.zjj.tenant;

import static org.assertj.core.api.Assertions.*;

import com.tngtech.archunit.core.domain.*;
import com.tngtech.archunit.junit.*;
import com.tngtech.archunit.lang.*;

import org.jmolecules.archunit.*;

@AnalyzeClasses(packages = "com.zjj.tenant") // (1)
class JMoleculesRulesUnitTest {

    @ArchTest ArchRule dddRules = JMoleculesDddRules.all(); // (2)

    @ArchTest ArchRule onion = JMoleculesArchitectureRules.ensureOnionSimple(); // (2)

    @ArchTest ArchRule layer = JMoleculesArchitectureRules.ensureLayering();

    // alternatively

    @ArchTest // (3)
    void detectsViolations(JavaClasses classes) {

      EvaluationResult result = onion.evaluate(classes);

      assertThat(result.hasViolation()).isFalse();
    }
}