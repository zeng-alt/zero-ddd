package com.zjj.main.domain.role;

import com.zjj.domain.component.Aggregate;
import lombok.Data;
import org.jmolecules.ddd.types.AggregateRoot;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月24日 16:04
 */
@Data
public class RoleAgg extends Aggregate<Long> {

    private Long id;
    private String roleName;
    private String roleKey;
    private String roleSort;
    private String status;
    private Integer deleted;
}
