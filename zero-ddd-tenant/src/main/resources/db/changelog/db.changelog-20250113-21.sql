--liquibase formatted sql

-- 给tenant_data_source表添加一个mode字段
ALTER TABLE tenant_data_source ADD COLUMN mode VARCHAR(20) DEFAULT 'SCHEMA';