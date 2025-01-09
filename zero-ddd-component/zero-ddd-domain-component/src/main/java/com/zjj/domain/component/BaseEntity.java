package com.zjj.domain.component;

//import com.zjj.domain.component.config.TenantEntityListener;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import lombok.Setter;
import org.hibernate.annotations.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Auditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.util.ProxyUtils;
import org.springframework.lang.Nullable;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月30日 20:05
 */
@MappedSuperclass
//@FilterDef(name = "tenantFilter", parameters = {@ParamDef(name = "tenantId", type = Long.class)})
//@Filter(name = "tenantFilter", condition = "tenant_id = :tenantId")
//@EntityListeners({AuditingEntityListener.class, TenantEntityListener.class})
@EntityListeners({AuditingEntityListener.class})
public abstract class BaseEntity<PK extends Serializable> implements Auditable<String, PK, LocalDateTime>, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @CreatedBy
    @Nullable
    @Column(name = "created_by", updatable = false)
    private String createdBy;
    @CreatedDate
    @Nullable
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;
    @Nullable
    @LastModifiedBy
    private String lastModifiedBy;
    @LastModifiedDate
    @Nullable
    private LocalDateTime lastModifiedDate;

    /**
     * Returns the user who created this entity.
     *
     * @return the createdBy
     */
    @Override
    public Optional<String> getCreatedBy() {
        return Optional.ofNullable(this.createdBy);
    }

    /**
     * Returns the creation date of the entity.
     *
     * @return the createdDate
     */
    @Override
    public Optional<LocalDateTime> getCreatedDate() {
        return this.createdDate == null ? Optional.empty() : Optional.of(this.createdDate);
    }


    /**
     * Returns the user who modified the entity lastly.
     *
     * @return the lastModifiedBy
     */
    @Override
    public Optional<String> getLastModifiedBy() {
        return Optional.ofNullable(this.lastModifiedBy);
    }


    /**
     * Returns the date of the last modification.
     *
     * @return the lastModifiedDate
     */
    @Override
    public Optional<LocalDateTime> getLastModifiedDate() {
        return this.lastModifiedDate == null ? Optional.empty() : Optional.of(this.lastModifiedDate);
    }

    @Override
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Override
    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    @Transient
    public boolean isNew() {
        return getId() == null;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (this == obj) {
            return true;
        } else if (!this.getClass().equals(ProxyUtils.getUserClass(obj))) {
            return false;
        } else {
            BaseEntity<?> that = (BaseEntity)obj;
            return this.getId() == null ? false : this.getId().equals(that.getId());
        }
    }

    public int hashCode() {
        int hashCode = 17;
        hashCode += this.getId() == null ? 0 : this.getId().hashCode() * 31;
        return hashCode;
    }
}
