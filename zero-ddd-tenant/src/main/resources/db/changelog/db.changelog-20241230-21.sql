--liquibase formatted sql

alter table if exists menu_resource
drop constraint if exists FK44d3jb1s67mqmav66rufxlndj;

alter table if exists tenant
drop constraint if exists FKmc75yf7tmpqiaxb101oosvae2;

alter table if exists tenant_menu
drop constraint if exists FKcm61ntrwkqkqm5tg87jd3wd8s;

alter table if exists tenant_menu
drop constraint if exists FKqw859jd783qmie5omnsitgtp2;

drop table if exists EVENT_PUBLICATION cascade;

drop table if exists event_publication_archive cascade;
    
drop table if exists menu_resource cascade;
    
drop table if exists tenant cascade;
    
drop table if exists tenant_data_source cascade;
    
drop table if exists tenant_menu cascade;

create table event_publication_archive (
                                           id uuid not null,
                                           completion_date timestamp(6) with time zone,
                                           event_type varchar(255),
                                           listener_id varchar(255),
                                           publication_date timestamp(6) with time zone,
                                           serialized_event varchar(1000),
                                           primary key (id)
);

create table EVENT_PUBLICATION (
                                       completion_date timestamp(6) with time zone,
                                       publication_date timestamp(6) with time zone,
                                       id uuid not null,
                                       event_type varchar(255),
                                       listener_id varchar(255),
                                       serialized_event varchar(1000),
                                       primary key (id)
);
    
create table menu_resource (
                               order_num integer,
                               created_date timestamp(6),
                               id bigint generated by default as identity,
                               last_modified_date timestamp(6),
                               parent_id bigint,
                               component varchar(255),
                               created_by varchar(255),
                               icon varchar(255),
                               is_cache varchar(255),
                               is_frame varchar(255),
                               last_modified_by varchar(255),
                               menu_name varchar(255),
                               menu_type varchar(255),
                               path varchar(255),
                               query_param varchar(255),
                               status varchar(255),
                               visible varchar(255),
                               primary key (id)
);
    
create table tenant (
                        account_count bigint,
                        created_date timestamp(6),
                        expire_time timestamp(6),
                        id bigint generated by default as identity,
                        last_modified_date timestamp(6),
                        tenant_data_source_id bigint unique,
                        address varchar(255),
                        company_name varchar(255),
                        contact_phone varchar(255),
                        contact_user_name varchar(255),
                        created_by varchar(255),
                        domain varchar(255),
                        intro varchar(255),
                        last_modified_by varchar(255),
                        license_number varchar(255),
                        remark varchar(255),
                        status varchar(255),
                        tenant_key varchar(255),
                        primary key (id)
);
    
create table tenant_data_source (
                                    enabled boolean,
                                    created_date timestamp(6),
                                    id bigint generated by default as identity,
                                    last_modified_date timestamp(6),
                                    created_by varchar(255),
                                    db varchar(255),
                                    last_modified_by varchar(255),
                                    password varchar(255),
                                    schema varchar(255),
                                    primary key (id)
);
    
create table tenant_menu (
                             created_date timestamp(6),
                             id bigint generated by default as identity,
                             last_modified_date timestamp(6),
                             menu_resource_id bigint,
                             tenant_id bigint,
                             created_by varchar(255),
                             last_modified_by varchar(255),
                             status varchar(255),
                             primary key (id)
);
    
alter table if exists menu_resource
    add constraint FK44d3jb1s67mqmav66rufxlndj
    foreign key (parent_id)
    references menu_resource;
    
alter table if exists tenant
    add constraint FKmc75yf7tmpqiaxb101oosvae2
    foreign key (tenant_data_source_id)
    references tenant_data_source;
    
alter table if exists tenant_menu
    add constraint FKcm61ntrwkqkqm5tg87jd3wd8s
    foreign key (menu_resource_id)
    references menu_resource;
    
alter table if exists tenant_menu
    add constraint FKqw859jd783qmie5omnsitgtp2
    foreign key (tenant_id)
    references tenant;