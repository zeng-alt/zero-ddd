--liquibase formatted sql


set client_min_messages = WARNING;
/*
 Navicat Premium Data Transfer

 Source Server         : pg
 Source Server Type    : PostgreSQL
 Source Server Version : 120001
 Source Host           : localhost:5432
 Source Catalog        : zero-ddd
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 120001
 File Encoding         : 65001

 Date: 14/05/2025 21:55:03
*/


-- ----------------------------
-- Sequence structure for main_expression_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "main_expression_id_seq";
CREATE SEQUENCE "main_expression_id_seq"
    INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for main_expression_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "main_expression_seq";
CREATE SEQUENCE "main_expression_seq"
    INCREMENT 50
MINVALUE  1
MAXVALUE 9223372036854775807
START 10
CACHE 1;

-- ----------------------------
-- Sequence structure for main_parameter_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "main_parameter_id_seq";
CREATE SEQUENCE "main_parameter_id_seq"
    INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for main_permission_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "main_permission_id_seq";
CREATE SEQUENCE "main_permission_id_seq"
    INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for main_permission_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "main_permission_seq";
CREATE SEQUENCE "main_permission_seq"
    INCREMENT 50
MINVALUE  1
MAXVALUE 9223372036854775807
START 10
CACHE 1;

-- ----------------------------
-- Sequence structure for main_policy_rule_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "main_policy_rule_id_seq";
CREATE SEQUENCE "main_policy_rule_id_seq"
    INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for main_resource_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "main_resource_seq";
CREATE SEQUENCE "main_resource_seq"
    INCREMENT 50
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for main_role_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "main_role_id_seq";
CREATE SEQUENCE "main_role_id_seq"
    INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for main_role_permission_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "main_role_permission_id_seq";
CREATE SEQUENCE "main_role_permission_id_seq"
    INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for main_role_permission_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "main_role_permission_seq";
CREATE SEQUENCE "main_role_permission_seq"
    INCREMENT 50
MINVALUE  1
MAXVALUE 9223372036854775807
START 10
CACHE 1;

-- ----------------------------
-- Sequence structure for main_role_precondition_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "main_role_precondition_id_seq";
CREATE SEQUENCE "main_role_precondition_id_seq"
    INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for main_role_precondition_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "main_role_precondition_seq";
CREATE SEQUENCE "main_role_precondition_seq"
    INCREMENT 50
MINVALUE  1
MAXVALUE 9223372036854775807
START 10
CACHE 1;

-- ----------------------------
-- Sequence structure for main_role_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "main_role_seq";
CREATE SEQUENCE "main_role_seq"
    INCREMENT 50
MINVALUE  1
MAXVALUE 9223372036854775807
START 10
CACHE 1;

-- ----------------------------
-- Sequence structure for main_user_expression_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "main_user_expression_id_seq";
CREATE SEQUENCE "main_user_expression_id_seq"
    INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for main_user_expression_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "main_user_expression_seq";
CREATE SEQUENCE "main_user_expression_seq"
    INCREMENT 50
MINVALUE  1
MAXVALUE 9223372036854775807
START 10
CACHE 1;

-- ----------------------------
-- Sequence structure for main_user_group_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "main_user_group_id_seq";
CREATE SEQUENCE "main_user_group_id_seq"
    INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for main_user_group_role_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "main_user_group_role_id_seq";
CREATE SEQUENCE "main_user_group_role_id_seq"
    INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for main_user_group_role_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "main_user_group_role_seq";
CREATE SEQUENCE "main_user_group_role_seq"
    INCREMENT 50
MINVALUE  1
MAXVALUE 9223372036854775807
START 10
CACHE 1;

-- ----------------------------
-- Sequence structure for main_user_group_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "main_user_group_seq";
CREATE SEQUENCE "main_user_group_seq"
    INCREMENT 50
MINVALUE  1
MAXVALUE 9223372036854775807
START 10
CACHE 1;

-- ----------------------------
-- Sequence structure for main_user_group_user_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "main_user_group_user_id_seq";
CREATE SEQUENCE "main_user_group_user_id_seq"
    INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for main_user_group_user_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "main_user_group_user_seq";
CREATE SEQUENCE "main_user_group_user_seq"
    INCREMENT 50
MINVALUE  1
MAXVALUE 9223372036854775807
START 10
CACHE 1;

-- ----------------------------
-- Sequence structure for main_user_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "main_user_id_seq";
CREATE SEQUENCE "main_user_id_seq"
    INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for main_user_resource_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "main_user_resource_id_seq";
CREATE SEQUENCE "main_user_resource_id_seq"
    INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for main_user_resource_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "main_user_resource_seq";
CREATE SEQUENCE "main_user_resource_seq"
    INCREMENT 50
MINVALUE  1
MAXVALUE 9223372036854775807
START 10
CACHE 1;

-- ----------------------------
-- Sequence structure for main_user_role_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "main_user_role_id_seq";
CREATE SEQUENCE "main_user_role_id_seq"
    INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for main_user_role_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "main_user_role_seq";
CREATE SEQUENCE "main_user_role_seq"
    INCREMENT 50
MINVALUE  1
MAXVALUE 9223372036854775807
START 10
CACHE 1;

-- ----------------------------
-- Sequence structure for main_user_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "main_user_seq";
CREATE SEQUENCE "main_user_seq"
    INCREMENT 50
MINVALUE  1
MAXVALUE 9223372036854775807
START 10
CACHE 1;

-- ----------------------------
-- Sequence structure for main_user_session_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "main_user_session_id_seq";
CREATE SEQUENCE "main_user_session_id_seq"
    INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for main_user_session_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "main_user_session_seq";
