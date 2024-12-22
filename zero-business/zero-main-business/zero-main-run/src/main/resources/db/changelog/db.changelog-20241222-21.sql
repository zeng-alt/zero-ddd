--liquibase formatted sql

drop table if exists main_expression cascade;

drop table if exists main_permission cascade;

drop table if exists main_resource cascade;

drop table if exists main_role cascade;

drop table if exists main_role_permission cascade;

drop table if exists main_role_precondition cascade;

drop table if exists main_user cascade;

drop table if exists main_user_expression cascade;

drop table if exists main_user_group cascade;

drop table if exists main_user_group_role cascade;

drop table if exists main_user_group_user cascade;

drop table if exists main_user_resource cascade;

drop table if exists main_user_role cascade;

drop table if exists main_user_session cascade;

drop sequence if exists main_expression_seq;

drop sequence if exists main_permission_seq;

drop sequence if exists main_resource_seq;

drop sequence if exists main_role_permission_seq;

drop sequence if exists main_role_precondition_seq;

drop sequence if exists main_role_seq;

drop sequence if exists main_user_expression_seq;

drop sequence if exists main_user_group_role_seq;

drop sequence if exists main_user_group_seq;

drop sequence if exists main_user_group_user_seq;

drop sequence if exists main_user_resource_seq;

drop sequence if exists main_user_role_seq;

drop sequence if exists main_user_seq;

drop sequence if exists main_user_session_seq;
    
create sequence main_expression_seq start with 1 increment by 50;
    
create sequence main_permission_seq start with 1 increment by 50;
    
create sequence main_resource_seq start with 1 increment by 50;
    
create sequence main_role_permission_seq start with 1 increment by 50;
    
create sequence main_role_precondition_seq start with 1 increment by 50;
    
create sequence main_role_seq start with 1 increment by 50;
    
create sequence main_user_expression_seq start with 1 increment by 50;
    
create sequence main_user_group_role_seq start with 1 increment by 50;
    
create sequence main_user_group_seq start with 1 increment by 50;
    
create sequence main_user_group_user_seq start with 1 increment by 50;
    
create sequence main_user_resource_seq start with 1 increment by 50;
    
create sequence main_user_role_seq start with 1 increment by 50;
    
create sequence main_user_seq start with 1 increment by 50;
    
create sequence main_user_session_seq start with 1 increment by 50;

create table main_expression (
                                 created_date timestamp(6),
                                 id bigint not null,
                                 last_modified_date timestamp(6),
                                 resource_id bigint,
                                 created_by varchar(255),
                                 last_modified_by varchar(255),
                                 primary key (id)
);

create table main_permission (
                                 created_date timestamp(6),
                                 id bigint not null,
                                 last_modified_date timestamp(6),
                                 created_by varchar(255),
                                 last_modified_by varchar(255),
                                 primary key (id)
);

create table main_resource (
                               created_date timestamp(6),
                               id bigint not null,
                               last_modified_date timestamp(6),
                               parent_id bigint,
                               permission_id bigint unique,
                               created_by varchar(255),
                               last_modified_by varchar(255),
                               primary key (id)
);

create table main_role (
                           deleted integer,
                           role_sort integer,
                           created_date timestamp(6),
                           id bigint not null,
                           last_modified_date timestamp(6),
                           created_by varchar(255),
                           last_modified_by varchar(255),
                           role_key varchar(255),
                           role_name varchar(255),
                           status varchar(255),
                           primary key (id)
);

create table main_role_permission (
                                      created_date timestamp(6),
                                      id bigint not null,
                                      last_modified_date timestamp(6),
                                      permission_id bigint,
                                      role_id bigint,
                                      created_by varchar(255),
                                      last_modified_by varchar(255),
                                      primary key (id)
);

create table main_role_precondition (
                                        created_date timestamp(6),
                                        id bigint not null,
                                        last_modified_date timestamp(6),
                                        role_id bigint,
                                        created_by varchar(255),
                                        last_modified_by varchar(255),
                                        primary key (id)
);

create table main_user (
                           deleted integer,
                           created_date timestamp(6),
                           id bigint not null,
                           last_modified_date timestamp(6),
                           avatar varchar(255),
                           created_by varchar(255),
                           email varchar(255),
                           gender varchar(255),
                           last_modified_by varchar(255),
                           nick_name varchar(255),
                           password varchar(255),
                           phone_number varchar(255),
                           status varchar(255),
                           username varchar(255),
                           primary key (id)
);

