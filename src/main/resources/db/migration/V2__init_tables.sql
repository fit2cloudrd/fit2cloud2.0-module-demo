CREATE TABLE IF NOT EXISTS `demo` (
  `id` varchar(50) NOT NULL COMMENT 'ID',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `create_time` bigint(15) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;