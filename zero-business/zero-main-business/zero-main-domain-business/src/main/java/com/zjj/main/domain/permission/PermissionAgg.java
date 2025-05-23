package com.zjj.main.domain.permission;

import com.zjj.domain.component.Aggregate;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.TenantId;
import org.jmolecules.ddd.types.AggregateRoot;
import org.jmolecules.ddd.types.Association;
import org.springframework.lang.Nullable;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年05月21日 11:06
 */
@Data
public class PermissionAgg extends Aggregate<PermissionAgg, PermissionId> {
    private PermissionId id;
    private String resourceType;
    private String code;
}
