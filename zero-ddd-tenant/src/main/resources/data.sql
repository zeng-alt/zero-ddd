-- insert into menu_resource(id, menu_name, parent_id) values(1, '父菜单', null);
-- insert into menu_resource(id, menu_name, parent_id) values(2, '子菜单1', 1);
-- insert into menu_resource(id, menu_name, parent_id) values(3, '子菜单2', 1);
-- insert into menu_resource(id, menu_name, parent_id) values(4, '子菜单3', 1);
-- insert into menu_resource(id, menu_name, parent_id) values(5, '子菜单23', 2);
-- insert into menu_resource(id, menu_name, parent_id) values(6, '子菜单23', 2);
-- insert into menu_resource(id, menu_name, parent_id) values(7, '子菜单23', 3);


insert into tenant(id, tenant_key, company_name, tenant_by) values(1, 'FAT', '新风科技', 1);
insert into tenant(id, tenant_key, company_name, tenant_by) values(2, 'TTT', '天天科技', 1);

-- insert into tenant_menu(id, tenant_id, menu_resource_id) values(1, 1, 1);
-- insert into tenant_menu(id, tenant_id, menu_resource_id) values(2, 1, 2);