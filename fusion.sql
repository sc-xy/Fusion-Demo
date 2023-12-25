use myfusion;

# 用户表只存储用户登录需要的信息，其他信息存储在其他表中
DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `username` varchar(255) NOT NULL,
    `password` varchar(255) NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
)   ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `user` (`id`, `username`, `password`) VALUES
(1, 'admin', '$2a$10$BEnZ3bP6oPOHl3CYS3gmwuZ/wPN3fIUL9ULTaQQSBhQsGTdoDpsIK');

# 用户信息表负责存储用户的其他信息
DROP TABLE IF EXISTS `user_info`;

CREATE TABLE `user_info` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `username` varchar(255) NOT NULL,
    `email` varchar(255) NOT NULL,
    `nickname` varchar(255) NOT NULL,
    `avatar` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`username`) USING BTREE,
    KEY `id` (`id`) USING BTREE,
    constraint `id` FOREIGN KEY (`id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
)   ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `user_info` (`id`, `username`, `email`, `nickname`, `avatar`) VALUES
(1, 'admin', 'xxx@qq.com', 'admin', 'https://avatars1.githubusercontent.com/u/227713?v=4');

# 博客信息表负责存储博客的信息
DROP TABLE IF EXISTS `blog`;

CREATE TABLE `blog` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `title` varchar(255) NOT NULL,
    `content` text NOT NULL,
    `author_id` int(11) NOT NULL,
    `view_count` int(11) NOT NULL DEFAULT '0',
    `comment_count` int(11) NOT NULL DEFAULT '0',
    `create_time` datetime NOT NULL,
    `update_time` datetime NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    KEY `author_id` (`author_id`) USING BTREE,
    constraint `author_id` FOREIGN KEY (`author_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
)   ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

# 评论信息表负责存储评论的信息
DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `parent_id` int(11) NOT NULL DEFAULT '-1',
    `blog_id` int(11) NOT NULL,
    `user_id` int(11) NOT NULL,
    `content` text NOT NULL,
    `create_time` datetime NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    KEY `blog_id` (`blog_id`) USING BTREE,
    KEY `user_id` (`user_id`) USING BTREE,
    constraint `blog_id` FOREIGN KEY (`blog_id`) REFERENCES `blog` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    constraint `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
)   ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

# 屏蔽词表负责存储用户私聊的屏蔽关系
DROP TABLE IF EXISTS `shield`;

CREATE TABLE `shield` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
    `user_name` varchar(255) NOT NULL,
    `shield_name` varchar(255) NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    KEY `user_name` (`user_name`) USING BTREE,
    KEY `shield_name` (`shield_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
# 文件信息表负责存储用户上传的文件信息
DROP TABLE IF EXISTS `file`;

CREATE TABLE `file` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `author_id` int(11) NOT NULL,
    `name` varchar(255) NOT NULL,
    `create_time` datetime NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    KEY `user_id` (`author_id`) USING BTREE,
    CONSTRAINT `fk_file_user_id` FOREIGN KEY (`author_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;