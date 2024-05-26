/*
Navicat MySQL Data Transfer

Source Server         : local_mysql8
Source Server Version : 80034
Source Host           : localhost:3307
Source Database       : jeet

Target Server Type    : MYSQL
Target Server Version : 80034
File Encoding         : 65001

Date: 2023-09-12 13:37:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `course_simple_bank`
-- ----------------------------
DROP TABLE IF EXISTS `course_simple_bank`;
CREATE TABLE `course_simple_bank` (
  `id` bigint NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `details` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin,
  `status` char(1) COLLATE utf8mb4_bin DEFAULT NULL,
  `struct_id` bigint DEFAULT NULL,
  `remark` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of course_simple_bank
-- ----------------------------
