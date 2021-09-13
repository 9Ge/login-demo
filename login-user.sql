/*
 Navicat Premium Data Transfer

 Source Server         : shenjianchao
 Source Server Type    : MySQL
 Source Server Version : 50727
 Source Host           : 192.168.3.34:3306
 Source Schema         : enercomndeviced

 Target Server Type    : MySQL
 Target Server Version : 50727
 File Encoding         : 65001

 Date: 13/09/2021 17:27:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `sex` int(255) DEFAULT NULL COMMENT '性别',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '姓名',
  `pwd` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `role` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
## 加密之前的密码是 1234
INSERT INTO `user` VALUES ('c8d130454d994a0aa7ec2b606ba17982', NULL, '杨成3', '$2a$10$gWQj63JBpXz1TtHsvWPL6u2WTG6UGYmzkOoXboDpq6K2vwrYYmlhK', 'ROLE_ADMIN');
INSERT INTO `user` VALUES ('c8d130454d994a0aa7ec2b606ba17987', NULL, '杨成222', '$2a$10$gWQj63JBpXz1TtHsvWPL6u2WTG6UGYmzkOoXboDpq6K2vwrYYmlhK', 'ROLE_USER');

SET FOREIGN_KEY_CHECKS = 1;
