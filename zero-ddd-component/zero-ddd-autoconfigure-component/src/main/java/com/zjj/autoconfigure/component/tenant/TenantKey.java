package com.zjj.autoconfigure.component.tenant;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年04月09日 15:44
 */
public interface TenantKey {

   default String getTenantKey() {
       String tenantId = TenantContextHolder.getTenantId() == null ? "" : TenantContextHolder.getTenantId() + ":";
//       String db =  TenantContextHolder.getDatabase() == null ? "" : TenantContextHolder.getDatabase() + ":";
//       String schema = TenantContextHolder.getSchema() == null ? "" : TenantContextHolder.getSchema() + ":";
       return tenantId;
   }
}
