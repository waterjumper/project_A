-- Department
DROP TABLE IF EXISTS sys_dept;
CREATE TABLE sys_dept (
                          id varchar(64) NOT NULL COMMENT 'Primary Key',
                          dept_no varchar(18) DEFAULT NULL COMMENT 'Department number (rule: parent relation code + own code)',
                          name varchar(300) DEFAULT NULL COMMENT 'Department name',
                          pid varchar(64) NOT NULL COMMENT 'Parent ID',
                          status tinyint(4) COMMENT 'Status (1: normal; 0: discontinued)',
                          relation_code varchar(3000) DEFAULT NULL COMMENT 'To maintain deeper level relationships',
                          dept_manager_id varchar(64) DEFAULT NULL COMMENT 'Department manager user ID',
                          manager_name varchar(255) DEFAULT NULL COMMENT 'Department manager name',
                          phone varchar(20) DEFAULT NULL COMMENT 'Department manager contact phone',
                          create_time datetime DEFAULT NULL COMMENT 'Creation time',
                          update_time datetime DEFAULT NULL COMMENT 'Update time',
                          deleted tinyint(4) COMMENT 'Is deleted (1 not deleted; 0 deleted)',
                          PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='System Department';

-- System Log
DROP TABLE IF EXISTS sys_log;
CREATE TABLE sys_log (
                         id varchar(64) NOT NULL,
                         user_id varchar(64) DEFAULT NULL COMMENT 'User ID',
                         username varchar(50) DEFAULT NULL COMMENT 'Username',
                         operation varchar(50) DEFAULT NULL COMMENT 'User operation',
                         time int(11) DEFAULT NULL COMMENT 'Response time',
                         method varchar(200) DEFAULT NULL COMMENT 'Request method',
                         params varchar(5000) DEFAULT NULL COMMENT 'Request parameters',
                         ip varchar(64) DEFAULT NULL COMMENT 'IP address',
                         create_time datetime DEFAULT NULL COMMENT 'Creation time',
                         PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='System Log';

-- Menu Permission
DROP TABLE IF EXISTS sys_permission;
CREATE TABLE sys_permission  (
                                 id varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Primary Key',
                                 name varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Menu permission name',
                                 perms varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Authorization (multiple separated by commas, e.g., sys:user:add,sys:user:edit)',
                                 icon varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Icon',
                                 url varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Access URL',
                                 target varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'A target attribute: _self _blank',
                                 pid varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Parent menu permission name',
                                 order_num int(11) NULL COMMENT 'Order',
                                 type tinyint(4) NULL DEFAULT NULL COMMENT 'Menu permission type (1: directory; 2: menu; 3: button)',
                                 status tinyint(4) NULL COMMENT 'Status 1: normal 0: disabled',
                                 create_time datetime NULL DEFAULT NULL COMMENT 'Creation time',
                                 update_time datetime NULL DEFAULT NULL COMMENT 'Update time',
                                 deleted tinyint(4) NULL  COMMENT 'Is deleted (1 not deleted; 0 deleted)',
                                 PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'System Permissions' ROW_FORMAT = Compact;

-- Role
DROP TABLE IF EXISTS sys_role;
CREATE TABLE sys_role (
                          id varchar(64) NOT NULL COMMENT 'Primary Key',
                          name varchar(255) DEFAULT NULL COMMENT 'Role name',
                          description varchar(300) DEFAULT NULL,
                          status tinyint(4) COMMENT 'Status (1: normal 0: discontinued)',
                          create_time datetime DEFAULT NULL COMMENT 'Creation time',
                          update_time datetime DEFAULT NULL COMMENT 'Update time',
                          deleted tinyint(4) COMMENT 'Is deleted (1 not deleted; 0 deleted)',
                          data_scope int COMMENT 'Data scope (1: all 2: custom 3: this department and below 4: only this department 5: oneself)',
                          PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='System Role';

-- Role Department
DROP TABLE IF EXISTS sys_role_dept;
CREATE TABLE sys_role_dept  (
                                id varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Primary Key',
                                role_id varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Role ID',
                                dept_id varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Department ID',
                                create_time datetime NULL DEFAULT NULL COMMENT 'Creation time',
                                PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Role Department' ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;

-- Role Permission Association
DROP TABLE IF EXISTS sys_role_permission;
CREATE TABLE sys_role_permission (
                                     id varchar(64) NOT NULL COMMENT 'Primary Key',
                                     role_id varchar(64) DEFAULT NULL COMMENT 'Role ID',
                                     permission_id varchar(64) DEFAULT NULL COMMENT 'Menu permission ID',
                                     create_time datetime DEFAULT NULL COMMENT 'Creation time',
                                     PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- User Table
DROP TABLE IF EXISTS sys_user;
CREATE TABLE sys_user (
                          id varchar(64) NOT NULL COMMENT 'User ID',
                          username varchar(50) NOT NULL COMMENT 'Account name',
                          salt varchar(20) DEFAULT NULL COMMENT 'Encryption salt',
                          password varchar(200) NOT NULL COMMENT 'User password ciphertext',
                          phone varchar(20) DEFAULT NULL COMMENT 'Phone number',
                          dept_id varchar(64) DEFAULT NULL COMMENT 'Department ID',
                          real_name varchar(60) DEFAULT NULL COMMENT 'Real name',
                          nick_name varchar(60) DEFAULT NULL COMMENT 'Nickname',
                          email varchar(50) DEFAULT NULL COMMENT 'Email (unique)',
                          status tinyint(4) COMMENT 'Account status (1: normal 0: locked)',
                          sex tinyint(4) COMMENT 'Gender (1: male 2: female)',
                          deleted tinyint(4)  COMMENT 'Is deleted (1 not deleted; 0 deleted)',
                          create_id varchar(64) DEFAULT NULL COMMENT 'Creator',
                          update_id varchar(64) DEFAULT NULL COMMENT 'Updater',
                          create_where tinyint(4) COMMENT 'Creation source (1: web 2: Android 3: iOS)',
                          create_time datetime DEFAULT NULL COMMENT 'Creation time',
                          update_time datetime DEFAULT NULL,
                          PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='System User';

-- User Role Association Table
DROP TABLE IF EXISTS sys_user_role;
CREATE TABLE sys_user_role (
                               id varchar(64) NOT NULL COMMENT 'User ID',
                               user_id varchar(64) DEFAULT NULL,
                               role_id varchar(64) DEFAULT NULL COMMENT 'Role ID',
                               create_time datetime DEFAULT NULL COMMENT 'Creation time',
                               PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='System User Role';

-- Data Dictionary Table
DROP TABLE IF EXISTS sys_dict;
CREATE TABLE sys_dict  (
                           id varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                           name varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Dictionary name',
                           remark varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Description',
                           create_time datetime NULL DEFAULT NULL COMMENT 'Creation time',
                           PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Data Dictionary Table' ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;

-- Data Dictionary Details
DROP TABLE IF EXISTS sys_dict_detail;
CREATE TABLE sys_dict_detail  (
                                  id varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                                  label varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Dictionary label',
                                  value varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Dictionary value',
                                  sort smallint(6) NULL DEFAULT NULL COMMENT 'Sort',
                                  dict_id varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Dictionary ID',
                                  create_time datetime NULL DEFAULT NULL COMMENT 'Creation date',
                                  PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Data Dictionary Details' ROW_FORMAT = Compact;

-- Scheduled Task
DROP TABLE IF EXISTS sys_job;
CREATE TABLE sys_job  (
                          id varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Task ID',
                          bean_name varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Spring bean name',
                          params varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Parameters',
                          cron_expression varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Cron expression',
                          status tinyint(4) NULL DEFAULT NULL COMMENT 'Task status 0: normal 1: paused',
                          remark varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Remark',
                          create_time datetime NULL DEFAULT NULL COMMENT 'Creation time',
                          PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Scheduled Task' ROW_FORMAT = Compact;

-- Scheduled Task Log
DROP TABLE IF EXISTS sys_job_log;
CREATE TABLE sys_job_log  (
                              id varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Task log ID',
                              job_id varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Task ID',
                              bean_name varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Spring bean name',
                              params varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Parameters',
                              status tinyint(4) NOT NULL COMMENT 'Task status 0: success 1: failure',
                              error varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Failure information',
                              times int(11) NOT NULL COMMENT 'Duration (in milliseconds)',
                              create_time datetime NULL DEFAULT NULL COMMENT 'Creation time',
                              PRIMARY KEY (id) USING BTREE,
                              INDEX job_id(job_id) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Scheduled Task Log' ROW_FORMAT = Compact;

-- 2020.5.27 Added Article Management
DROP TABLE IF EXISTS sys_content;
CREATE TABLE sys_content  (
                              id varchar(50) CHARACTER SET utf8mb4 NOT NULL COMMENT 'Primary Key',
                              title varchar(255) CHARACTER SET utf8mb4 NULL DEFAULT NULL COMMENT 'Title',
                              type int(11)  DEFAULT NULL COMMENT 'Article type',
                              content longtext CHARACTER SET utf8mb4 NULL COMMENT 'Content',
                              create_time datetime NULL DEFAULT NULL COMMENT 'Creation time',
                              create_id varchar(50) NULL DEFAULT NULL COMMENT 'Creator',
                              PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COMMENT = 'Article Management' ROW_FORMAT = Compact;

-- Drop the table if it exists
DROP TABLE IF EXISTS c_user;
-- Create the 'c_user' table
CREATE TABLE `c_user` (
                          `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Primary Key ID',
                          `username` varchar(50) NOT NULL COMMENT 'Account Name',
                          `phone` varchar(18) NOT NULL COMMENT 'Phone Number',
                          `salt` varchar(20) DEFAULT NULL COMMENT 'Encryption Salt',
                          `password` varchar(200) NOT NULL COMMENT 'User Password Hash',
                          `ctime` bigint NOT NULL DEFAULT 0 COMMENT 'Creation Time',
                          `utime` bigint NOT NULL DEFAULT 0 COMMENT 'Update Time',
                          PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='c-end Users';

-- Drop the 'dishes' table if it exists
DROP TABLE IF EXISTS dishes;
-- Create the 'dishes' table
CREATE TABLE `dishes` (
                          `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Primary Key ID',
                          `code` varchar(32) NOT NULL COMMENT 'Dish Code',
                          `name` varchar(255) NOT NULL COMMENT 'Dish Name',
                          `url` varchar(255) NOT NULL DEFAULT 'URL of Dish Image',
                          `description` varchar(1024) NOT NULL DEFAULT '' COMMENT 'Detailed Description of the Dish, Including Main Ingredients and Flavor Characteristics',
                          `price` int(11) NOT NULL COMMENT 'Price of the Dish (Unit: Cents)',
                          `rating` int(11) NOT NULL DEFAULT '0' COMMENT 'Recommendation Index, Five Stars as Highest Recommendation',
                          `ctime` bigint(20) NOT NULL DEFAULT '0',
                          `utime` bigint(20) NOT NULL DEFAULT '0',
                          PRIMARY KEY (`id`) USING BTREE,
                          UNIQUE KEY `idx_code` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Create the 'orders' table if it does not exist
CREATE TABLE IF NOT EXISTS orders (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Primary Key ID',
    `code` varchar(32) NOT NULL COMMENT 'Order Code',
    `user_id` bigint(20) NOT NULL COMMENT 'User ID Who Placed the Order',
    `order_status` TINYINT NOT NULL DEFAULT '0' COMMENT 'Order Status (0: Pending Payment; 1: Paid; 2: Shipped; 3: Completed; 4: Cancelled)',
    `total_amount` int(11) NOT NULL DEFAULT '0' COMMENT 'Total Order Amount (Cents)',
    `payment_amount` int(11) NOT NULL DEFAULT '0' COMMENT 'Actual Payment Amount (Cents)',
    `ctime` bigint(20) NOT NULL DEFAULT '0',
    `utime` bigint(20) NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `idx_code` (`code`) USING BTREE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Orders Table';

-- Create the 'order_details' table if it does not exist
CREATE TABLE IF NOT EXISTS order_details (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Primary Key ID',
    `order_id` bigint(20) NOT NULL COMMENT 'Order ID',
    `product_code` varchar(40) NOT NULL DEFAULT '' COMMENT 'Product Code',
    `product_name` varchar(255) NOT NULL DEFAULT '',
    `quantity` INT NOT NULL COMMENT 'Quantity Purchased',
    `unit_price` INT NOT NULL COMMENT 'Unit Price',
    `ctime` bigint(20) NOT NULL DEFAULT '0',
    `utime` bigint(20) NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`) USING BTREE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Order Details Table';

-- Drop the 'cart' table if it exists
DROP TABLE IF EXISTS cart;
-- Create the 'cart' table
CREATE TABLE `cart` (
                        `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Primary Key ID',
                        `user_id` bigint(20) NOT NULL COMMENT 'User ID',
                        `product_code` varchar(40) NOT NULL COMMENT 'Product Code',
                        `quantity` int(11) NOT NULL COMMENT 'Quantity',
                        `unit_price` int(11) NOT NULL COMMENT 'Unit Price',
                        `config` text COMMENT 'config',
                        `remark` varchar(225) NOT NULL COMMENT 'Remark',
                        `ctime` bigint(20) NOT NULL DEFAULT '0',
                        `utime` bigint(20) NOT NULL DEFAULT '0',
                        PRIMARY KEY (`id`) USING BTREE,
                        UNIQUE KEY `idx_uid_code` (`user_id`, `product_code`) COMMENT 'Ensuring Uniqueness'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Shopping Cart Table';




INSERT INTO `cooke`.`dishes` (`id`, `code`, `name`, `url`, `description`, `price`, `rating`, `ctime`, `utime`) VALUES (1778996352919842818, 'fkqrBbAw1712980722586Jl2cCNoD', 'braised pig''s trotters', '2024/0413/128779241994708-vr4RiSZ6.jpeg', 'test22222', 101, 0, 1712980757348, 1712983827804);
INSERT INTO `cooke`.`dishes` (`id`, `code`, `name`, `url`, `description`, `price`, `rating`, `ctime`, `utime`) VALUES (1779012035316875266, 'lJupBQlr1712984496315cBLmmbTB', 'Australian lobster', '2024/0413/131842555066666-zQmeIPFS.jpeg', 'fsdf', 1200, 0, 1712984496321, 1712984670171);
INSERT INTO `cooke`.`dishes` (`id`, `code`, `name`, `url`, `description`, `price`, `rating`, `ctime`, `utime`) VALUES (1779842094332178433, 'jsMH3j9V1713182397805MH0MLELd', 'preserved pork with garlic', '2024/0415/171367166239708-QZz6G1I7.jpeg', '', 33, 0, 1713182397812, 1713182397812);
INSERT INTO `cooke`.`dishes` (`id`, `code`, `name`, `url`, `description`, `price`, `rating`, `ctime`, `utime`) VALUES (1779844464353071106, 'XELxTpLu1713182962861RsmdlpFg', 'spicy squid', '2024/0415/171949076478791-Xz4JgOkl.jpeg', '', 66, 0, 1713182962869, 1713182977105);
INSERT INTO `cooke`.`dishes` (`id`, `code`, `name`, `url`, `description`, `price`, `rating`, `ctime`, `utime`) VALUES (1779844464353071108, 'XELxTpLu1713739532861RsmdlpFg', 'Shiping tofu', '2024/0415/20240510163947.jpeg', '', 23, 0, 1713182962859, 1713182977805);
INSERT INTO `cooke`.`dishes` (`id`, `code`, `name`, `url`, `description`, `price`, `rating`, `ctime`, `utime`) VALUES (1779844464353071116, 'XELxTpLu1738585732861RsmdlpFg', 'Stir-fried shredded potatoes', '2024/0415/20240510165441.jpeg', '', 16, 0, 1713182962859, 1713182977805);
INSERT INTO `cooke`.`dishes` (`id`, `code`, `name`, `url`, `description`, `price`, `rating`, `ctime`, `utime`) VALUES (1779844464353071198, 'XELxTpLu1713525732861RsmdlpFg', 'braised fish', '2024/0415/20240510165722.jpeg', '', 48, 0, 1713182962859, 1713182977805);
INSERT INTO `cooke`.`dishes` (`id`, `code`, `name`, `url`, `description`, `price`, `rating`, `ctime`, `utime`) VALUES (1779844464353071182, 'XELxTpLu1713898732861RsmdlpFg', 'Shiping tofu', '2024/0415/20240510170034.jpeg', '', 66, 0, 1713182962859, 1713182977805);

INSERT INTO `cooke`.`cart` (`id`, `user_id`, `product_code`, `quantity`, `unit_price`, `remark`, `ctime`, `utime`) VALUES (4, 1779119383055015937, 'lJupBQlr1712984496315cBLmmbTB', 2, 11, '1', 1713077026336, 1713077091797);
INSERT INTO `cooke`.`cart` (`id`, `user_id`, `product_code`, `quantity`, `unit_price`, `remark`, `ctime`, `utime`) VALUES (6, 1779119383055015937, 'fkqrBbAw1712980722586Jl2cCNoD', 1, 101, '1', 1713077105160, 1713077105160);
INSERT INTO `cooke`.`cart` (`id`, `user_id`, `product_code`, `quantity`, `unit_price`, `remark`, `ctime`, `utime`) VALUES (7, 1779119383055015937, 'jsMH3j9V1713182397805MH0MLELd', 1, 120, '1', 1713182840066, 1713182840066);
INSERT INTO `cooke`.`cart` (`id`, `user_id`, `product_code`, `quantity`, `unit_price`, `remark`, `ctime`, `utime`) VALUES (8, 1779119383055015937, 'XELxTpLu1713182962861RsmdlpFg', 1, 1200, '1', 1713182990321, 1713182990321);


-- 初始数据
INSERT INTO sys_dept(id, dept_no, name, pid, status, relation_code, dept_manager_id, manager_name, phone, deleted) VALUES ('1', 'D000001', '总公司', '0', 1, 'D000001', NULL, '小李', '13888888888', 1);
INSERT INTO sys_permission VALUES ('1', 'delete', 'sysGenerator:delete', NULL, 'sysGenerator/delete', NULL, '15', 1, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission VALUES ('10', 'assign role', 'sys:user:role:update', NULL, 'sys/user/roles/*', NULL, '24', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission VALUES ('11', 'menu permission management', NULL, NULL, 'index/menus', '_self', '51', 98, 2, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission VALUES ('12', 'list', 'sys:dept:list', NULL, 'sys/depts', NULL, '41', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission VALUES ('13', 'delete', 'sys:role:deleted', NULL, 'sys/role/*', NULL, '53', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission VALUES ('1311115974068449281', 'data permission', 'sys:role:bindDept', '', 'sys/role/bindDept', '_self', '53', 5, 3, 1, '2020-09-30 09:29:42', NULL, 1);
INSERT INTO sys_permission VALUES ('14', 'immediately execute scheduled task', 'sysJob:run', NULL, 'sysJob/run', '_self', '59', 5, 3, 1, '2020-04-22 15:47:54', NULL, 1);
INSERT INTO sys_permission VALUES ('15', 'code generation', NULL, NULL, 'index/sysGenerator', '_self', '54', 1, 2, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission VALUES ('16', 'list', 'sysGenerator:list', NULL, 'sysGenerator/listByPage', NULL, '15', 1, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission VALUES ('17', 'detail', 'sys:permission:detail', NULL, 'sys/permission/*', NULL, '11', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission VALUES ('18', 'Timed Task Recovery', 'sysJob:resume', NULL, 'sysJob/resume', '_self', '59', 4, 3, 1, '2020-04-22 15:48:40', NULL, 1);
INSERT INTO sys_permission VALUES ('19', 'list', 'sys:role:list', NULL, 'sys/roles', NULL, '53', 0, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission VALUES ('2', 'SQL control', '', '', 'druid/sql.html', '_self', '21', 98, 2, 1, '2020-03-19 13:29:40', '2020-05-07 13:36:59', 1);
INSERT INTO sys_permission VALUES ('20', 'change', 'sysGenerator:update', NULL, 'sysGenerator/update', NULL, '15', 1, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission VALUES ('21', 'else', NULL, 'layui-icon-list', NULL, NULL, '0', 200, 1, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission VALUES ('22', 'detail', 'sys:dept:detail', NULL, 'sys/dept/*', NULL, '41', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission VALUES ('23', 'list', 'sys:user:list', NULL, 'sys/users', NULL, '24', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission VALUES ('24', 'user manage', NULL, NULL, 'index/users', '_self', '51', 100, 2, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission VALUES ('25', 'detail', 'sys:user:detail', NULL, 'sys/user/*', NULL, '24', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission VALUES ('26', 'delete', 'sys:permission:deleted', NULL, 'sys/permission/*', NULL, '11', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission VALUES ('27', 'file manage', '', '', 'index/sysFiles', '_self', '54', 10, 2, 1, NULL, '2020-06-15 16:00:29', 1);
INSERT INTO sys_permission VALUES ('28', 'list', 'sysFiles:list', NULL, 'sysFiles/listByPage', NULL, '27', 0, 3, 1, NULL, NULL, 1);
INSERT INTO sys_permission VALUES ('29', 'add', 'sysFiles:add', NULL, 'sysFiles/add', NULL, '27', 0, 3, 1, NULL, NULL, 1);
INSERT INTO sys_permission VALUES ('3', 'add', 'sys:role:add', NULL, 'sys/role', NULL, '53', 0, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission VALUES ('30', 'delete', 'sysFiles:delete', NULL, 'sysFiles/delete', NULL, '27', 0, 3, 1, NULL, NULL, 1);
INSERT INTO sys_permission VALUES ('31', 'Article Management', NULL, NULL, 'index/sysContent', '_self', '54', 10, 2, 1, NULL, NULL, 1);
INSERT INTO sys_permission VALUES ('32', 'list', 'sysContent:list', NULL, 'sysContent/listByPage', NULL, '31', 0, 3, 1, NULL, NULL, 1);
INSERT INTO sys_permission VALUES ('33', 'add', 'sysContent:add', NULL, 'sysContent/add', NULL, '31', 0, 3, 1, NULL, NULL, 1);
INSERT INTO sys_permission VALUES ('34', 'change', 'sysContent:update', NULL, 'sysContent/update', NULL, '31', 0, 3, 1, NULL, NULL, 1);
INSERT INTO sys_permission VALUES ('35', 'delete', 'sysContent:delete', NULL, 'sysContent/delete', NULL, '31', 0, 3, 1, NULL, NULL, 1);
INSERT INTO sys_permission VALUES ('36', 'update', 'sys:role:update', NULL, 'sys/role', NULL, '53', 0, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission VALUES ('38', 'update', 'sys:dept:update', NULL, 'sys/dept', NULL, '41', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission VALUES ('39', 'detail', 'sys:role:detail', NULL, 'sys/role/*', NULL, '53', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission VALUES ('4', 'add', 'sysGenerator:add', NULL, 'sysGenerator/add', NULL, '15', 1, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission VALUES ('40', 'edit', 'sys:permission:update', NULL, 'sys/permission', NULL, '11', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission VALUES ('41', 'Sectoral management', NULL, NULL, 'index/depts', '_self', '51', 100, 2, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission VALUES ('42', 'add', 'sys:user:add', NULL, 'sys/user', NULL, '24', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission VALUES ('43', 'list', 'sys:permission:list', NULL, 'sys/permissions', NULL, '11', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission VALUES ('44', 'add', 'sys:permission:add', NULL, 'sys/permission', NULL, '11', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission VALUES ('45', 'Dictionary management', NULL, '', 'index/sysDict', NULL, '54', 10, 2, 1, NULL, NULL, 1);
INSERT INTO sys_permission VALUES ('46', 'list', 'sysDict:list', NULL, 'sysDict/listByPage', NULL, '45', 0, 3, 1, NULL, NULL, 1);
INSERT INTO sys_permission VALUES ('47', 'add', 'sysDict:add', NULL, 'sysDict/add', NULL, '45', 0, 3, 1, NULL, NULL, 1);
INSERT INTO sys_permission VALUES ('48', 'edit', 'sysDict:update', NULL, 'sysDict/update', NULL, '45', 0, 3, 1, NULL, NULL, 1);
INSERT INTO sys_permission VALUES ('49', 'delete', 'sysDict:delete', NULL, 'sysDict/delete', NULL, '45', 0, 3, 1, NULL, NULL, 1);
INSERT INTO sys_permission VALUES ('5', 'delete', 'sys:dept:deleted', NULL, 'sys/dept/*', NULL, '41', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission VALUES ('50', 'Form Construction', '', '', 'index/build', '_self', '21', 1, 2, 1, '2020-04-22 13:09:41', '2020-05-07 13:36:47', 1);
INSERT INTO sys_permission VALUES ('51', 'Organizational management', NULL, 'layui-icon-user', NULL, NULL, '0', 1, 1, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission VALUES ('52', 'Ownership of roles', 'sys:user:role:detail', NULL, 'sys/user/roles/*', NULL, '24', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission VALUES ('53', 'Role Management', NULL, NULL, 'index/roles', '_self', '51', 99, 2, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission VALUES ('54', 'system management', NULL, 'layui-icon-set-fill', NULL, NULL, '0', 98, 1, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission VALUES ('55', 'Timed Task Pause', 'sysJob:pause', NULL, 'sysJob/pause', '_self', '59', 1, 3, 1, '2020-04-22 15:48:18', NULL, 1);
INSERT INTO sys_permission VALUES ('56', 'update', 'sys:user:update', NULL, 'sys/user', NULL, '24', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission VALUES ('57', 'delete', 'sys:user:deleted', NULL, 'sys/user', NULL, '24', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission VALUES ('58', 'delete', 'sys:log:deleted', NULL, 'sys/logs', NULL, '8', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission VALUES ('59', 'scheduled task', NULL, NULL, 'index/sysJob', '_self', '54', 10, 2, 1, NULL, NULL, 1);
INSERT INTO sys_permission VALUES ('6', 'interface manage', '', '', 'doc.html', '_blank', '21', 100, 2, 1, '2020-03-19 13:29:40', '2020-05-07 13:36:02', 1);
INSERT INTO sys_permission VALUES ('60', 'list', 'sysJob:list', NULL, 'sysJob/listByPage', NULL, '59', 0, 3, 1, NULL, NULL, 1);
INSERT INTO sys_permission VALUES ('61', 'add', 'sysJob:add', NULL, 'sysJob/add', NULL, '59', 0, 3, 1, NULL, NULL, 1);
INSERT INTO sys_permission VALUES ('62', 'edit', 'sysJob:update', NULL, 'sysJob/update', NULL, '59', 0, 3, 1, NULL, NULL, 1);
INSERT INTO sys_permission VALUES ('63', 'delete', 'sysJob:delete', NULL, 'sysJob/delete', NULL, '59', 0, 3, 1, NULL, NULL, 1);
INSERT INTO sys_permission VALUES ('7', 'list', 'sys:log:list', NULL, 'sys/logs', NULL, '8', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission VALUES ('8', 'log manage', NULL, NULL, 'index/logs', '_self', '54', 97, 2, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);
INSERT INTO sys_permission VALUES ('9', 'add', 'sys:dept:add', NULL, 'sys/dept', NULL, '41', 100, 3, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);


INSERT INTO sys_role(id, name, description, status, create_time, update_time, deleted) VALUES ('1', '超级管理员', '拥有所有权限-不能删除', 1, '2019-11-01 19:26:29', '2020-03-19 13:29:51', 1);
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('1', '1', '1', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('10', '1', '10', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('11', '1', '11', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('12', '1', '12', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('13', '1', '13', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('14', '1', '14', '2020-05-26 17:04:21');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('15', '1', '15', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('16', '1', '16', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('17', '1', '17', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('18', '1', '18', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('19', '1', '19', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('2', '1', '2', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('20', '1', '20', '2020-05-26 17:04:21');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('21', '1', '21', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('22', '1', '22', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('23', '1', '23', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('24', '1', '24', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('25', '1', '25', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('26', '1', '26', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('27', '1', '27', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('28', '1', '28', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('29', '1', '29', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('3', '1', '3', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('30', '1', '30', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('31', '1', '31', '2020-05-26 17:04:21');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('32', '1', '32', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('33', '1', '33', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('34', '1', '34', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('35', '1', '35', '2020-05-26 17:04:21');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('36', '1', '36', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('38', '1', '38', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('39', '1', '39', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('4', '1', '4', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('40', '1', '40', '2020-06-15 15:21:17');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('41', '1', '41', '2020-06-15 15:21:17');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('42', '1', '42', '2020-06-15 15:21:17');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('43', '1', '43', '2020-06-15 15:21:17');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('44', '1', '44', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('45', '1', '45', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('46', '1', '46', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('47', '1', '47', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('48', '1', '48', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('49', '1', '49', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('5', '1', '5', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('50', '1', '50', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('51', '1', '51', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('52', '1', '52', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('53', '1', '53', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('54', '1', '54', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('55', '1', '55', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('56', '1', '56', '2020-05-26 17:04:21');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('57', '1', '57', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('58', '1', '58', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('59', '1', '59', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('6', '1', '6', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('60', '1', '60', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('61', '1', '61', '2020-05-26 14:21:56');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('62', '1', '62', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('63', '1', '63', '2020-04-22 15:48:47');


INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('7', '1', '7', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('8', '1', '8', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('9', '1', '9', '2020-04-22 15:48:47');
INSERT INTO sys_role_permission(id, role_id, permission_id, create_time) VALUES ('1311116066716430339', '1', '1311115974068449281', '2020-09-30 09:30:04');



-- 菜单新增
INSERT INTO `cooke`.`sys_permission` (`id`, `name`, `perms`, `icon`, `url`, `target`, `pid`, `order_num`, `type`, `status`, `create_time`, `update_time`, `deleted`)
VALUES ('70', 'Cook Application Management', NULL, 'layui-icon-list', NULL, NULL, '0', 200, 1, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 1);

INSERT INTO `cooke`.`sys_permission` (`id`, `name`, `perms`, `icon`, `url`, `target`, `pid`, `order_num`, `type`, `status`, `create_time`, `update_time`, `deleted`)
VALUES ('71', 'User management', '', '', 'index/c_users', '_self', '70', 100, 2, 1, '2020-03-19 13:29:40', '2020-05-07 13:36:02', 1);
INSERT INTO `cooke`.`sys_permission` (`id`, `name`, `perms`, `icon`, `url`, `target`, `pid`, `order_num`, `type`, `status`, `create_time`, `update_time`, `deleted`)
VALUES ('72', 'Menu management', '', '', 'index/cook', '_self', '70', 100, 2, 1, '2020-03-19 13:29:40', '2020-05-07 13:36:02', 1);

INSERT INTO `cooke`.`sys_permission` (`id`, `name`, `perms`, `icon`, `url`, `target`, `pid`, `order_num`, `type`, `status`, `create_time`, `update_time`, `deleted`)
VALUES ('73', 'Order Management', '', '', 'index/order', '_self', '70', 100, 2, 1, '2020-03-19 13:29:40', '2020-05-07 13:36:02', 1);



-- 绑定权限
INSERT INTO `sys_role_permission` (`id`, `role_id`, `permission_id`, `create_time`) VALUES ('70', '1', '70', '2020-04-22 15:48:47');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `permission_id`, `create_time`) VALUES ('71', '1', '71', '2020-04-22 15:48:47');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `permission_id`, `create_time`) VALUES ('72', '1', '72', '2020-04-22 15:48:47');
INSERT INTO `sys_role_permission` (`id`, `role_id`, `permission_id`, `create_time`) VALUES ('73', '1', '73', '2020-04-22 15:48:47');



INSERT INTO sys_user(id, username, salt, password, phone, dept_id, real_name, nick_name, email, status, sex, deleted, create_id, update_id, create_where, create_time, update_time) VALUES ('1', 'admin', '324ce32d86224b00a02b', '2102b59a75ab87616b62d0b9432569d0', '13888888888', '1', 'Hahahah', 'Hahahah', 'xxxxxx@163.com', 1, 2, 1, '1', '1', 3, '2019-09-22 19:38:05', '2020-03-18 09:15:22');
INSERT INTO sys_user_role(id, user_id, role_id, create_time) VALUES ('1', '1', '1', '2020-03-19 02:23:13');
INSERT INTO sys_dict(id, name, remark, create_time) VALUES ('1255790029680242690', 'sex', 'sex', '2020-04-30 17:24:09');
INSERT INTO sys_dict(id, name, remark, create_time) VALUES ('1282504369620430849', 'content_type', 'hahaha', '2020-07-13 10:37:24');
INSERT INTO sys_dict_detail(id, label, value, sort, dict_id, create_time) VALUES ('1255790073535885314', 'Male', '1', 1, '1255790029680242690', '2020-04-30 17:24:19');
INSERT INTO sys_dict_detail(id, label, value, sort, dict_id, create_time) VALUES ('1255790100115189761', 'Female', '2', 2, '1255790029680242690', '2020-04-30 17:24:25');
INSERT INTO sys_dict_detail(id, label, value, sort, dict_id, create_time) VALUES ('1282504475715350530', 'hahaha', '1', 1, '1282504369620430849', '2020-07-13 10:37:49');
INSERT INTO sys_dict_detail(id, label, value, sort, dict_id, create_time) VALUES ('1282504651729317889', 'Paul', '2', 2, '1282504369620430849', '2020-07-13 10:38:31');
INSERT INTO sys_dict_detail(id, label, value, sort, dict_id, create_time) VALUES ('1282846022950842369', 'rapper', '3', 3, '1282504369620430849', '2020-07-14 09:15:01');
INSERT INTO sys_job(id, bean_name, params, cron_expression, status, remark, create_time) VALUES ('1252884495040782337', 'testTask', '1', '0 */1 * * * ?', 0, '1', '2020-04-22 16:58:35');




DROP TABLE IF EXISTS review;
CREATE TABLE `review` (
                          `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Primary Key ID',
                          `user_id` bigint(20) NOT NULL COMMENT 'UserID',
                          `product_code` varchar(40) NOT NULL COMMENT 'Production code',
                          `star` int(11) NOT NULL COMMENT 'quantities',
                          `review` text COMMENT 'Comments',
                          `ctime` bigint(20) NOT NULL DEFAULT '0',
                          `utime` bigint(20) NOT NULL DEFAULT '0',
                          PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Dish Review Form';
