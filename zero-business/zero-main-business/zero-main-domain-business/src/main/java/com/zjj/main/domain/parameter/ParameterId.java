package com.zjj.main.domain.parameter;

import lombok.Value;
import org.jmolecules.ddd.types.Identifier;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年05月21日 11:22
 */
@Value(staticConstructor = "of")
public class ParameterId implements Identifier {
    Long id;
}
