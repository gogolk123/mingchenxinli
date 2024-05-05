CREATE DATABASE mingchenxinli;
USE mingchenxinli;

CREATE TABLE `Counters` (
                            `id` int(11) NOT NULL AUTO_INCREMENT,
                            `count` int(11) NOT NULL DEFAULT '1',
                            `createdAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            `updatedAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

CREATE TABLE `user` (
                        `id` bigint unsigned NOT NULL COMMENT 'Id',
                        `user_id` bigint NOT NULL DEFAULT 0 COMMENT '用户id',
                        `extra` text  COMMENT '扩展字段',
                        `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                        `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `uk_user_id` (`user_id`)

) ENGINE = InnoDB DEFAULT CHARSET=utf8 COMMENT = '用户表';


CREATE TABLE `user_relation` (
                             `id` bigint unsigned NOT NULL COMMENT 'Id',
                             `user_id` bigint NOT NULL DEFAULT 0 COMMENT '用户id',
                             `out_id` varchar(64)  NOT NULL DEFAULT '' COMMENT '外部ID',
                             `out_id_type` int NOT NULL DEFAULT '0' COMMENT '外部id类型,1 wx_open_id',
                             `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                             PRIMARY KEY (`id`),
                             UNIQUE KEY `uk_out_id_type` (`out_id`, `out_id_type`)

) ENGINE = InnoDB DEFAULT CHARSET=utf8 COMMENT = '用户关联表';

CREATE TABLE `counselor` (
                             `id` bigint unsigned NOT NULL COMMENT 'Id',
                             `counselor_id` varchar(64)  NOT NULL DEFAULT '' COMMENT '咨询师id',
                             `user_id` bigint NOT NULL DEFAULT 0 COMMENT '用户id',

                             `name` varchar(64)  NOT NULL COMMENT '名字',
                             `edu` varchar(128)  NOT NULL COMMENT '学历',
                             `address` varchar(256)  NOT NULL COMMENT '地址',
                             `extra` text  COMMENT '扩展字段',
                             `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                             PRIMARY KEY (`id`),
                             UNIQUE KEY `uk_counselor_id` (`counselor_id`),
                             KEY `user_id` (`user_id`)

) ENGINE = InnoDB DEFAULT CHARSET=utf8 COMMENT = '咨询师表';

CREATE TABLE `counseling` (
                              `id` bigint unsigned NOT NULL COMMENT 'Id',
                              `counseling_id` bigint unsigned NOT NULL COMMENT '咨询信息id',
                              `counselor_id` varchar(64)  NOT NULL DEFAULT '' COMMENT '咨询师id',
                              `duration` int NOT NULL DEFAULT '0' COMMENT '咨询时长',
                              `fee` int NOT NULL DEFAULT '0' COMMENT '咨询费用',
                              `way` int NOT NULL DEFAULT '1' COMMENT '咨询方式:1线上，2线下',
                              `effect_start_date` timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '生效开始时间',
                              `effect_end_date` timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '生效结束',
                              `extra` text  COMMENT '扩展字段',
                              `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                              PRIMARY KEY (`id`),
                              UNIQUE KEY `uk_counseling_id` (`counseling_id`),
                              UNIQUE KEY `uk_counselor_id_way` (`counselor_id`, `effect_start_date`, `effect_end_date`,`way`)
) ENGINE = InnoDB DEFAULT CHARSET=utf8 COMMENT = '咨询师预约表';


CREATE TABLE `order_base` (
                              `id` bigint unsigned NOT NULL COMMENT 'Id',
                              `order_id` varchar(64)  NOT NULL DEFAULT '' COMMENT '订单id',
                              `duration` int NOT NULL DEFAULT '0' COMMENT '咨询时长',
                              `fee` int NOT NULL DEFAULT '0' COMMENT '咨询费用',
                              `way` int NOT NULL DEFAULT '0' COMMENT '咨询方式:1线上，2线下',
                              `user_id` bigint NOT NULL DEFAULT 0 COMMENT '用户id',
                              `unit_period_key` varchar(64) NOT NULL DEFAULT '0' COMMENT '时间标识',
                              `visitor_id` varchar(64)  NOT NULL DEFAULT '' COMMENT '来访者id',
                              `counselor_id` varchar(64)  NOT NULL DEFAULT '' COMMENT '咨询师id',
                              `biz_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '业务时间',
                              `status` int NOT NULL DEFAULT 0 COMMENT '0待支付，1，已支付，-1 已退款',
                              `extra` text  COMMENT '扩展字段', -- {"start_time":timestamp1, "end_time":timestamp2}
                              `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',


                              PRIMARY KEY (`id`),
                              UNIQUE KEY `uk_order_id` (`order_id`),
                              KEY `idx_user_id_biz_date` (
                                                          `user_id`,
                                                          `biz_date`
                                  ),
                              KEY `idx_counselor_id_biz_date` (
                                                               `counselor_id`,
                                                               `biz_date`
                                  )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 COMMENT = '订单表';


CREATE TABLE `visitor` (
                           `id` bigint unsigned NOT NULL COMMENT 'Id',
                           `visitor_id` varchar(64)  NOT NULL DEFAULT '' COMMENT '来访者id',
                           `user_id` bigint NOT NULL DEFAULT 0 COMMENT '用户id',
                           `name` varchar(64)  NOT NULL COMMENT '名字',
                           `phone` varchar(128)  NOT NULL COMMENT '电话',
                           `born` varchar(256)  NOT NULL COMMENT '出生日期',
                           `gender` varchar(256)  NOT NULL COMMENT '性别',
                           `is_first_visit` text  COMMENT '是否首次来访',
                           `extra` text  COMMENT '扩展字段',
                           `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                           PRIMARY KEY (`id`),
                           UNIQUE KEY `uk_visitor_id` (`visitor_id`),
                           KEY `user_id` (`user_id`)

) ENGINE = InnoDB DEFAULT CHARSET = utf8 COMMENT = '来访者表'
