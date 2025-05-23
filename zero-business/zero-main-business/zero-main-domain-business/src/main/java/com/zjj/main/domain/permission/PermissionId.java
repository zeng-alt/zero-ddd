package com.zjj.main.domain.permission;

import lombok.Value;
import org.jmolecules.ddd.types.Identifier;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年05月21日 11:13
 */
@Value(staticConstructor = "of")
public class PermissionId implements Identifier {
    Long id;
}