package com.zjj.main.infrastructure.db.jpa.entity;

import com.zjj.domain.component.BaseEntity;
import com.zjj.domain.component.TenantAuditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.TenantId;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年05月16日 16:50
 */
@Getter
@Setter
@Entity
@Table(name = "main_dict_type")
public class DictType extends BaseEntity<Long> implements TenantAuditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 字典类型
     */
    private String dictCode;

    /**
     * 备注
     */
    private String remark;

    @OneToMany(mappedBy = "dictType", cascade = CascadeType.ALL)
    @OrderBy("dictSort DESC")
    private List<DictData> dictDatas = new ArrayList<>();

    @TenantId
    @Nullable
    private String tenantBy;
}