CREATE SEQUENCE "main_user_session_seq"
    INCREMENT 50
MINVALUE  1
MAXVALUE 9223372036854775807
START 10
CACHE 1;


DROP TABLE IF EXISTS "event_publication" CASCADE;
CREATE TABLE IF NOT EXISTS "event_publication"
(
    id               UUID NOT NULL,
    listener_id      TEXT NOT NULL,
    event_type       TEXT NOT NULL,
    serialized_event TEXT NOT NULL,
    publication_date TIMESTAMP WITH TIME ZONE NOT NULL,
    completion_date  TIMESTAMP WITH TIME ZONE,
    PRIMARY KEY (id)
);
CREATE INDEX IF NOT EXISTS event_publication_serialized_event_hash_idx ON "event_publication" USING hash(serialized_event);
CREATE INDEX IF NOT EXISTS event_publication_by_completion_date_idx ON "event_publication" (completion_date);

DROP TABLE IF EXISTS "event_publication_archive" CASCADE;
CREATE TABLE IF NOT EXISTS "event_publication_archive"
(
    id               UUID NOT NULL,
    listener_id      TEXT NOT NULL,
    event_type       TEXT NOT NULL,
    serialized_event TEXT NOT NULL,
    publication_date TIMESTAMP WITH TIME ZONE NOT NULL,
    completion_date  TIMESTAMP WITH TIME ZONE,
    PRIMARY KEY (id)
);
CREATE INDEX IF NOT EXISTS event_publication_archive_serialized_event_hash_idx ON "event_publication_archive" USING hash(serialized_event);
CREATE INDEX IF NOT EXISTS event_publication_archive_by_completion_date_idx ON "event_publication_archive" (completion_date);
-- ----------------------------
-- Table structure for main_expression
-- ----------------------------
DROP TABLE IF EXISTS "main_expression" CASCADE;
CREATE TABLE "main_expression" (
                                   "created_date" timestamp(6),
                                   "id" int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY (
                                       INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
),
                                   "last_modified_date" timestamp(6),
                                   "resource_id" int8,
                                   "created_by" varchar(255) COLLATE "pg_catalog"."default",
                                   "last_modified_by" varchar(255) COLLATE "pg_catalog"."default",
                                   "tenant_by" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Records of main_expression
-- ----------------------------

-- ----------------------------
-- Table structure for main_graphql_resource
-- ----------------------------
DROP TABLE IF EXISTS "main_graphql_resource" CASCADE;
CREATE TABLE "main_graphql_resource" (
                                         "id" int8 NOT NULL,
                                         "operation" varchar(255) COLLATE "pg_catalog"."default",
                                         "function_name" varchar(255) COLLATE "pg_catalog"."default",
                                         "uri" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of main_graphql_resource
-- ----------------------------
INSERT INTO "main_graphql_resource" VALUES (12, 'QUERY', 'queryTenant', '/tenant/graphql');
INSERT INTO "main_graphql_resource" VALUES (13, 'QUERY', 'findParameter', '/tenant/graphql');

-- ----------------------------
-- Table structure for main_menu_resource
-- ----------------------------
DROP TABLE IF EXISTS "main_menu_resource" CASCADE;
CREATE TABLE "main_menu_resource" (
                                      "enable" bool,
                                      "resource_order" int4,
                                      "show" bool,
                                      "id" int8 NOT NULL,
                                      "parent_id" int8,
                                      "code" varchar(255) COLLATE "pg_catalog"."default",
                                      "component" varchar(255) COLLATE "pg_catalog"."default",
                                      "description" varchar(255) COLLATE "pg_catalog"."default",
                                      "icon" varchar(255) COLLATE "pg_catalog"."default",
                                      "keep_alive" varchar(255) COLLATE "pg_catalog"."default",
                                      "layout" varchar(255) COLLATE "pg_catalog"."default",
                                      "method" varchar(255) COLLATE "pg_catalog"."default",
                                      "name" varchar(255) COLLATE "pg_catalog"."default",
                                      "path" varchar(255) COLLATE "pg_catalog"."default",
                                      "redirect" varchar(255) COLLATE "pg_catalog"."default",
                                      "type" varchar(255) COLLATE "pg_catalog"."default",
                                      "path_variables" varchar(255) COLLATE "pg_catalog"."default",
                                      "menu_style" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of main_menu_resource
-- ----------------------------
INSERT INTO "main_menu_resource" VALUES ('t', 2, 't', 10, 4, NULL, '/src/views/pms/user/index.vue', NULL, 'i-fe:user', NULL, NULL, NULL, '用户管理', '/pms/user', NULL, 'MENU', NULL, NULL);
INSERT INTO "main_menu_resource" VALUES ('t', 7, 'f', 1, 4, NULL, NULL, NULL, NULL, NULL, NULL, 'GET', '查看登录用户', '/main/v1/user/detail', NULL, 'BUTTON', NULL, NULL);
INSERT INTO "main_menu_resource" VALUES ('t', 9, 'f', 3, 4, NULL, NULL, NULL, NULL, NULL, NULL, 'GET', '根据id查看用户', '/main/v1/user/detail/{id}', NULL, 'BUTTON', NULL, NULL);
INSERT INTO "main_menu_resource" VALUES ('t', 8, 'f', 2, 4, NULL, NULL, NULL, NULL, NULL, NULL, 'POST', 'graphql管理', '/main/graphql', NULL, 'BUTTON', NULL, NULL);
INSERT INTO "main_menu_resource" VALUES ('t', 1, 't', 8, 4, NULL, '/src/views/pms/resource/index.vue', NULL, 'i-carbon:Http', NULL, NULL, NULL, 'HTTP资源管理', '/pms/resource', NULL, 'MENU', NULL, NULL);
INSERT INTO "main_menu_resource" VALUES ('t', 1, 'f', 9, 4, NULL, NULL, NULL, 'i-fe:list', NULL, NULL, 'GET', NULL, '/main/v1/menu/resource/tree/all', NULL, 'BUTTON', NULL, NULL);
INSERT INTO "main_menu_resource" VALUES ('t', 3, 't', 11, 4, NULL, '/src/views/pms/resource/index.vue', '用于管理角色', 'i-ali:role', NULL, NULL, NULL, '角色管理', '/pms/resource', NULL, 'MENU', NULL, NULL);
INSERT INTO "main_menu_resource" VALUES ('t', 1, 't', 12, 4, NULL, '/src/views/pms/resource/index.vue', '用于管理graphql资源', 'i-carbon:graphql', NULL, NULL, NULL, 'GRAPHQL管理', '/pms/resource', NULL, 'MENU', NULL, NULL);
INSERT INTO "main_menu_resource" VALUES ('t', 3, 't', 6, NULL, NULL, NULL, NULL, 'i-fe:grid', NULL, NULL, NULL, '基础功能', '/main/v1/profile', NULL, 'MENU', NULL, NULL);
INSERT INTO "main_menu_resource" VALUES ('t', 1, 't', 5, NULL, NULL, NULL, NULL, 'i-fe:grid', NULL, NULL, NULL, '业务示例', NULL, NULL, 'MENU', NULL, NULL);
INSERT INTO "main_menu_resource" VALUES ('t', 4, 't', 13, 4, NULL, '/src/views/pms/parameter/index.vue', '用于管理系统参数', 'i-ali:SystemParameter', NULL, NULL, NULL, '系统参数管理', '/pms/parameter', NULL, 'MENU', NULL, NULL);
INSERT INTO "main_menu_resource" VALUES ('t', 9, 'f', 7, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'GET', NULL, '/main/v1/menu/resource/tree', NULL, 'BUTTON', NULL, NULL);
INSERT INTO "main_menu_resource" VALUES ('t', 2, 't', 4, NULL, NULL, NULL, NULL, 'i-fe:grid', NULL, NULL, NULL, '系统管理', NULL, NULL, 'MENU', NULL, 'list');

-- ----------------------------
-- Table structure for main_parameter
-- ----------------------------
DROP TABLE IF EXISTS "main_parameter" CASCADE;
CREATE TABLE "main_parameter" (
                                  "created_date" timestamp(6),
                                  "id" int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY (
                                      INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
),
                                  "last_modified_date" timestamp(6),
                                  "created_by" varchar(255) COLLATE "pg_catalog"."default",
                                  "last_modified_by" varchar(255) COLLATE "pg_catalog"."default",
                                  "parameter_key" varchar(255) COLLATE "pg_catalog"."default",
                                  "parameter_name" varchar(255) COLLATE "pg_catalog"."default",
                                  "parameter_type" varchar(255) COLLATE "pg_catalog"."default",
                                  "parameter_value" varchar(255) COLLATE "pg_catalog"."default",
                                  "remark" varchar(255) COLLATE "pg_catalog"."default",
                                  "tenant_by" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Records of main_parameter
-- ----------------------------
INSERT INTO "main_parameter" VALUES (NULL, NULL, NULL, NULL, NULL, 'captcha', '验证码', 'Boolean', 'false', '是否开启验证码', '${tenantName}');
INSERT INTO "main_parameter" VALUES (NULL, NULL, '2025-05-14 15:37:30.62984', NULL, 'superAdmin', 'tenant', '租户', 'Boolean', 'true', '是否开启租户', '${tenantName}');

-- ----------------------------
-- Table structure for main_permission
-- ----------------------------
DROP TABLE IF EXISTS "main_permission" CASCADE;
CREATE TABLE "main_permission" (
                                   "created_date" timestamp(6),
                                   "id" int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY (
                                       INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
),
                                   "last_modified_date" timestamp(6),
                                   "resource_id" int8,
                                   "code" varchar(255) COLLATE "pg_catalog"."default",
                                   "created_by" varchar(255) COLLATE "pg_catalog"."default",
                                   "last_modified_by" varchar(255) COLLATE "pg_catalog"."default",
                                   "tenant_by" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Records of main_permission
-- ----------------------------
INSERT INTO "main_permission" VALUES ('2025-04-03 17:38:13.242011', 1, '2025-04-03 17:38:13.242011', 1, 'get:user:detail', NULL, NULL, '${tenantName}');
INSERT INTO "main_permission" VALUES ('2025-04-03 17:38:13.269283', 2, '2025-04-03 17:38:13.269283', 2, 'post:main:graphql', NULL, NULL, '${tenantName}');
INSERT INTO "main_permission" VALUES (NULL, 3, NULL, 3, 'get:user:detail:id', NULL, NULL, '${tenantName}');
INSERT INTO "main_permission" VALUES (NULL, 4, NULL, 4, 'SysMgt', NULL, NULL, '${tenantName}');
INSERT INTO "main_permission" VALUES (NULL, 5, NULL, 5, 'Demo', NULL, NULL, '${tenantName}');
INSERT INTO "main_permission" VALUES (NULL, 7, NULL, 6, 'Base', NULL, NULL, '${tenantName}');
INSERT INTO "main_permission" VALUES (NULL, 8, NULL, 7, 'get:menu:resource:tree', NULL, NULL, '${tenantName}');
INSERT INTO "main_permission" VALUES (NULL, 9, NULL, 8, 'show:pms:resource', NULL, NULL, '${tenantName}');
INSERT INTO "main_permission" VALUES (NULL, 10, NULL, 9, 'show:pms:resource:tree:all', NULL, NULL, '${tenantName}');
INSERT INTO "main_permission" VALUES (NULL, 11, NULL, 12, 'query:tenant', NULL, NULL, '${tenantName}');
INSERT INTO "main_permission" VALUES (NULL, 14, NULL, 13, 'find:parameter', NULL, NULL, '${tenantName}');

-- ----------------------------
-- Table structure for main_policy_rule
-- ----------------------------
DROP TABLE IF EXISTS "main_policy_rule" CASCADE;
CREATE TABLE "main_policy_rule" (
                                    "id" int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY (
                                        INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
),
                                    "created_by" varchar(255) COLLATE "pg_catalog"."default",
                                    "created_date" timestamp(6),
                                    "last_modified_by" varchar(255) COLLATE "pg_catalog"."default",
                                    "last_modified_date" timestamp(6),
                                    "condition" varchar(255) COLLATE "pg_catalog"."default",
                                    "description" varchar(255) COLLATE "pg_catalog"."default",
                                    "name" varchar(255) COLLATE "pg_catalog"."default",
                                    "pre_auth" bool,
                                    "tenant_by" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
                                    "resource_id" int8
)
;

-- ----------------------------
-- Records of main_policy_rule
-- ----------------------------
INSERT INTO "main_policy_rule" VALUES (1, NULL, NULL, NULL, NULL, 'true', 'TRUE_RULE', 'TRUE_RULE', 't', '${tenantName}', 1);
INSERT INTO "main_policy_rule" VALUES (2, NULL, NULL, NULL, NULL, 'true', 'TRUE_RULE', 'TRUE_RULE', 'f', '${tenantName}', 1);
INSERT INTO "main_policy_rule" VALUES (4, NULL, NULL, NULL, NULL, 'true', 'TRUE_RULE', 'TRUE_RULE', 'f', '${tenantName}', 7);
INSERT INTO "main_policy_rule" VALUES (3, NULL, NULL, NULL, NULL, 'target[testVo].username == subject.username', 'TRUE_RULE', 'TRUE_RULE', 't', '${tenantName}', 7);

-- ----------------------------
-- Table structure for main_resource
-- ----------------------------
DROP TABLE IF EXISTS "main_resource" CASCADE;
CREATE TABLE "main_resource" (
                                 "created_date" timestamp(6),
                                 "id" int8 NOT NULL,
                                 "last_modified_date" timestamp(6),
                                 "resource_type" varchar(31) COLLATE "pg_catalog"."default" NOT NULL,
                                 "created_by" varchar(255) COLLATE "pg_catalog"."default",
                                 "last_modified_by" varchar(255) COLLATE "pg_catalog"."default",
                                 "tenant_by" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
                                 "code" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of main_resource
-- ----------------------------
INSERT INTO "main_resource" VALUES (NULL, 4, NULL, 'HTTP', NULL, NULL, '${tenantName}', 'SysMgt');
INSERT INTO "main_resource" VALUES (NULL, 5, NULL, 'HTTP', NULL, NULL, '${tenantName}', 'Demo');
INSERT INTO "main_resource" VALUES (NULL, 6, NULL, 'HTTP', NULL, NULL, '${tenantName}', 'Base');
INSERT INTO "main_resource" VALUES (NULL, 8, NULL, 'HTTP', NULL, NULL, '${tenantName}', 'Resource_Mgt');
INSERT INTO "main_resource" VALUES ('2025-04-03 17:38:13.165751', 1, '2025-04-03 17:38:13.165751', 'HTTP', NULL, NULL, '${tenantName}', 'GetUser');
INSERT INTO "main_resource" VALUES (NULL, 7, NULL, 'HTTP', NULL, NULL, '${tenantName}', 'GetMenuTree');
INSERT INTO "main_resource" VALUES (NULL, 3, NULL, 'HTTP', NULL, NULL, '${tenantName}', 'GetUserById');
INSERT INTO "main_resource" VALUES ('2025-04-03 17:38:13.238804', 2, '2025-04-03 17:38:13.238804', 'HTTP', NULL, NULL, '${tenantName}', 'MainGraphql');
INSERT INTO "main_resource" VALUES ('2025-04-03 17:38:13.238804', 9, '2025-04-03 17:38:13.238804', 'HTTP', NULL, NULL, '${tenantName}', 'GetHttpResourceAll');
INSERT INTO "main_resource" VALUES ('2025-04-03 17:38:13.238804', 10, '2025-04-03 17:38:13.238804', 'HTTP', NULL, NULL, '${tenantName}', 'UserManager');
INSERT INTO "main_resource" VALUES ('2025-04-03 17:38:13.238804', 11, '2025-04-03 17:38:13.238804', 'HTTP', NULL, NULL, '${tenantName}', 'RoleManager');
INSERT INTO "main_resource" VALUES ('2025-04-03 17:38:13.238804', 12, '2025-04-03 17:38:13.238804', 'GRAPHQL', NULL, NULL, '${tenantName}', 'queryTenant');
INSERT INTO "main_resource" VALUES ('2025-04-03 17:38:13.238804', 13, '2025-04-03 17:38:13.238804', 'GRAPHQL', NULL, NULL, '${tenantName}', 'findParameter');

-- ----------------------------
-- Table structure for main_role
-- ----------------------------
DROP TABLE IF EXISTS "main_role" CASCADE;
CREATE TABLE "main_role" (
                             "deleted" int4,
                             "enable" bool,
                             "role_sort" int4,
                             "created_date" timestamp(6),
                             "id" int8 NOT NULL GENERATED ALWAYS AS IDENTITY (
                                 INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
),
                             "last_modified_date" timestamp(6),
                             "code" varchar(255) COLLATE "pg_catalog"."default",
                             "created_by" varchar(255) COLLATE "pg_catalog"."default",
                             "last_modified_by" varchar(255) COLLATE "pg_catalog"."default",
                             "name" varchar(255) COLLATE "pg_catalog"."default",
                             "tenant_by" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Records of main_role
-- ----------------------------
INSERT INTO "main_role" VALUES (0, 't', NULL, NULL, DEFAULT, NULL, 'Admin', NULL, NULL, '管理员', '${tenantName}');

-- ----------------------------
-- Table structure for main_role_permission
-- ----------------------------
DROP TABLE IF EXISTS "main_role_permission" CASCADE;
CREATE TABLE "main_role_permission" (
                                        "created_date" timestamp(6),
                                        "id" int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY (
                                            INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
),
                                        "last_modified_date" timestamp(6),
                                        "permission_id" int8,
                                        "role_id" int8,
                                        "created_by" varchar(255) COLLATE "pg_catalog"."default",
                                        "last_modified_by" varchar(255) COLLATE "pg_catalog"."default",
                                        "tenant_by" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Records of main_role_permission
-- ----------------------------
INSERT INTO "main_role_permission" VALUES (NULL, 1, NULL, 1, 1, NULL, NULL, '${tenantName}');
INSERT INTO "main_role_permission" VALUES (NULL, 2, NULL, 2, 1, NULL, NULL, '${tenantName}');
INSERT INTO "main_role_permission" VALUES (NULL, 4, NULL, 3, 1, NULL, NULL, '${tenantName}');
INSERT INTO "main_role_permission" VALUES (NULL, 5, NULL, 4, 1, NULL, NULL, '${tenantName}');
INSERT INTO "main_role_permission" VALUES (NULL, 6, NULL, 5, 1, NULL, NULL, '${tenantName}');
INSERT INTO "main_role_permission" VALUES (NULL, 8, NULL, 7, 1, '', NULL, '${tenantName}');
INSERT INTO "main_role_permission" VALUES (NULL, 9, NULL, 8, 1, NULL, NULL, '${tenantName}');
INSERT INTO "main_role_permission" VALUES (NULL, 10, NULL, 9, 1, NULL, NULL, '${tenantName}');
INSERT INTO "main_role_permission" VALUES (NULL, 11, NULL, 10, 1, NULL, NULL, '${tenantName}');
INSERT INTO "main_role_permission" VALUES (NULL, 13, NULL, 14, 1, NULL, NULL, '${tenantName}');

-- ----------------------------
-- Table structure for main_role_precondition
-- ----------------------------
DROP TABLE IF EXISTS "main_role_precondition" CASCADE;
CREATE TABLE "main_role_precondition" (
                                          "created_date" timestamp(6),
                                          "id" int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY (
                                              INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
),
                                          "last_modified_date" timestamp(6),
                                          "role_id" int8,
                                          "created_by" varchar(255) COLLATE "pg_catalog"."default",
                                          "last_modified_by" varchar(255) COLLATE "pg_catalog"."default",
                                          "tenant_by" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Records of main_role_precondition
-- ----------------------------

-- ----------------------------
-- Table structure for main_user
-- ----------------------------
DROP TABLE IF EXISTS "main_user" CASCADE;
CREATE TABLE "main_user" (
                             "deleted" int4,
                             "created_date" timestamp(6),
                             "id" int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY (
                                 INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
),
                             "last_modified_date" timestamp(6),
                             "avatar" varchar(255) COLLATE "pg_catalog"."default",
                             "created_by" varchar(255) COLLATE "pg_catalog"."default",
                             "email" varchar(255) COLLATE "pg_catalog"."default",
                             "gender" varchar(255) COLLATE "pg_catalog"."default",
                             "last_modified_by" varchar(255) COLLATE "pg_catalog"."default",
                             "nick_name" varchar(255) COLLATE "pg_catalog"."default",
                             "password" varchar(255) COLLATE "pg_catalog"."default",
                             "phone_number" varchar(255) COLLATE "pg_catalog"."default",
                             "status" varchar(255) COLLATE "pg_catalog"."default",
                             "tenant_by" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
                             "username" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of main_user
-- ----------------------------
INSERT INTO "main_user" VALUES (0, NULL, 11, NULL, NULL, NULL, NULL, '1', NULL, 'Admin', '{bcrypt}$2a$10$8xbX8CngT8AO8froAt8Ky.VDSzhxSFpIZlp4aO.XKnX3g3tGbj/QO', NULL, '1', '${tenantName}', 'admin6');
INSERT INTO "main_user" VALUES (0, NULL, 13, NULL, NULL, NULL, NULL, '1', NULL, 'Admin', '{bcrypt}$2a$10$8xbX8CngT8AO8froAt8Ky.VDSzhxSFpIZlp4aO.XKnX3g3tGbj/QO', NULL, '1', '${tenantName}', 'admin8');
INSERT INTO "main_user" VALUES (0, NULL, 12, NULL, NULL, NULL, NULL, '2', NULL, 'Admin', '{bcrypt}$2a$10$8xbX8CngT8AO8froAt8Ky.VDSzhxSFpIZlp4aO.XKnX3g3tGbj/QO', NULL, '1', '${tenantName}', 'admin7');
INSERT INTO "main_user" VALUES (0, NULL, 14, NULL, NULL, NULL, NULL, '1', NULL, 'Admin', '{bcrypt}$2a$10$8xbX8CngT8AO8froAt8Ky.VDSzhxSFpIZlp4aO.XKnX3g3tGbj/QO', NULL, '1', '${tenantName}', 'admin9');
INSERT INTO "main_user" VALUES (0, NULL, 15, NULL, NULL, NULL, NULL, '1', NULL, 'Admin', '{bcrypt}$2a$10$8xbX8CngT8AO8froAt8Ky.VDSzhxSFpIZlp4aO.XKnX3g3tGbj/QO', NULL, '1', '${tenantName}', 'admin10');
INSERT INTO "main_user" VALUES (0, NULL, 10, NULL, NULL, NULL, NULL, '2', NULL, 'Admin', '{bcrypt}$2a$10$8xbX8CngT8AO8froAt8Ky.VDSzhxSFpIZlp4aO.XKnX3g3tGbj/QO', NULL, '1', '${tenantName}', 'admin5');
INSERT INTO "main_user" VALUES (0, NULL, 9, NULL, NULL, NULL, NULL, '1', NULL, 'Admin', '{bcrypt}$2a$10$8xbX8CngT8AO8froAt8Ky.VDSzhxSFpIZlp4aO.XKnX3g3tGbj/QO', NULL, '1', '${tenantName}', 'admin4');
INSERT INTO "main_user" VALUES (0, NULL, 8, NULL, NULL, NULL, NULL, '1', NULL, 'Admin', '{bcrypt}$2a$10$8xbX8CngT8AO8froAt8Ky.VDSzhxSFpIZlp4aO.XKnX3g3tGbj/QO', NULL, '1', '${tenantName}', 'admin3');
INSERT INTO "main_user" VALUES (0, NULL, 7, NULL, NULL, NULL, NULL, '2', NULL, 'Admin', '{bcrypt}$2a$10$8xbX8CngT8AO8froAt8Ky.VDSzhxSFpIZlp4aO.XKnX3g3tGbj/QO', NULL, '1', '${tenantName}', 'admin2');
INSERT INTO "main_user" VALUES (0, NULL, 5, NULL, NULL, NULL, 'zeng@163.com', '1', NULL, '超级管理员', '{bcrypt}$2a$10$8xbX8CngT8AO8froAt8Ky.VDSzhxSFpIZlp4aO.XKnX3g3tGbj/QO', NULL, '1', '${tenantName}', 'superAdmin');
INSERT INTO "main_user" VALUES (0, NULL, 6, NULL, NULL, NULL, NULL, '1', NULL, 'Admin', '{bcrypt}$2a$10$8xbX8CngT8AO8froAt8Ky.VDSzhxSFpIZlp4aO.XKnX3g3tGbj/QO', NULL, '1', '${tenantName}', 'admin1');
INSERT INTO "main_user" VALUES (0, NULL, 1, NULL, NULL, NULL, NULL, '1', NULL, 'Admin', '{bcrypt}$2a$10$8xbX8CngT8AO8froAt8Ky.VDSzhxSFpIZlp4aO.XKnX3g3tGbj/QO', NULL, '1', '${tenantName}', 'admin');

-- ----------------------------
-- Table structure for main_user_expression
-- ----------------------------
DROP TABLE IF EXISTS "main_user_expression" CASCADE;
CREATE TABLE "main_user_expression" (
                                        "created_date" timestamp(6),
                                        "id" int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY (
                                            INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
),
                                        "last_modified_date" timestamp(6),
                                        "user_resource_id" int8,
                                        "created_by" varchar(255) COLLATE "pg_catalog"."default",
                                        "last_modified_by" varchar(255) COLLATE "pg_catalog"."default",
                                        "tenant_by" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Records of main_user_expression
-- ----------------------------

-- ----------------------------
-- Table structure for main_user_group
-- ----------------------------
DROP TABLE IF EXISTS "main_user_group" CASCADE;
CREATE TABLE "main_user_group" (
                                   "created_date" timestamp(6),
                                   "id" int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY (
                                       INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
),
                                   "last_modified_date" timestamp(6),
                                   "created_by" varchar(255) COLLATE "pg_catalog"."default",
                                   "last_modified_by" varchar(255) COLLATE "pg_catalog"."default",
                                   "tenant_by" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Records of main_user_group
-- ----------------------------

-- ----------------------------
-- Table structure for main_user_group_role
-- ----------------------------
DROP TABLE IF EXISTS "main_user_group_role" CASCADE;
CREATE TABLE "main_user_group_role" (
                                        "created_date" timestamp(6),
                                        "id" int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY (
                                            INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
),
                                        "last_modified_date" timestamp(6),
                                        "role_id" int8,
                                        "user_group_id" int8,
                                        "created_by" varchar(255) COLLATE "pg_catalog"."default",
                                        "last_modified_by" varchar(255) COLLATE "pg_catalog"."default",
                                        "tenant_by" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Records of main_user_group_role
-- ----------------------------

-- ----------------------------
-- Table structure for main_user_group_user
-- ----------------------------
DROP TABLE IF EXISTS "main_user_group_user" CASCADE;
CREATE TABLE "main_user_group_user" (
                                        "created_date" timestamp(6),
                                        "id" int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY (
                                            INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
),
                                        "last_modified_date" timestamp(6),
                                        "user_group_user_id" int8,
                                        "user_id" int8,
                                        "created_by" varchar(255) COLLATE "pg_catalog"."default",
                                        "last_modified_by" varchar(255) COLLATE "pg_catalog"."default",
                                        "tenant_by" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Records of main_user_group_user
-- ----------------------------

-- ----------------------------
-- Table structure for main_user_resource
-- ----------------------------
DROP TABLE IF EXISTS "main_user_resource" CASCADE;
CREATE TABLE "main_user_resource" (
                                      "created_date" timestamp(6),
                                      "id" int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY (
                                          INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
),
                                      "last_modified_date" timestamp(6),
                                      "user_id" int8,
                                      "created_by" varchar(255) COLLATE "pg_catalog"."default",
                                      "last_modified_by" varchar(255) COLLATE "pg_catalog"."default",
                                      "tenant_by" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Records of main_user_resource
-- ----------------------------

-- ----------------------------
-- Table structure for main_user_role
-- ----------------------------
DROP TABLE IF EXISTS "main_user_role" CASCADE;
CREATE TABLE "main_user_role" (
                                  "created_date" timestamp(6),
                                  "id" int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY (
                                      INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
),
                                  "last_modified_date" timestamp(6),
                                  "role_id" int8,
                                  "user_id" int8,
                                  "created_by" varchar(255) COLLATE "pg_catalog"."default",
                                  "last_modified_by" varchar(255) COLLATE "pg_catalog"."default",
                                  "tenant_by" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Records of main_user_role
-- ----------------------------
INSERT INTO "main_user_role" VALUES (NULL, 1, NULL, 1, 1, NULL, NULL, '${tenantName}');

-- ----------------------------
-- Table structure for main_user_session
-- ----------------------------
DROP TABLE IF EXISTS "main_user_session" CASCADE;
CREATE TABLE "main_user_session" (
                                     "created_date" timestamp(6),
                                     "id" int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY (
                                         INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
),
                                     "last_modified_date" timestamp(6),
                                     "created_by" varchar(255) COLLATE "pg_catalog"."default",
                                     "last_modified_by" varchar(255) COLLATE "pg_catalog"."default",
                                     "tenant_by" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
                                     "role_id" int8,
                                     "user_id" int8
)
;

-- ----------------------------
-- Records of main_user_session
-- ----------------------------

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "main_expression_id_seq"
    OWNED BY "main_expression"."id";
SELECT setval('"main_expression_id_seq"', 2, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"main_expression_seq"', 60, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "main_parameter_id_seq"
    OWNED BY "main_parameter"."id";
SELECT setval('"main_parameter_id_seq"', 7, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "main_permission_id_seq"
    OWNED BY "main_permission"."id";
SELECT setval('"main_permission_id_seq"', 15, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"main_permission_seq"', 60, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "main_policy_rule_id_seq"
    OWNED BY "main_policy_rule"."id";
SELECT setval('"main_policy_rule_id_seq"', 2, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"main_resource_seq"', 101, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "main_role_id_seq"
    OWNED BY "main_role"."id";
SELECT setval('"main_role_id_seq"', 2, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "main_role_permission_id_seq"
    OWNED BY "main_role_permission"."id";
SELECT setval('"main_role_permission_id_seq"', 14, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"main_role_permission_seq"', 60, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "main_role_precondition_id_seq"
    OWNED BY "main_role_precondition"."id";
SELECT setval('"main_role_precondition_id_seq"', 2, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"main_role_precondition_seq"', 60, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"main_role_seq"', 60, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "main_user_expression_id_seq"
    OWNED BY "main_user_expression"."id";
SELECT setval('"main_user_expression_id_seq"', 2, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"main_user_expression_seq"', 60, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "main_user_group_id_seq"
    OWNED BY "main_user_group"."id";
SELECT setval('"main_user_group_id_seq"', 2, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "main_user_group_role_id_seq"
    OWNED BY "main_user_group_role"."id";
SELECT setval('"main_user_group_role_id_seq"', 2, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"main_user_group_role_seq"', 60, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"main_user_group_seq"', 60, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "main_user_group_user_id_seq"
    OWNED BY "main_user_group_user"."id";
SELECT setval('"main_user_group_user_id_seq"', 2, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"main_user_group_user_seq"', 60, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "main_user_id_seq"
    OWNED BY "main_user"."id";
SELECT setval('"main_user_id_seq"', 16, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "main_user_resource_id_seq"
    OWNED BY "main_user_resource"."id";
SELECT setval('"main_user_resource_id_seq"', 2, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"main_user_resource_seq"', 60, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "main_user_role_id_seq"
    OWNED BY "main_user_role"."id";
SELECT setval('"main_user_role_id_seq"', 2, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"main_user_role_seq"', 60, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"main_user_seq"', 60, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "main_user_session_id_seq"
    OWNED BY "main_user_session"."id";
SELECT setval('"main_user_session_id_seq"', 2, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"main_user_session_seq"', 60, false);

-- ----------------------------
-- Primary Key structure for table main_expression
-- ----------------------------
ALTER TABLE "main_expression" ADD CONSTRAINT "main_expression_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table main_graphql_resource
-- ----------------------------
ALTER TABLE "main_graphql_resource" ADD CONSTRAINT "main_graphql_resource_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table main_menu_resource
-- ----------------------------
ALTER TABLE "main_menu_resource" ADD CONSTRAINT "main_menu_resource_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table main_parameter
-- ----------------------------
ALTER TABLE "main_parameter" ADD CONSTRAINT "main_parameter_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table main_permission
-- ----------------------------
ALTER TABLE "main_permission" ADD CONSTRAINT "main_permission_resource_id_key" UNIQUE ("resource_id");

-- ----------------------------
-- Primary Key structure for table main_permission
-- ----------------------------
ALTER TABLE "main_permission" ADD CONSTRAINT "main_permission_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table main_policy_rule
-- ----------------------------
ALTER TABLE "main_policy_rule" ADD CONSTRAINT "main_policy_rule_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table main_resource
-- ----------------------------
ALTER TABLE "main_resource" ADD CONSTRAINT "main_resource_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table main_role
-- ----------------------------
ALTER TABLE "main_role" ADD CONSTRAINT "main_role_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table main_role_permission
-- ----------------------------
ALTER TABLE "main_role_permission" ADD CONSTRAINT "main_role_permission_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table main_role_precondition
-- ----------------------------
ALTER TABLE "main_role_precondition" ADD CONSTRAINT "main_role_precondition_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table main_user
-- ----------------------------
ALTER TABLE "main_user" ADD CONSTRAINT "main_user_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table main_user_expression
-- ----------------------------
ALTER TABLE "main_user_expression" ADD CONSTRAINT "main_user_expression_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table main_user_group
-- ----------------------------
ALTER TABLE "main_user_group" ADD CONSTRAINT "main_user_group_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table main_user_group_role
-- ----------------------------
ALTER TABLE "main_user_group_role" ADD CONSTRAINT "main_user_group_role_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table main_user_group_user
-- ----------------------------
ALTER TABLE "main_user_group_user" ADD CONSTRAINT "main_user_group_user_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table main_user_resource
-- ----------------------------
ALTER TABLE "main_user_resource" ADD CONSTRAINT "main_user_resource_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table main_user_role
-- ----------------------------
ALTER TABLE "main_user_role" ADD CONSTRAINT "main_user_role_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table main_user_session
-- ----------------------------
ALTER TABLE "main_user_session" ADD CONSTRAINT "ukk25vyph2tnlep8lwbv35t6akl" UNIQUE ("user_id");

-- ----------------------------
-- Primary Key structure for table main_user_session
-- ----------------------------
ALTER TABLE "main_user_session" ADD CONSTRAINT "main_user_session_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Foreign Keys structure for table main_expression
-- ----------------------------
ALTER TABLE "main_expression" ADD CONSTRAINT "fkctm6ra2vx8wonfimyyf43tu35" FOREIGN KEY ("resource_id") REFERENCES "main_resource" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table main_graphql_resource
-- ----------------------------
ALTER TABLE "main_graphql_resource" ADD CONSTRAINT "fkba3t1qhalxs85xw744xhfkby0" FOREIGN KEY ("id") REFERENCES "main_resource" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table main_menu_resource
-- ----------------------------
ALTER TABLE "main_menu_resource" ADD CONSTRAINT "fk7o9nu5t5if0l5srsrpn6kkwi0" FOREIGN KEY ("id") REFERENCES "main_resource" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "main_menu_resource" ADD CONSTRAINT "fknf9umup3ovmj8d4wxios052ol" FOREIGN KEY ("parent_id") REFERENCES "main_menu_resource" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table main_permission
-- ----------------------------
ALTER TABLE "main_permission" ADD CONSTRAINT "fkknpo5mbi3jse1kmmgttpq3ik3" FOREIGN KEY ("resource_id") REFERENCES "main_resource" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table main_policy_rule
-- ----------------------------
ALTER TABLE "main_policy_rule" ADD CONSTRAINT "fkmr56ga0tffiekbxyn85sbifds" FOREIGN KEY ("resource_id") REFERENCES "main_resource" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table main_role_permission
-- ----------------------------
ALTER TABLE "main_role_permission" ADD CONSTRAINT "fk4asijil0617ps9gpyhthj65l" FOREIGN KEY ("role_id") REFERENCES "main_role" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "main_role_permission" ADD CONSTRAINT "fk6xefdd10oy6ts32yh5i6p63xx" FOREIGN KEY ("permission_id") REFERENCES "main_permission" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table main_role_precondition
-- ----------------------------
ALTER TABLE "main_role_precondition" ADD CONSTRAINT "fkr0hofiv39ejgje1jj2f8aqa0x" FOREIGN KEY ("role_id") REFERENCES "main_role" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table main_user_expression
-- ----------------------------
ALTER TABLE "main_user_expression" ADD CONSTRAINT "fkdaece98rnvyyciv9mf6lymrob" FOREIGN KEY ("user_resource_id") REFERENCES "main_user_resource" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table main_user_group_role
-- ----------------------------
ALTER TABLE "main_user_group_role" ADD CONSTRAINT "fka7p2kin11wimpkjdvvu2c29ot" FOREIGN KEY ("user_group_id") REFERENCES "main_user_group" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "main_user_group_role" ADD CONSTRAINT "fkba52gdf7iis4ujq39g7ritlup" FOREIGN KEY ("role_id") REFERENCES "main_role" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table main_user_group_user
-- ----------------------------
ALTER TABLE "main_user_group_user" ADD CONSTRAINT "fk5sbc9pkxa4oqdpp2adktkc8u7" FOREIGN KEY ("user_group_user_id") REFERENCES "main_user_group" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "main_user_group_user" ADD CONSTRAINT "fknctduxmn3df72yi5ixsf8jgdm" FOREIGN KEY ("user_id") REFERENCES "main_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table main_user_resource
-- ----------------------------
ALTER TABLE "main_user_resource" ADD CONSTRAINT "fkc2vhxjnijdiarqfe9p7pmp2ui" FOREIGN KEY ("user_id") REFERENCES "main_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table main_user_role
-- ----------------------------
ALTER TABLE "main_user_role" ADD CONSTRAINT "fkb2446vrjljcnwmw5ql8hnof0t" FOREIGN KEY ("user_id") REFERENCES "main_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "main_user_role" ADD CONSTRAINT "fkqjyi9j8fbvtms7nkuca66twql" FOREIGN KEY ("role_id") REFERENCES "main_role" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table main_user_session
-- ----------------------------
ALTER TABLE "main_user_session" ADD CONSTRAINT "fk3l2thufyx3ign0n00x97c4ib9" FOREIGN KEY ("role_id") REFERENCES "main_role" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "main_user_session" ADD CONSTRAINT "fk3wui2rny6na5wl3xugqjf4ej6" FOREIGN KEY ("user_id") REFERENCES "main_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
