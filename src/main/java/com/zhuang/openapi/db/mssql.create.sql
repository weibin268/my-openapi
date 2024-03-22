-- auto-generated definition
create table sys_openapi_user
(
    id          varchar(50) not null
        constraint sys_openapi_user_pk
            primary key,
    username    varchar(50),
    password    varchar(50),
    remark      varchar(500),
    status      int,
    create_time datetime,
    create_by   varchar(50),
    modify_time datetime,
    modify_by   varchar(50)
)
go

exec sp_addextendedproperty 'MS_Description', 'OpenApi用户表', 'SCHEMA', 'dbo', 'TABLE', 'sys_openapi_user'
go

exec sp_addextendedproperty 'MS_Description', 'ID主键', 'SCHEMA', 'dbo', 'TABLE', 'sys_openapi_user', 'COLUMN', 'id'
go

exec sp_addextendedproperty 'MS_Description', '用户名', 'SCHEMA', 'dbo', 'TABLE', 'sys_openapi_user', 'COLUMN', 'username'
go

exec sp_addextendedproperty 'MS_Description', '密码', 'SCHEMA', 'dbo', 'TABLE', 'sys_openapi_user', 'COLUMN', 'password'
go

exec sp_addextendedproperty 'MS_Description', '备注', 'SCHEMA', 'dbo', 'TABLE', 'sys_openapi_user', 'COLUMN', 'remark'
go

exec sp_addextendedproperty 'MS_Description', '状态：0=禁用；1=启用；', 'SCHEMA', 'dbo', 'TABLE', 'sys_openapi_user', 'COLUMN',
     'status'
go

exec sp_addextendedproperty 'MS_Description', '创建时间', 'SCHEMA', 'dbo', 'TABLE', 'sys_openapi_user', 'COLUMN',
     'create_time'
go

exec sp_addextendedproperty 'MS_Description', '创建人', 'SCHEMA', 'dbo', 'TABLE', 'sys_openapi_user', 'COLUMN', 'create_by'
go

exec sp_addextendedproperty 'MS_Description', '修改时间', 'SCHEMA', 'dbo', 'TABLE', 'sys_openapi_user', 'COLUMN',
     'modify_time'
go

exec sp_addextendedproperty 'MS_Description', '修改人', 'SCHEMA', 'dbo', 'TABLE', 'sys_openapi_user', 'COLUMN', 'modify_by'
go

------------------------------------------------------------------------------------------------------------------------------------------
-- auto-generated definition
create table sys_openapi_user_ref
(
    id              int identity
        constraint sys_openapi_user_ref_pk
        primary key,
    openapi_user_id varchar(50),
    ref_table       varchar(50),
    ref_id          varchar(50)
)
go

exec sp_addextendedproperty 'MS_Description', 'OpenApi用户资源关联表', 'SCHEMA', 'dbo', 'TABLE', 'sys_openapi_user_ref'
go

exec sp_addextendedproperty 'MS_Description', 'ID', 'SCHEMA', 'dbo', 'TABLE', 'sys_openapi_user_ref', 'COLUMN', 'id'
go

exec sp_addextendedproperty 'MS_Description', 'OpenApi用户Id', 'SCHEMA', 'dbo', 'TABLE', 'sys_openapi_user_ref', 'COLUMN',
     'openapi_user_id'
go

exec sp_addextendedproperty 'MS_Description', '关联表名', 'SCHEMA', 'dbo', 'TABLE', 'sys_openapi_user_ref', 'COLUMN',
     'ref_table'
go

exec sp_addextendedproperty 'MS_Description', '关联记录Id', 'SCHEMA', 'dbo', 'TABLE', 'sys_openapi_user_ref', 'COLUMN',
     'ref_id'
go


------------------------------------------------------------------------------------------------------------------------------------------


-- auto-generated definition
create table sys_openapi_log
(
    id                bigint identity
        constraint sys_openapi_log_pk
        primary key,
    api_name          varchar(100),
    api_params        varchar(1000),
    api_execute_times int,
    client_ip         varchar(50),
    create_time       datetime,
    openapi_user_id   varchar(50)
)
go

exec sp_addextendedproperty 'MS_Description', 'OpenApi调用日志', 'SCHEMA', 'dbo', 'TABLE', 'sys_openapi_log'
go

exec sp_addextendedproperty 'MS_Description', 'ID', 'SCHEMA', 'dbo', 'TABLE', 'sys_openapi_log', 'COLUMN', 'id'
go

exec sp_addextendedproperty 'MS_Description', 'api名字', 'SCHEMA', 'dbo', 'TABLE', 'sys_openapi_log', 'COLUMN', 'api_name'
go

exec sp_addextendedproperty 'MS_Description', 'api参数值', 'SCHEMA', 'dbo', 'TABLE', 'sys_openapi_log', 'COLUMN',
     'api_params'
go

exec sp_addextendedproperty 'MS_Description', '接口执行时间（单位：毫秒）', 'SCHEMA', 'dbo', 'TABLE', 'sys_openapi_log', 'COLUMN',
     'api_execute_times'
go

exec sp_addextendedproperty 'MS_Description', '客户端ip', 'SCHEMA', 'dbo', 'TABLE', 'sys_openapi_log', 'COLUMN',
     'client_ip'
go
exec sp_addextendedproperty 'MS_Description', '创建时间', 'SCHEMA', 'dbo', 'TABLE', 'sys_openapi_log', 'COLUMN',
     'create_time'
go





