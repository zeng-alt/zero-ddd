package com.zjj.main.domain.resource.http;

import lombok.Value;
import org.jmolecules.ddd.types.Identifier;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年06月17日 17:11
 */
@Value(staticConstructor = "of")
public class HttpResourceId implements Identifier {
    Long id;
}
