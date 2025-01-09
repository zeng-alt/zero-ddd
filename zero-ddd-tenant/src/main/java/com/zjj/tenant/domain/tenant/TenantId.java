package com.zjj.tenant.domain.tenant;

import lombok.Value;
import org.jmolecules.ddd.types.Identifier;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年01月07日 21:12
 */
@Value(staticConstructor = "of")
public class TenantId implements Identifier {
    Long id;
}
