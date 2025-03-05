package com.zjj.autoconfigure.component.tenant;


import lombok.Data;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月20日 14:26
 */
@Data
public class Tenant {
    private String tenantId;
    /***********************************
     * db模式
     * ********************************/
    private String db;
    private String password;

    /***********************************
     * schema模式
     * ********************************/
    private String schema;

    private Mode mode;


    public enum Mode {
        DATABASE,
        SCHEMA,
        COLUMN;

        public static Mode of(String s) {
            for (Mode mode : Mode.values()) {
                if (mode.name().equalsIgnoreCase(s)) {
                    return mode;
                }
            }
            return null;
        }
    }

    public Tenant() {}

    Tenant(String tenantId, String db, String password, String schema, Mode mode) {
        this.tenantId = tenantId;
        this.db = db;
        this.password = password;
        this.schema = schema;
        this.mode = mode;
    }

    public static TenantDbBuilder dbBuilder() {
        return new TenantDbBuilder();
    }
    public static TenantSchemaBuilder schemaBuilder() {
        return new TenantSchemaBuilder();
    }

    public static TenantBuilder builder() {
        return new TenantBuilder();
    }

    public static class TenantSchemaBuilder {
        private String tenantId;
        private String schema;

        TenantSchemaBuilder() {
        }

        public TenantSchemaBuilder tenantId(String tenantId) {
            this.tenantId = tenantId;
            return this;
        }

        public TenantSchemaBuilder schema(String schema) {
            this.schema = schema;
            return this;
        }

        public Tenant build() {
            return new Tenant(this.tenantId, null, null, schema, Mode.SCHEMA);
        }

        public String toString() {
            return "Tenant.TenantBuilder(tenantId=" + this.tenantId + ", schema=" + this.schema + ")";
        }

    }


    public static class TenantDbBuilder {
        private String tenantId;
        private String db;
        private String password;
        private String username;

        TenantDbBuilder() {
        }

        public TenantDbBuilder tenantId(String tenantId) {
            this.tenantId = tenantId;
            return this;
        }

        public TenantDbBuilder db(String db) {
            this.db = db;
            return this;
        }

        public TenantDbBuilder password(String password) {
            this.password = password;
            return this;
        }


        public Tenant build() {
            return new Tenant(this.tenantId, this.db, this.password, null, Mode.DATABASE);
        }

        public String toString() {
            return "Tenant.TenantBuilder(tenantId=" + this.tenantId + ", db=" + this.db + ", username=" + this.db + ", password=" + this.password+ ")";
        }
    }

    public static class TenantBuilder {
        private String tenantId;
        private String db;
        private String password;
        private String schema;
        private Mode mode;

        TenantBuilder() {
        }

        public TenantBuilder tenantId(String tenantId) {
            this.tenantId = tenantId;
            return this;
        }

        public TenantBuilder db(String db) {
            this.db = db;
            return this;
        }

        public TenantBuilder password(String password) {
            this.password = password;
            return this;
        }

        public TenantBuilder schema(String schema) {
            this.schema = schema;
            return this;
        }

        public TenantBuilder mode(String mode) {
            this.mode = Mode.of(mode);
            return this;
        }

        public TenantBuilder mode(Mode mode) {
            this.mode = mode;
            return this;
        }

        public Tenant build() {
            return new Tenant(this.tenantId, this.db, this.password, this.schema, this.mode);
        }

        public String toString() {
            return "Tenant.TenantBuilder(tenantId=" + this.tenantId + ", db=" + this.db + ", username=" + this.db + ", password=" + this.password + ", schema=" + this.schema + ")";
        }
    }
}
