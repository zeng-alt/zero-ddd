package com.zjj.main.domain.user;

import lombok.Value;
import org.jmolecules.ddd.types.Identifier;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年05月21日 11:19
 */
@Value(staticConstructor = "of")
public class UserId implements Identifier {
    Long id;
}
