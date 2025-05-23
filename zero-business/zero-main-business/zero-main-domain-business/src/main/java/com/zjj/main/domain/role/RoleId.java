package com.zjj.main.domain.role;

import lombok.Value;
import org.jmolecules.ddd.types.Identifier;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年05月21日 11:18
 */
@Value(staticConstructor = "of")
public class RoleId implements Identifier {
    Long id;
}