create table main_user_expression (
                                      created_date timestamp(6),
                                      id bigint not null,
                                      last_modified_date timestamp(6),
                                      user_resource_id bigint,
                                      created_by varchar(255),
                                      last_modified_by varchar(255),
                                      primary key (id)
);

create table main_user_group (
                                 created_date timestamp(6),
                                 id bigint not null,
                                 last_modified_date timestamp(6),
                                 created_by varchar(255),
                                 last_modified_by varchar(255),
                                 primary key (id)
);

create table main_user_group_role (
                                      created_date timestamp(6),
                                      id bigint not null,
                                      last_modified_date timestamp(6),
                                      role_id bigint,
                                      user_group_id bigint,
                                      created_by varchar(255),
                                      last_modified_by varchar(255),
                                      primary key (id)
);

create table main_user_group_user (
                                      created_date timestamp(6),
                                      id bigint not null,
                                      last_modified_date timestamp(6),
                                      user_group_user_id bigint,
                                      user_id bigint,
                                      created_by varchar(255),
                                      last_modified_by varchar(255),
                                      primary key (id)
);

create table main_user_resource (
                                    created_date timestamp(6),
                                    id bigint not null,
                                    last_modified_date timestamp(6),
                                    resource_id bigint unique,
                                    user_id bigint,
                                    created_by varchar(255),
                                    last_modified_by varchar(255),
                                    primary key (id)
);

create table main_user_role (
                                created_date timestamp(6),
                                id bigint not null,
                                last_modified_date timestamp(6),
                                role_id bigint,
                                user_id bigint,
                                created_by varchar(255),
                                last_modified_by varchar(255),
                                primary key (id)
);

create table main_user_session (
                                   created_date timestamp(6),
                                   id bigint not null,
                                   last_modified_date timestamp(6),
                                   created_by varchar(255),
                                   last_modified_by varchar(255),
                                   primary key (id)
);


alter table if exists main_expression
    add constraint FKctm6ra2vx8wonfimyyf43tu35
    foreign key (resource_id)
    references main_resource;

alter table if exists main_resource
    add constraint FKo476chu4ascxg5lcsne50octg
    foreign key (parent_id)
    references main_resource;

alter table if exists main_resource
    add constraint FK6r7boyy67yy6ssekk5dg6532o
    foreign key (permission_id)
    references main_permission;

alter table if exists main_role_permission
    add constraint FK6xefdd10oy6ts32yh5i6p63xx
    foreign key (permission_id)
    references main_permission;

alter table if exists main_role_permission
    add constraint FK4asijil0617ps9gpyhthj65l
    foreign key (role_id)
    references main_role;

alter table if exists main_role_precondition
    add constraint FKr0hofiv39ejgje1jj2f8aqa0x
    foreign key (role_id)
    references main_role;

alter table if exists main_user_expression
    add constraint FKdaece98rnvyyciv9mf6lymrob
    foreign key (user_resource_id)
    references main_user_resource;

alter table if exists main_user_group_role
    add constraint FKba52gdf7iis4ujq39g7ritlup
    foreign key (role_id)
    references main_role;

alter table if exists main_user_group_role
    add constraint FKa7p2kin11wimpkjdvvu2c29ot
    foreign key (user_group_id)
    references main_user_group;

alter table if exists main_user_group_user
    add constraint FKnctduxmn3df72yi5ixsf8jgdm
    foreign key (user_id)
    references main_user;

alter table if exists main_user_group_user
    add constraint FK5sbc9pkxa4oqdpp2adktkc8u7
    foreign key (user_group_user_id)
    references main_user_group;

alter table if exists main_user_resource
    add constraint FK2kh1htft73vqlywcwpb28rkkm
    foreign key (resource_id)
    references main_resource;

alter table if exists main_user_resource
    add constraint FKc2vhxjnijdiarqfe9p7pmp2ui
    foreign key (user_id)
    references main_user;

alter table if exists main_user_role
    add constraint FKqjyi9j8fbvtms7nkuca66twql
    foreign key (role_id)
    references main_role;

alter table if exists main_user_role
    add constraint FKb2446vrjljcnwmw5ql8hnof0t
    foreign key (user_id)
    references main_user;