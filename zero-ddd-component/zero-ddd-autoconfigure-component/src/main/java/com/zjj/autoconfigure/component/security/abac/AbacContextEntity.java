package com.zjj.autoconfigure.component.security.abac;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年06月10日 16:19
 */
@Data
public class AbacContextEntity implements Serializable, ContextEntity<List<AbacContextEntity.ObjectEntity>, AbacContextEntity.ObjectEntity, List<AbacContextEntity.ObjectEntity>> {

    private List<ObjectEntity> objectEntities;
    private ObjectEntity resultObject;
    private List<ObjectEntity> arguments;

    @Data
    public static class ObjectEntity implements Serializable {
        private String name;
        private Boolean isCollection = false;
        private String type;
        private Boolean isMap = false;
        private Boolean association = false;
        private String description;
        private List<ObjectAttribute> attributes;
    }

    @Data
    public static class ObjectAttribute implements Serializable {
        private String name;
        private String type;
        private String defaultValue;
        private ObjectEntity associationType;
        private Boolean association = false;
        private Boolean collection = false;
        private Boolean isMap = false;
        private String description;
    }
}
