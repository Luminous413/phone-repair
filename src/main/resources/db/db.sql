/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80013
 Source Host           : localhost:3306
 Source Schema         : phone_repair

 Target Server Type    : MySQL
 Target Server Version : 80013
 File Encoding         : 65001

 Date: 07/09/2025 20:11:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for yjx_parts
-- ----------------------------
DROP TABLE IF EXISTS `yjx_parts`;
CREATE TABLE `yjx_parts`  (
                              `part_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '配件编号',
                              `part_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '配件名称',
                              `part_description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '配件描述',
                              `part_price` decimal(10, 2) NOT NULL COMMENT '配件价格',
                              `stock_quantity` int(11) NOT NULL DEFAULT 0 COMMENT '库存数量',
                              `supplier_id` int(11) NOT NULL COMMENT '供应商编号，引用 yjx_user(user_id)',
                              `created_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
                              `updated_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
                              PRIMARY KEY (`part_id`) USING BTREE,
                              UNIQUE INDEX `part_name`(`part_name`) USING BTREE,
                              INDEX `supplier_id`(`supplier_id`) USING BTREE,
                              CONSTRAINT `yjx_parts_ibfk_1` FOREIGN KEY (`supplier_id`) REFERENCES `yjx_user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of yjx_parts
-- ----------------------------
INSERT INTO `yjx_parts` VALUES (1, 'iPhone 13 Pro 屏幕', '适用于 iPhone 13 Pro 的屏幕', 300.00, 20, 5, '2025-09-04 10:52:31', '2025-09-05 20:33:13');
INSERT INTO `yjx_parts` VALUES (2, 'Samsung Galaxy S21 电池', '适用于 Samsung Galaxy S21 的电池', 150.00, 15, 5, '2025-09-04 10:52:31', '2025-09-04 10:52:31');
INSERT INTO `yjx_parts` VALUES (3, '摄像头', '适用于水果15', 400.00, 2, 8, '2025-09-06 17:55:34', '2025-09-06 17:55:34');
INSERT INTO `yjx_parts` VALUES (4, 'iPhone 14 电池', '适用于 iPhone 14 的原装品质电池', 180.00, 20, 8, '2025-09-06 17:58:02', '2025-09-06 17:58:02');
INSERT INTO `yjx_parts` VALUES (5, 'Xiaomi Mi 11 后置摄像头', '小米Mi 11主摄摄像头模块', 220.00, 8, 8, '2025-09-06 17:58:02', '2025-09-06 17:58:02');

-- ----------------------------
-- Table structure for yjx_repair_management
-- ----------------------------
DROP TABLE IF EXISTS `yjx_repair_management`;
CREATE TABLE `yjx_repair_management`  (
                                          `repair_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '维修管理编号',
                                          `repair_request_id` int(11) NOT NULL COMMENT '维修申请编号，引用 yjx_repair_request(request_id)',
                                          `technician_id` int(11) NOT NULL COMMENT '维修人员编号，引用 yjx_user(user_id)',
                                          `repair_price` decimal(10, 2) NOT NULL COMMENT '维修价格',
                                          `payment_status` enum('待支付','支付中','支付完成','支付异常') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '待支付' COMMENT '支付状态',
                                          `repair_notes` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '维修备注',
                                          `created_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '订单创建时间',
                                          `updated_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '订单更新时间',
                                          PRIMARY KEY (`repair_id`) USING BTREE,
                                          INDEX `repair_request_id`(`repair_request_id`) USING BTREE,
                                          INDEX `technician_id`(`technician_id`) USING BTREE,
                                          CONSTRAINT `yjx_repair_management_ibfk_1` FOREIGN KEY (`repair_request_id`) REFERENCES `yjx_repair_request` (`request_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                          CONSTRAINT `yjx_repair_management_ibfk_2` FOREIGN KEY (`technician_id`) REFERENCES `yjx_user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of yjx_repair_management
-- ----------------------------
INSERT INTO `yjx_repair_management` VALUES (1, 1, 4, 800.00, '待支付', '维修中，更换屏幕', '2025-09-04 17:13:35', '2025-09-04 17:13:35');
INSERT INTO `yjx_repair_management` VALUES (2, 2, 4, 500.00, '支付中', '更换电池', '2025-09-04 17:13:35', '2025-09-04 17:13:35');
INSERT INTO `yjx_repair_management` VALUES (4, 3, 1, 100.00, '待支付', '打地方', '2025-09-04 19:11:22', '2025-09-04 19:16:44');
INSERT INTO `yjx_repair_management` VALUES (5, 15, 1, 100.00, '待支付', '1234567890', '2025-09-05 20:25:07', '2025-09-05 20:25:07');

-- ----------------------------
-- Table structure for yjx_repair_request
-- ----------------------------
DROP TABLE IF EXISTS `yjx_repair_request`;
CREATE TABLE `yjx_repair_request`  (
                                       `request_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '维修单号',
                                       `user_id` int(11) NOT NULL COMMENT '提交订单的用户编号，引用 yjx_user(user_id)',
                                       `receptionist_id` int(11) NULL DEFAULT NULL COMMENT '接待人员编号，引用 yjx_user(user_id)',
                                       `phone_model` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手机型号',
                                       `phone_issue_description` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '问题描述',
                                       `request_status` int(11) NULL DEFAULT 1 COMMENT '维修状态，引用 yjx_repair_status(status_id)',
                                       `created_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '订单创建时间',
                                       `updated_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '订单更新时间',
                                       PRIMARY KEY (`request_id`) USING BTREE,
                                       INDEX `user_id`(`user_id`) USING BTREE,
                                       INDEX `receptionist_id`(`receptionist_id`) USING BTREE,
                                       INDEX `request_status`(`request_status`) USING BTREE,
                                       CONSTRAINT `yjx_repair_request_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `yjx_user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                       CONSTRAINT `yjx_repair_request_ibfk_2` FOREIGN KEY (`receptionist_id`) REFERENCES `yjx_user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                       CONSTRAINT `yjx_repair_request_ibfk_3` FOREIGN KEY (`request_status`) REFERENCES `yjx_repair_status` (`status_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of yjx_repair_request
-- ----------------------------
INSERT INTO `yjx_repair_request` VALUES (1, 2, 3, 'iPhone 13 Pro', '屏幕破损，需要更换屏幕', 1, '2025-09-04 10:52:31', '2025-09-04 10:52:31');
INSERT INTO `yjx_repair_request` VALUES (2, 2, 3, 'Samsung Galaxy S21', '电池续航能力严重下降', 2, '2025-09-04 10:52:31', '2025-09-04 10:52:31');
INSERT INTO `yjx_repair_request` VALUES (3, 2, 3, 'iPhone 14', '电池寿命下降，需要更换电池', 1, '2025-09-04 10:52:31', '2025-09-04 10:52:31');
INSERT INTO `yjx_repair_request` VALUES (4, 2, 3, 'Xiaomi Mi 11', '手机摄像头无法对焦', 2, '2025-09-04 10:52:31', '2025-09-04 10:52:31');
INSERT INTO `yjx_repair_request` VALUES (5, 3, 3, 'iPhone 12', '触摸屏失灵', 1, '2025-09-04 10:52:31', '2025-09-04 10:52:31');
INSERT INTO `yjx_repair_request` VALUES (6, 3, 3, 'OnePlus 9', '手机发热，自动关机', 4, '2025-09-04 10:52:31', '2025-09-04 10:52:31');
INSERT INTO `yjx_repair_request` VALUES (7, 2, 3, 'iPhone 14', '电池寿命下降，需要更换电池', 1, '2025-09-04 10:53:32', '2025-09-04 10:53:32');
INSERT INTO `yjx_repair_request` VALUES (8, 2, 3, 'Xiaomi Mi 11', '手机摄像头无法对焦', 2, '2025-09-04 10:53:32', '2025-09-04 10:53:32');
INSERT INTO `yjx_repair_request` VALUES (9, 3, 3, 'iPhone 12', '触摸屏失灵', 1, '2025-09-04 10:53:32', '2025-09-04 10:53:32');
INSERT INTO `yjx_repair_request` VALUES (11, 4, 3, 'iPhone 13', '手机无法开机', 2, '2025-09-04 10:53:32', '2025-09-04 10:53:32');
INSERT INTO `yjx_repair_request` VALUES (12, 4, 3, 'Huawei P40', '手机屏幕碎裂', 3, '2025-09-04 10:53:32', '2025-09-04 10:53:32');
INSERT INTO `yjx_repair_request` VALUES (13, 5, 3, 'Samsung Galaxy S22', '电池膨胀，手机后盖破损', 4, '2025-09-04 10:53:32', '2025-09-04 10:53:32');
INSERT INTO `yjx_repair_request` VALUES (14, 5, 3, 'iPhone 14 Pro', '系统运行缓慢', 1, '2025-09-04 10:53:32', '2025-09-04 10:53:32');
INSERT INTO `yjx_repair_request` VALUES (15, 1, 3, 'Google Pixel 5', '无法充电', 2, '2025-09-04 10:53:32', '2025-09-04 10:53:32');
INSERT INTO `yjx_repair_request` VALUES (16, 1, 3, 'iPhone SE', '屏幕掉漆', 1, '2025-09-04 10:53:32', '2025-09-04 10:53:32');
INSERT INTO `yjx_repair_request` VALUES (17, 2, 3, 'Xiaomi Mi 10', 'WiFi连接不稳定', 4, '2025-09-04 10:53:32', '2025-09-04 10:53:32');
INSERT INTO `yjx_repair_request` VALUES (18, 3, 3, 'Samsung Galaxy Note 20', '音量键失灵', 1, '2025-09-04 10:53:32', '2025-09-04 10:53:32');
INSERT INTO `yjx_repair_request` VALUES (19, 4, 3, 'Oppo Find X3', '闪光灯不工作', 3, '2025-09-04 10:53:32', '2025-09-04 10:53:32');
INSERT INTO `yjx_repair_request` VALUES (20, 5, 3, 'Realme GT', '频繁死机', 2, '2025-09-04 10:53:32', '2025-09-04 10:53:32');
INSERT INTO `yjx_repair_request` VALUES (21, 1, 3, 'Nokia 8.3', '系统崩溃，无法启动', 4, '2025-09-04 10:53:32', '2025-09-04 10:53:32');

-- ----------------------------
-- Table structure for yjx_repair_status
-- ----------------------------
DROP TABLE IF EXISTS `yjx_repair_status`;
CREATE TABLE `yjx_repair_status`  (
                                      `status_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '状态编号',
                                      `status_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '状态名称',
                                      `status_description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '状态描述',
                                      PRIMARY KEY (`status_id`) USING BTREE,
                                      UNIQUE INDEX `status_name`(`status_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of yjx_repair_status
-- ----------------------------
INSERT INTO `yjx_repair_status` VALUES (1, '提交订单', '订单已提交，等待处理');
INSERT INTO `yjx_repair_status` VALUES (2, '正在维修', '订单正在维修中');
INSERT INTO `yjx_repair_status` VALUES (3, '维修成功等待取件', '维修完成，等待客户取件');
INSERT INTO `yjx_repair_status` VALUES (4, '订单已完成', '订单已完成并归档');
INSERT INTO `yjx_repair_status` VALUES (5, '订单异常', '订单状态异常，需要管理员处理');

-- ----------------------------
-- Table structure for yjx_role
-- ----------------------------
DROP TABLE IF EXISTS `yjx_role`;
CREATE TABLE `yjx_role`  (
                             `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色编号',
                             `role_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
                             `role_description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '角色描述',
                             PRIMARY KEY (`role_id`) USING BTREE,
                             UNIQUE INDEX `role_name`(`role_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of yjx_role
-- ----------------------------
INSERT INTO `yjx_role` VALUES (1, 'admin', '管理员，负责系统管理');
INSERT INTO `yjx_role` VALUES (2, 'user', '普通用户，提交维修申请');
INSERT INTO `yjx_role` VALUES (3, 'receptionist', '前台接待，负责接待客户和订单创建');
INSERT INTO `yjx_role` VALUES (4, 'technician', '维修人员，负责维修任务');
INSERT INTO `yjx_role` VALUES (5, 'supplier', '供应商，提供零件和设备');

-- ----------------------------
-- Table structure for yjx_supplier_management
-- ----------------------------
DROP TABLE IF EXISTS `yjx_supplier_management`;
CREATE TABLE `yjx_supplier_management`  (
                                            `supplier_management_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '供应商管理编号',
                                            `supplier_id` int(11) NOT NULL COMMENT '供应商编号',
                                            `part_id` int(11) NOT NULL COMMENT '配件编号',
                                            `supply_quantity` int(11) NULL DEFAULT NULL COMMENT '供应数量',
                                            `created_at` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
                                            `updated_at` timestamp(0) NULL DEFAULT NULL COMMENT '更新时间',
                                            PRIMARY KEY (`supplier_management_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of yjx_supplier_management
-- ----------------------------
INSERT INTO `yjx_supplier_management` VALUES (1, 8, 2, 100, '2025-09-05 17:06:12', '2025-09-06 17:54:43');
INSERT INTO `yjx_supplier_management` VALUES (3, 8, 1, 80, '2025-09-05 17:06:12', '2025-09-06 17:54:45');
INSERT INTO `yjx_supplier_management` VALUES (4, 8, 3, 3, '2025-09-05 17:13:48', '2025-09-06 17:54:40');
INSERT INTO `yjx_supplier_management` VALUES (9, 8, 4, 9, '2025-09-05 17:16:28', '2025-09-06 17:54:37');

-- ----------------------------
-- Table structure for yjx_user
-- ----------------------------
DROP TABLE IF EXISTS `yjx_user`;
CREATE TABLE `yjx_user`  (
                             `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户编号',
                             `user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
                             `user_email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户邮箱',
                             `user_password_hash` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码哈希',
                             `role_id` int(11) NOT NULL COMMENT '角色编号，引用 yjx_role(role_id)',
                             `user_bio` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '用户简介',
                             `user_phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
                             `user_gender` enum('male','female','secret') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'secret' COMMENT '性别',
                             `user_last_active` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '上次活跃时间',
                             `user_created_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
                             `user_status` enum('active','inactive') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'active' COMMENT '账户状态',
                             PRIMARY KEY (`user_id`) USING BTREE,
                             UNIQUE INDEX `user_name`(`user_name`) USING BTREE,
                             UNIQUE INDEX `user_email`(`user_email`) USING BTREE,
                             INDEX `role_id`(`role_id`) USING BTREE,
                             CONSTRAINT `yjx_user_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `yjx_role` (`role_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of yjx_user
-- ----------------------------
INSERT INTO `yjx_user` VALUES (1, 'xiongzheng', 'xzjob@xxx.com', '2b6a4907a9638ac73f693559d6d2de69', 1, '我是管理员熊峥', '17600000000', 'male', '2025-09-04 10:52:31', '2025-09-04 10:52:31', 'active');
INSERT INTO `yjx_user` VALUES (2, 'zhangsan', 'zhangsan@xxx.com', '2b6a4907a9638ac73f693559d6d2de69', 2, '普通用户张三，提交维修申请', '13800000000', 'male', '2025-09-04 10:52:31', '2025-09-04 10:52:31', 'active');
INSERT INTO `yjx_user` VALUES (3, 'qiantai', 'qiantai@xxx.com', '2b6a4907a9638ac73f693559d6d2de69', 3, '我是前台接待员', '15500000000', 'female', '2025-09-04 10:52:31', '2025-09-04 10:52:31', 'active');
INSERT INTO `yjx_user` VALUES (4, 'alice123', 'alice123@xxx.com', '2b6a4907a9638ac73f693559d6d2de69', 2, '普通用户Alice，提交维修申请', '17700000001', 'female', '2025-09-04 10:52:31', '2025-09-04 10:52:31', 'active');
INSERT INTO `yjx_user` VALUES (5, 'bob456', 'bob456@xxx.com', '2b6a4907a9638ac73f693559d6d2de69', 2, '普通用户Bob，提交维修申请', '17800000002', 'male', '2025-09-04 10:52:31', '2025-09-04 10:52:31', 'active');
INSERT INTO `yjx_user` VALUES (6, '妹妹', 'charlie789@xxx.com', '2b6a4907a9638ac73f693559d6d2de69', 3, '前台小妹', '17900000003', 'male', '2025-09-04 10:52:31', '2025-09-04 10:52:31', 'active');
INSERT INTO `yjx_user` VALUES (7, 'david012', 'david012@xxx.com', '2b6a4907a9638ac73f693559d6d2de69', 2, '普通用户David，提交维修申请', '18000000004', 'male', '2025-09-04 10:52:31', '2025-09-04 10:52:31', 'active');
INSERT INTO `yjx_user` VALUES (8, 'ellen345', 'ellen345@xxx.com', '2b6a4907a9638ac73f693559d6d2de69', 5, '供应商', '18100000005', 'female', '2025-09-04 10:52:31', '2025-09-04 10:52:31', 'active');
INSERT INTO `yjx_user` VALUES (11, 'yyzs', '2943@qq.com', '68fcd047c78b0d6a3347eb1331f76656', 1, '芸芸众生', '15872988026', 'secret', '2025-09-05 14:28:03', '2025-09-05 14:28:03', 'active');

SET FOREIGN_KEY_CHECKS = 1;
