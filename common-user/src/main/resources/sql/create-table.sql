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
)
    comment '菜单';

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
)
    comment '权限';

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
)
    comment '权限对应菜单';

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
)
    comment '角色';

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
)
    comment '角色对应权限';

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
)
    comment '用户';

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
)
    comment '用户对应角色';
