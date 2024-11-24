package com.zjj.domain.component;

import com.zjj.bean.componenet.ApplicationContextHelper;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.util.ProxyUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.beans.BeanProperty;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月15日 21:16
 */
public abstract class Aggregate<ID> {


    public abstract ID getId();

    protected DomainEvent publishEvent(@NonNull DomainEvent event) {
        Assert.notNull(event, "Domain event must not be null");
        ApplicationContextHelper.publishEvent(Objects.requireNonNull(event));
        return event;
    }

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
