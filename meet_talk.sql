/*
 Navicat MySQL Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : localhost:3306
 Source Schema         : meet_talk

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 11/09/2022 19:52:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `a_id` int(0) NOT NULL AUTO_INCREMENT,
  `u_id` int(0) NULL DEFAULT NULL,
  `title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `summary` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `content` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`a_id`) USING BTREE,
  INDEX `u_id`(`u_id`) USING BTREE,
  INDEX `idx_article_title`(`title`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES (1, 1, 'Java程序员在线秃头', '简介1', '**分配空间:**\r\n\r\n创建ByteBuffer:\r\n\r\n```java\r\nSystem.out.println(ByteBuffer.allocate(10).getClass());\r\nSystem.out.println(ByteBuffer.allocateDirect(10).getClass());\r\n\r\n\r\n#打印结果:\r\nclass java.nio.HeapByteBuffer\r\nclass java.nio.DirectByteBuffer\r\n```\r\n\r\n**HeapByteBuffer**:存在于java的堆内存中,会收到gc的影响,并且当jvm内存不足的时候会对其进行垃圾回收的处理,比如标记整理复制等等,', '2022-07-28 20:32:11');
INSERT INTO `article` VALUES (2, 4, '如何快乐的敲代码', '简介测试', '多次传输:\r\n\r\n```java\r\ntry (FileChannel from = new FileInputStream(\"data.txt\").getChannel();\r\n     FileChannel to = new FileOutputStream(\"to.txt\").getChannel()\r\n) {\r\n    \r\n    long size=from.size();\r\n    for (long left=size;left>0;){\r\n        left-= from.transferTo((size-left),left,to);\r\n    }\r\n\r\n} catch (IOException e) {\r\n    e.printStackTrace();\r\n}\r\n```\r\n\r\n### Path', '2022-07-28 20:15:11');
INSERT INTO `article` VALUES (3, 2, '刷题小技巧', '1', '### selector\r\n\r\n**非阻塞存在的缺点:因为一直是while(true) 即使是没有连接或者是数据读取,程序也一直在不断的循环运行**\r\n\r\n```\r\n//通过SelectionKey可以获得事件\r\nSelectionKey selectionKey = ssc.register(selector, 0, null);\r\n```\r\n\r\n**四种事件:**\r\n\r\n1.accept:会在有连接请求时触发\r\n\r\n2.connect:客户端连接建立后触发\r\n\r\n3.read:可读事件\r\n\r\n4.write:可写事件\r\n\r\n**当事件发生的时候,要么处理,要么取消**', '2022-07-28 20:21:11');
INSERT INTO `article` VALUES (4, 4, '如何提升', '1321', '#### 处理read\r\n\r\n**当注册的时候,selector中就保存着ssc与其对应的事件**\r\n\r\n```\r\n //创建服务器\r\n ServerSocketChannel ssc = ServerSocketChannel.open();\r\n//通过SelectionKey可以获得事件\r\nSelectionKey selectionKey = ssc.register(selector, 0, null);\r\n//只关注accept事件\r\nselectionKey.interestOps(SelectionKey.OP_ACCEPT);\r\n```\r\n\r\n**并且 selector.selectedKeys()中也存有ssc与其对应事件,并且不会消失,只是当处理其对应事件之后, selector.selectedKeys()集合中会消除其对应的事件,**', '2022-07-28 20:54:11');
INSERT INTO `article` VALUES (5, 4, '谁怕?一蓑烟雨任平生', '424', '#### 处理客户端断开\r\n\r\n**都是调用key.cancel 直接彻底删除selector中的对象**\r\n\r\n当不正常断开,也就是程序异常关闭,在异常发生的时候进行处理\r\n\r\n当socketchannel正常断开:调用sc.close,通过判断sc.read是否=-1', '2022-07-28 20:40:11');
INSERT INTO `article` VALUES (6, 3, '稻香', '周杰伦', '#### 附件与扩容\r\n\r\n上面处理消息边界的时候存在一个问题,也就是每一个客户端都应该有一个bytebuffer,因此用到了附件\r\n\r\n也就是注册到selector时的第三个参数\r\n\r\n```\r\nsocketChannel.register(selector, SelectionKey.OP_READ,buffer);\r\n```\r\n\r\n获取附件:\r\n\r\n```\r\nByteBuffer byteBuffer =(ByteBuffer) key.attachment();\r\n```\r\n\r\n#### byteBuffer大小分配', '2022-07-30 19:50:11');
INSERT INTO `article` VALUES (7, 4, '测试标题', '测试简介', '### 处理结果\n\n第一种就是使用 **.sync()**方法进行同步, 等待的是当前线程,异步的是nio线程\n\n\n\n第二种就是使用  **addListener(回调对象 )** 进行异步处理结果,当前线程不进行处理只需在有结果返回时进行处理\n\n```java\nchannelFuture.addListener(new ChannelFutureListener() {\n    @Override\n    //在nio线程建立连接完成后调用此方法\n    public void operationComplete(ChannelFuture channelFuture) throws Exception {\n        Channel channel = channelFuture.channel();\n        channel.writeAndFlush(\"nihao!\");\n    }\n});\n```\n\n### 关闭问题', '2022-09-06 15:06:30');
INSERT INTO `article` VALUES (8, 4, '22.9.6', '今天也是在上网课', '### future\n\nJDK的future:\n\n```java\n//创建线程池\nExecutorService service = Executors.newFixedThreadPool(2);\n//提交任务\nFuture<Integer> future = service.submit(new Callable<Integer>() {\n    @Override\n    public Integer call() throws Exception {\n        //模拟计算出数据 50\n        System.out.println(  \"正在计算....\");\n        Thread.sleep(1000);\n        return 50;\n    }\n});\n//主线程通过future对象获取线程池内线程计算出的数据\n//get方法阻塞  同步\nInteger res = future.get();\nSystem.out.println(\"获取到的数据是: \"+res);\n```\n\nnetty的Future', '2022-09-06 15:12:25');

-- ----------------------------
-- Table structure for attention
-- ----------------------------
DROP TABLE IF EXISTS `attention`;
CREATE TABLE `attention`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `follow_u_id` int(0) NULL DEFAULT NULL,
  `u_id` int(0) NULL DEFAULT NULL,
  `status` int(0) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `u_id`(`u_id`) USING BTREE,
  INDEX `follow_u_id`(`follow_u_id`) USING BTREE,
  CONSTRAINT `attention_ibfk_1` FOREIGN KEY (`u_id`) REFERENCES `user` (`u_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `attention_ibfk_2` FOREIGN KEY (`follow_u_id`) REFERENCES `user` (`u_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of attention
-- ----------------------------
INSERT INTO `attention` VALUES (3, 3, 4, 0);
INSERT INTO `attention` VALUES (4, 5, 4, 0);
INSERT INTO `attention` VALUES (5, 2, 1, 0);
INSERT INTO `attention` VALUES (6, 3, 2, 0);
INSERT INTO `attention` VALUES (7, 1, 3, 0);
INSERT INTO `attention` VALUES (9, 4, 2, 0);
INSERT INTO `attention` VALUES (25, 4, 1, 0);
INSERT INTO `attention` VALUES (29, 1, 4, 0);

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `c_id` int(0) NOT NULL AUTO_INCREMENT,
  `u_id` int(0) NULL DEFAULT NULL,
  `a_id` int(0) NULL DEFAULT NULL,
  `comment` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `root` int(0) NULL DEFAULT NULL,
  `root_c_id` bigint(0) NULL DEFAULT NULL,
  `parent_u_id` bigint(0) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`c_id`) USING BTREE,
  INDEX `comment_ibfk_1`(`u_id`) USING BTREE,
  INDEX `commment_article_ibfk_2`(`a_id`) USING BTREE,
  CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`u_id`) REFERENCES `user` (`u_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `commment_article_ibfk_2` FOREIGN KEY (`a_id`) REFERENCES `article` (`a_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (1, 1, 1, '评论', 1, NULL, NULL, '2022-07-27 20:32:50');
INSERT INTO `comment` VALUES (2, 2, 1, 'AAA', 0, 1, 1, '2022-07-27 20:32:50');
INSERT INTO `comment` VALUES (3, 3, 1, '评论3', 0, 1, 2, '2022-07-27 20:32:50');
INSERT INTO `comment` VALUES (4, 4, 1, 'AAAA', 0, 1, 3, '2022-07-29 10:31:53');
INSERT INTO `comment` VALUES (5, 4, 1, '11', 0, 1, 3, '2022-07-29 10:32:02');
INSERT INTO `comment` VALUES (6, 4, 1, 'A', 1, NULL, NULL, '2022-07-29 10:35:56');
INSERT INTO `comment` VALUES (8, 4, 1, '测试', 0, 1, 2, '2022-07-29 16:34:01');
INSERT INTO `comment` VALUES (9, 1, 6, 'aaaa', 1, NULL, NULL, '2022-08-06 19:46:16');
INSERT INTO `comment` VALUES (10, 1, 4, '哈哈哈', 1, NULL, NULL, '2022-08-06 19:46:20');
INSERT INTO `comment` VALUES (11, 1, 5, '嘻嘻嘻', 1, NULL, NULL, '2022-08-06 19:46:24');
INSERT INTO `comment` VALUES (12, 1, 3, '嘿嘿嘿', 1, NULL, NULL, '2022-08-06 19:46:32');
INSERT INTO `comment` VALUES (13, 4, 3, '稻香', 0, 12, 1, '2022-08-30 18:44:27');
INSERT INTO `comment` VALUES (20, 2, 3, '芜湖', 1, NULL, NULL, '2022-09-04 18:57:32');
INSERT INTO `comment` VALUES (21, 2, 3, '啊哈哈', 1, NULL, NULL, '2022-09-04 18:58:00');
INSERT INTO `comment` VALUES (22, 2, 6, '?', 1, NULL, NULL, '2022-09-04 18:59:05');
INSERT INTO `comment` VALUES (24, 2, 6, '11', 1, NULL, NULL, '2022-09-04 18:59:48');
INSERT INTO `comment` VALUES (25, 2, 3, '你在干嘛', 0, 12, 4, '2022-09-04 19:04:17');
INSERT INTO `comment` VALUES (26, 2, 3, '??', 0, 12, 4, '2022-09-04 19:05:29');
INSERT INTO `comment` VALUES (27, 2, 2, '111', 1, NULL, NULL, '2022-09-04 19:08:20');

-- ----------------------------
-- Table structure for favorites
-- ----------------------------
DROP TABLE IF EXISTS `favorites`;
CREATE TABLE `favorites`  (
  `f_id` int(0) NOT NULL AUTO_INCREMENT,
  `f_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `u_id` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`f_id`) USING BTREE,
  INDEX `favor_ibfk_1`(`u_id`) USING BTREE,
  CONSTRAINT `favor_ibfk_1` FOREIGN KEY (`u_id`) REFERENCES `user` (`u_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of favorites
-- ----------------------------
INSERT INTO `favorites` VALUES (2, '千寻的java', 4);
INSERT INTO `favorites` VALUES (9, '小千的收藏夹', 1);
INSERT INTO `favorites` VALUES (10, '千寻的收藏夹', 4);
INSERT INTO `favorites` VALUES (14, '学习java', 2);

-- ----------------------------
-- Table structure for favorites_article
-- ----------------------------
DROP TABLE IF EXISTS `favorites_article`;
CREATE TABLE `favorites_article`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `f_id` int(0) NULL DEFAULT NULL,
  `a_id` int(0) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `f_id`(`f_id`) USING BTREE,
  INDEX `a_id`(`a_id`) USING BTREE,
  CONSTRAINT `favorites_article_ibfk_1` FOREIGN KEY (`f_id`) REFERENCES `favorites` (`f_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `favorites_article_ibfk_2` FOREIGN KEY (`a_id`) REFERENCES `article` (`a_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of favorites_article
-- ----------------------------
INSERT INTO `favorites_article` VALUES (21, 9, 2, '2022-08-30 19:15:51');
INSERT INTO `favorites_article` VALUES (24, 10, 2, '2022-08-30 19:53:36');
INSERT INTO `favorites_article` VALUES (26, 10, 3, '2022-08-30 19:53:37');

-- ----------------------------
-- Table structure for praise
-- ----------------------------
DROP TABLE IF EXISTS `praise`;
CREATE TABLE `praise`  (
  `p_id` int(0) NOT NULL AUTO_INCREMENT,
  `a_id` int(0) NULL DEFAULT NULL,
  `u_id` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`p_id`) USING BTREE,
  INDEX `a_id`(`a_id`) USING BTREE,
  INDEX `parise_ibfk_1`(`u_id`) USING BTREE,
  CONSTRAINT `parise_ibfk_1` FOREIGN KEY (`u_id`) REFERENCES `user` (`u_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `praise_ibfk_1` FOREIGN KEY (`a_id`) REFERENCES `article` (`a_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of praise
-- ----------------------------
INSERT INTO `praise` VALUES (5, 5, 4);
INSERT INTO `praise` VALUES (12, 5, 1);
INSERT INTO `praise` VALUES (13, 4, 1);
INSERT INTO `praise` VALUES (14, 1, 4);
INSERT INTO `praise` VALUES (17, 2, 2);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `u_id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `avatar` varchar(400) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `signature` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `private_mode` int(0) NULL DEFAULT 0 COMMENT '1代表私密模式 0代表正常',
  PRIMARY KEY (`u_id`) USING BTREE,
  INDEX `idx_user_name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '千', 'https://img2.baidu.com/it/u=3071049752,741448477&fm=253&fmt=auto&app=138&f=JPEG?w=501&h=500', '$2a$10$AIUE3XOpB3ThzUqhaEON.OopXl7mDnumGvzdunES52eAASRS6Zi4W', '竹杖芒鞋轻胜马', NULL, 0);
INSERT INTO `user` VALUES (2, 'lxl', 'https://img2.baidu.com/it/u=501245233,3760444191&fm=253&fmt=auto&app=138&f=JPEG?w=533&h=500', '$2a$10$AIUE3XOpB3ThzUqhaEON.OopXl7mDnumGvzdunES52eAASRS6Zi4W', '啊哈哈哈哈哈哈', NULL, 0);
INSERT INTO `user` VALUES (3, '蛋白粉', 'https://img1.baidu.com/it/u=461632996,4072736410&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500', '$2a$10$AIUE3XOpB3ThzUqhaEON.OopXl7mDnumGvzdunES52eAASRS6Zi4W', '嘻嘻嘻嘻', NULL, 0);
INSERT INTO `user` VALUES (4, '千寻', 'https://img0.baidu.com/it/u=2132921925,3667188979&fm=253&fmt=auto&app=120&f=JPEG?w=800&h=800', '$2a$10$AIUE3XOpB3ThzUqhaEON.OopXl7mDnumGvzdunES52eAASRS6Zi4W', '任尔东西南北风', '2022-07-29 09:00:29', 1);
INSERT INTO `user` VALUES (5, '测试用户', 'https://img2.baidu.com/it/u=501245233,3760444191&fm=253&fmt=auto&app=138&f=JPEG?w=533&h=500', '$2a$10$AIUE3XOpB3ThzUqhaEON.OopXl7mDnumGvzdunES52eAASRS6Zi4W', '测试', '2022-08-06 09:55:09', 0);

SET FOREIGN_KEY_CHECKS = 1;
