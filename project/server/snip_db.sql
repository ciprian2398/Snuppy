create database dbf;

use dbf;

CREATE TABLE `users` (
  `id_user` int(11) NOT NULL AUTO_INCREMENT,
  `user_email` varchar(30) NOT NULL,
  `user_password` varchar(100) NOT NULL,
  `image_blob` longblob,
  PRIMARY KEY (`id_user`),
  UNIQUE KEY `user_email` (`user_email`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

LOCK TABLES `users` WRITE;
INSERT INTO `users` VALUES (9,'ciprian2398@gmail.com','23cipi1998',NULL);
UNLOCK TABLES;

/*create user*/
CREATE USER 'def_user1'@'%' IDENTIFIED BY 'P.cD#Mypx,D7MycXr44!';

REVOKE ALL PRIVILEGES, GRANT OPTION FROM 'def_user1'@'%';

GRANT SELECT ON dbf.users TO 'def_user1'@'%';
GRANT INSERT ON dbf.users TO 'def_user1'@'%';
GRANT UPDATE ON dbf.users TO 'def_user1'@'%';
GRANT GRANT OPTION ON dbf.users TO 'def_user1'@'%';

FLUSH PRIVILEGES;

