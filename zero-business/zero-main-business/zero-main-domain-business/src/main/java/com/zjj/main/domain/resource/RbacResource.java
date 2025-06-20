package com.zjj.main.domain.resource;

import com.zjj.domain.component.Aggregate;
import lombok.Data;
import org.jmolecules.ddd.types.AggregateRoot;
import org.jmolecules.ddd.types.Identifier;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年06月17日 17:09
 */
@Data
public abstract class RbacResource<T extends AggregateRoot<T, ID>, ID extends Identifier> extends Aggregate<T, ID> {

    private String resourceType;

    private String code;

    private String name;
}
