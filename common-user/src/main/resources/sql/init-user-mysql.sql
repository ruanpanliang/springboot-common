create table SYS_MENU
(
    ID           bigint auto_increment comment '编号'
        primary key,
    MENU_CODE    varchar(32)      not null comment '菜单编码',
    MENU_NAME    varchar(128)     not null comment '菜单名字',
    MENU_TYPE    varchar(32)      null comment '菜单类型',
    MENU_INDEX   int              not null comment '菜单序号',
    MENU_PATH    varchar(256)     not null comment '菜单路由',
    MENU_ICON    varchar(256)     not null comment '菜单图标',
    PARENT_ID    varchar(32)      not null comment '父菜单ID',
    CREATED_BY   bigint           null comment '创建人',
    CREATED_TIME datetime         not null comment '创建时间',
    UPDATED_BY   bigint           null comment '更新人',
    UPDATED_TIME datetime         not null comment '更新时间',
    RANDOM_CODE  varchar(32)      not null comment '随机码',
    REVISION     int  default 1   not null comment '版本号',
    DELETE_FLAG  char default '0' not null comment '逻辑删除标记 0:未删除 1:已删除'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT = '菜单';

create table SYS_PRIVILEGE
(
    ID             bigint auto_increment comment '编号'
        primary key,
    PRIVILEGE_CODE varchar(32)      not null comment '权限编码',
    PRIVILEGE_NAME varchar(128)     not null comment '权限名称',
    PRIVILEGE_TYPE varchar(32)      null comment '权限类型',
    ENTITY_TYPE    varchar(32)      null comment '实体类型',
    ENTITY_ID      varchar(32)      null comment '实体编码',
    REMARK         varchar(32)      null comment '备注',
    CREATED_BY     bigint           null comment '创建人',
    CREATED_TIME   datetime         not null comment '创建时间',
    UPDATED_BY     bigint           null comment '更新人',
    UPDATED_TIME   datetime         not null comment '更新时间',
    RANDOM_CODE    varchar(32)      not null comment '随机码',
    REVISION       int  default 1   not null comment '版本号',
    DELETE_FLAG    char default '0' not null comment '逻辑删除标记 0:未删除 1:已删除'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT = '权限';

create table SYS_PRIVILEGE_MENU
(
    ID           bigint auto_increment comment '编号'
        primary key,
    PRIVILEGE_ID bigint           not null comment '权限ID',
    MENU_ID      bigint           not null comment '菜单ID',
    CREATED_BY   bigint           null comment '创建人',
    CREATED_TIME datetime         not null comment '创建时间',
    UPDATED_BY   bigint           null comment '更新人',
    UPDATED_TIME datetime         not null comment '更新时间',
    RANDOM_CODE  varchar(32)      not null comment '随机码',
    REVISION     int  default 1   not null comment '版本号',
    DELETE_FLAG  char default '0' not null comment '逻辑删除标记 0:未删除 1:已删除'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT = '权限对应菜单';

create table SYS_ROLE
(
    ID           bigint auto_increment comment '编号'
        primary key,
    ROLE_CODE    varchar(32)      not null comment '角色编码',
    ROLE_NAME    varchar(128)     not null comment '角色名称',
    ROLE_TYPE    varchar(32)      not null comment '角色类型',
    ROLE_STATUS  int              not null comment '角色状态 | 1:正常 0：失效',
    REMARK       varchar(1024)    null comment '备注',
    CREATED_BY   bigint           null comment '创建人',
    CREATED_TIME datetime         not null comment '创建时间',
    UPDATED_BY   bigint           null comment '更新人',
    UPDATED_TIME datetime         not null comment '更新时间',
    RANDOM_CODE  varchar(32)      not null comment '随机码',
    REVISION     int  default 1   not null comment '版本号',
    DELETE_FLAG  char default '0' not null comment '逻辑删除标记 0:未删除 1:已删除'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT = '角色';

create table SYS_ROLE_PRIVILEGE
(
    ID           bigint auto_increment comment '编号'
        primary key,
    ROLE_ID      bigint           not null comment '角色编号',
    PRIVILEGE_ID bigint           not null comment '权限编码',
    CREATED_BY   bigint           null comment '创建人',
    CREATED_TIME datetime         not null comment '创建时间',
    UPDATED_BY   bigint           null comment '更新人',
    UPDATED_TIME datetime         not null comment '更新时间',
    RANDOM_CODE  varchar(32)      not null comment '随机码',
    REVISION     int  default 1   not null comment '版本号',
    DELETE_FLAG  char default '0' not null comment '逻辑删除标记 0:未删除 1:已删除'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT = '角色对应权限';

create table SYS_USER
(
    ID            bigint auto_increment comment '编号'
        primary key,
    USER_ACCOUNT  varchar(128)     null comment '用户账号',
    USER_PASSWORD varchar(128)     null comment '用户密码',
    USER_TYPE     varchar(32)      null comment '用户类型',
    USER_NAME     varchar(128)     null comment '用户名称',
    EMAIL         varchar(32)      null comment '电子邮件',
    PHONE         varchar(32)      null comment '手机号码',
    STATUS        int  default 1   null comment '用户状态 | 1：正常 0：注销',
    CREATED_BY    bigint           null comment '创建人',
    CREATED_TIME  datetime         not null comment '创建时间',
    UPDATED_BY    bigint           null comment '更新人',
    UPDATED_TIME  datetime         not null comment '更新时间',
    RANDOM_CODE   varchar(32)      not null comment '随机码',
    REVISION      int  default 1   not null comment '版本号',
    DELETE_FLAG   char default '0' not null comment '逻辑删除标记 0:未删除 1:已删除'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT = '用户';

create table SYS_USER_ROLE
(
    ID           bigint auto_increment comment '编号'
        primary key,
    USER_ID      bigint           not null comment '用户编号',
    ROLE_ID      bigint           not null comment '角色编号',
    CREATED_BY   bigint           null comment '创建人',
    CREATED_TIME datetime         not null comment '创建时间',
    UPDATED_BY   bigint           null comment '更新人',
    UPDATED_TIME datetime         not null comment '更新时间',
    RANDOM_CODE  varchar(32)      not null comment '随机码',
    REVISION     int  default 1   not null comment '版本号',
    DELETE_FLAG  char default '0' not null comment '逻辑删除标记 0:未删除 1:已删除'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT = '用户对应角色';

create table SYS_DICT_TYPE
(
    ID             bigint auto_increment comment '编号'
        primary key,
    DICT_TYPE_CODE varchar(32)      not null comment '字典类型编码',
    DICT_TYPE_NAME varchar(128)     not null comment '字典类型名称',
    STATUS         int  default 1   not null comment '状态 | 1：使用 0：未使用',
    CREATED_BY     varchar(32)      null comment '创建人',
    CREATED_TIME   datetime         not null comment '创建时间',
    UPDATED_BY     varchar(32)      null comment '更新人',
    UPDATED_TIME   datetime         not null comment '更新时间',
    RANDOM_CODE    varchar(32)      not null comment '随机码',
    REVISION       int  default 1   not null comment '版本号',
    DELETE_FLAG    char default '0' not null comment '逻辑删除标记 | 0:未删除 1:已删除'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='字典类型';

create table SYS_DICT
(
    ID             bigint auto_increment comment '编号'
        primary key,
    DICT_CODE      varchar(32)      not null comment '字典编号',
    DICT_NAME      varchar(128)     not null comment '字典名称',
    DICT_TYPE_CODE varchar(32)      not null comment '字典类型编码',
    STATUS         int  default 1   not null comment '状态 | 1：使用 0：未使用',
    CREATED_BY     varchar(32)      null comment '创建人',
    CREATED_TIME   datetime         not null comment '创建时间',
    UPDATED_BY     varchar(32)      null comment '更新人',
    UPDATED_TIME   datetime         not null comment '更新时间',
    RANDOM_CODE    varchar(32)      not null comment '随机码',
    REVISION       int  default 1   not null comment '版本号',
    DELETE_FLAG    char default '0' not null comment '逻辑删除标记 | 0:未删除 1:已删除'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='字典';


#初始化表数据

#菜单表
INSERT INTO SYS_MENU (ID, MENU_CODE, MENU_NAME, MENU_TYPE, MENU_INDEX, MENU_PATH, MENU_ICON, PARENT_ID, CREATED_BY, CREATED_TIME, UPDATED_BY, UPDATED_TIME, RANDOM_CODE, REVISION, DELETE_FLAG) VALUES (1, '1001', '用户管理', 'admin', 1, '/v1/user/user', 'aa', '0', 1, current_timestamp, 1, current_timestamp, '56279f8c16b44794a891e49d6f334ea3', 1, '0');
INSERT INTO SYS_MENU (ID, MENU_CODE, MENU_NAME, MENU_TYPE, MENU_INDEX, MENU_PATH, MENU_ICON, PARENT_ID, CREATED_BY, CREATED_TIME, UPDATED_BY, UPDATED_TIME, RANDOM_CODE, REVISION, DELETE_FLAG) VALUES (2, '1002', '角色管理', 'admin', 2, '/v1/user/role', 'bb', '0', 1, current_timestamp, 1, current_timestamp, '28279f8c16b44794a891e49d6f334ea3', 1, '0');
INSERT INTO SYS_MENU (ID, MENU_CODE, MENU_NAME, MENU_TYPE, MENU_INDEX, MENU_PATH, MENU_ICON, PARENT_ID, CREATED_BY, CREATED_TIME, UPDATED_BY, UPDATED_TIME, RANDOM_CODE, REVISION, DELETE_FLAG) VALUES (3, '1003', '权限管理', 'admin', 3, '/v1/user/privilege', 'cc', '0', 1, current_timestamp, 1, current_timestamp, '13be208b691e408a8397d53206a39cd6', 1, '0');
INSERT INTO SYS_MENU (ID, MENU_CODE, MENU_NAME, MENU_TYPE, MENU_INDEX, MENU_PATH, MENU_ICON, PARENT_ID, CREATED_BY, CREATED_TIME, UPDATED_BY, UPDATED_TIME, RANDOM_CODE, REVISION, DELETE_FLAG) VALUES (4, '1004', '菜单管理', 'admin', 4, '/v1/user/menu', 'dd', '0', 1, current_timestamp, 1, current_timestamp, '091494062d884b78a175ba5f5eb83840', 1, '0');
INSERT INTO SYS_MENU (ID, MENU_CODE, MENU_NAME, MENU_TYPE, MENU_INDEX, MENU_PATH, MENU_ICON, PARENT_ID, CREATED_BY, CREATED_TIME, UPDATED_BY, UPDATED_TIME, RANDOM_CODE, REVISION, DELETE_FLAG) VALUES (5, '1005', '字典类型', 'admin', 5, '/v1/user/dicttype', 'ee', '0', 1, current_timestamp, 1, current_timestamp, 'nsxm00bmi2kovyy8xu6az4kh8fm5cpz4', 1, '0');
INSERT INTO SYS_MENU (ID, MENU_CODE, MENU_NAME, MENU_TYPE, MENU_INDEX, MENU_PATH, MENU_ICON, PARENT_ID, CREATED_BY, CREATED_TIME, UPDATED_BY, UPDATED_TIME, RANDOM_CODE, REVISION, DELETE_FLAG) VALUES (6, '1006',    '字典', 'admin', 6, '/v1/user/dict', 'ff', '0', 1, current_timestamp, 1, current_timestamp, 'bard6oh810casf9irib4vaxwpn6yro1f', 1, '0');

#权限表
INSERT INTO SYS_PRIVILEGE (ID, PRIVILEGE_CODE, PRIVILEGE_NAME, PRIVILEGE_TYPE, ENTITY_TYPE, ENTITY_ID, REMARK, CREATED_BY, CREATED_TIME, UPDATED_BY, UPDATED_TIME, RANDOM_CODE, REVISION, DELETE_FLAG) VALUES (1, '1001', '用户管理', '', '', '', '', 1, current_timestamp, 1, current_timestamp, 'e53733002c434884b0baf6d0d7d1c6fa', 1, '0');
INSERT INTO SYS_PRIVILEGE (ID, PRIVILEGE_CODE, PRIVILEGE_NAME, PRIVILEGE_TYPE, ENTITY_TYPE, ENTITY_ID, REMARK, CREATED_BY, CREATED_TIME, UPDATED_BY, UPDATED_TIME, RANDOM_CODE, REVISION, DELETE_FLAG) VALUES (2, '1002', '角色管理', '', '', '', '', 1, current_timestamp, 1, current_timestamp, '60033f7a78c94107bfe8829538279304', 1, '0');
INSERT INTO SYS_PRIVILEGE (ID, PRIVILEGE_CODE, PRIVILEGE_NAME, PRIVILEGE_TYPE, ENTITY_TYPE, ENTITY_ID, REMARK, CREATED_BY, CREATED_TIME, UPDATED_BY, UPDATED_TIME, RANDOM_CODE, REVISION, DELETE_FLAG) VALUES (3, '1003', '权限管理', '', '', '', '', 1, current_timestamp, 1, current_timestamp, '1d77fff4d7454558b68696ed9dbff4b4', 1, '0');
INSERT INTO SYS_PRIVILEGE (ID, PRIVILEGE_CODE, PRIVILEGE_NAME, PRIVILEGE_TYPE, ENTITY_TYPE, ENTITY_ID, REMARK, CREATED_BY, CREATED_TIME, UPDATED_BY, UPDATED_TIME, RANDOM_CODE, REVISION, DELETE_FLAG) VALUES (4, '1004', '菜单管理', '', '', '', '', 1, current_timestamp, 1, current_timestamp, '5c73b00863df4a7f976e18e7fdfbedfa', 1, '0');

#角色表
INSERT INTO SYS_ROLE (ID, ROLE_CODE, ROLE_NAME, ROLE_TYPE, ROLE_STATUS, REMARK, CREATED_BY, CREATED_TIME, UPDATED_BY, UPDATED_TIME, RANDOM_CODE, REVISION, DELETE_FLAG) VALUES (1, '101', '超级管理员', 'admin', 1, '', 3, current_timestamp, 3, current_timestamp, 'a57bbc91dd5440d59329a5636ea87f8c', 1, '0');

#权限对应菜单
INSERT INTO SYS_PRIVILEGE_MENU (ID, PRIVILEGE_ID, MENU_ID, CREATED_BY, CREATED_TIME, UPDATED_BY, UPDATED_TIME, RANDOM_CODE, REVISION, DELETE_FLAG) VALUES (1, 1, 1, 1, current_timestamp, 1, current_timestamp, '310a43053cb54ad1804336677bfc4b1f', 1, '0');
INSERT INTO SYS_PRIVILEGE_MENU (ID, PRIVILEGE_ID, MENU_ID, CREATED_BY, CREATED_TIME, UPDATED_BY, UPDATED_TIME, RANDOM_CODE, REVISION, DELETE_FLAG) VALUES (2, 2, 2, 1, current_timestamp, 1, current_timestamp, '41159e15386a4fde95a1b06abb0348a6', 1, '0');
INSERT INTO SYS_PRIVILEGE_MENU (ID, PRIVILEGE_ID, MENU_ID, CREATED_BY, CREATED_TIME, UPDATED_BY, UPDATED_TIME, RANDOM_CODE, REVISION, DELETE_FLAG) VALUES (3, 3, 3, 1, current_timestamp, 1, current_timestamp, '01f67f138acb45318586eb7b0bd0b448', 1, '0');
INSERT INTO SYS_PRIVILEGE_MENU (ID, PRIVILEGE_ID, MENU_ID, CREATED_BY, CREATED_TIME, UPDATED_BY, UPDATED_TIME, RANDOM_CODE, REVISION, DELETE_FLAG) VALUES (4, 4, 4, 1, current_timestamp, 1, current_timestamp, 'e4e0820aed3c40fabb42a83198957b43', 1, '0');

#角色对应权限
INSERT INTO SYS_ROLE_PRIVILEGE (ID, ROLE_ID, PRIVILEGE_ID, CREATED_BY, CREATED_TIME, UPDATED_BY, UPDATED_TIME, RANDOM_CODE, REVISION, DELETE_FLAG) VALUES (1, 1, 1, 1, current_timestamp, 1, current_timestamp, '7f6e5cd22f1b4e518b92bbce751ddb4a', 1, '0');
INSERT INTO SYS_ROLE_PRIVILEGE (ID, ROLE_ID, PRIVILEGE_ID, CREATED_BY, CREATED_TIME, UPDATED_BY, UPDATED_TIME, RANDOM_CODE, REVISION, DELETE_FLAG) VALUES (2, 1, 2, 1, current_timestamp, 1, current_timestamp, 'f788838199db4dc5a6adcebad0560bae', 1, '0');
INSERT INTO SYS_ROLE_PRIVILEGE (ID, ROLE_ID, PRIVILEGE_ID, CREATED_BY, CREATED_TIME, UPDATED_BY, UPDATED_TIME, RANDOM_CODE, REVISION, DELETE_FLAG) VALUES (3, 1, 3, 1, current_timestamp, 1, current_timestamp, 'c6ba5b4b848647e184d5f06f1c73623d', 1, '0');
INSERT INTO SYS_ROLE_PRIVILEGE (ID, ROLE_ID, PRIVILEGE_ID, CREATED_BY, CREATED_TIME, UPDATED_BY, UPDATED_TIME, RANDOM_CODE, REVISION, DELETE_FLAG) VALUES (4, 1, 4, 1, current_timestamp, 1, current_timestamp, 'f86712fd78d349a4a75f3a0ec93609fb', 1, '0');

#用户(密码admin13579)
INSERT INTO SYS_USER (ID, USER_ACCOUNT, USER_PASSWORD, USER_TYPE, USER_NAME, EMAIL, PHONE, STATUS, CREATED_BY, CREATED_TIME, UPDATED_BY, UPDATED_TIME, RANDOM_CODE, REVISION, DELETE_FLAG) VALUES (1, 'admin', '$2a$10$lS3OC1.Y8mmHKYtqqm/BCuSlDGghVsTamsSzKa7b4CApiUV7zFmwS', 'admin', '平台管理员', '', '', 1, 1, current_timestamp, 1, current_timestamp, '4d0c38a093e047c5ad51f5d1baaf83a0', 1, '0');

#用户对应角色
INSERT INTO SYS_USER_ROLE (ID, USER_ID, ROLE_ID, CREATED_BY, CREATED_TIME, UPDATED_BY, UPDATED_TIME, RANDOM_CODE, REVISION, DELETE_FLAG) VALUES (1, 1, 1, 1, current_timestamp, 1, current_timestamp, '318c19ca621740cfabdd9aa3d8938edb', 1, '0');

commit;
