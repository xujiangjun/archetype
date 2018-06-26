-- 查询数据推荐：SQLyog、Sequel Pro
-- 导出数据推荐：Navicat Premium
-- 书写SQL推荐：DataGrip

-- 参数配置表
drop table if exists c_param_config;
create table `c_param_config` (
  `id` int unsigned not null auto_increment comment '自增主键',
  `is_deleted` tinyint unsigned default '0' comment '是否删除：0-未删除; 1-已删除',
  `creator` varchar(16) not null default '' comment '创建人',
  `gmt_create` datetime not null comment '创建时间',
  `modifier` varchar(16) not null default '' comment '修改人',
  `gmt_modified` datetime not null comment '修改时间',
  `param_name` varchar(24) not null comment '参数描述',
  `param_no` varchar(32) not null comment '参数编码',
  `param_value` varchar(255) not null comment '参数值',
  `remark` varchar(64) default null comment '备注',
  primary key (`id`),
  key `idx_param_no` (`param_no`)
) engine=innodb default charset=utf8 comment='参数配置表';

-- 初始化数据
insert into c_param_config values (1, 0, 'System', now(), 'System', now(), 'IP白名单配置项', 'c_ip_white_list', '183.129.150.18', '如通过数据库直接变更ip白名单，需要调用刷新缓存接口');