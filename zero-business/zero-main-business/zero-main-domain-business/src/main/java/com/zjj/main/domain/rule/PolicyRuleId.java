package com.zjj.main.domain.rule;

import lombok.Value;
import org.jmolecules.ddd.types.Identifier;

@Value(staticConstructor = "of")
public class PolicyRuleId implements Identifier {
    Long id;
}