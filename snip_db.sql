create database snip_db;

use snip_db;

CREATE TABLE `users` (
  `id_user` int(11) NOT NULL AUTO_INCREMENT,
  `user_email` varchar(30) NOT NULL,
  `user_password` varchar(100) NOT NULL,
  `image_blob` longblob,
  PRIMARY KEY (`id_user`),
  UNIQUE KEY `user_email` (`user_email`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*create user*/
CREATE USER 'def_user'@'%' IDENTIFIED BY 'P.cD#Mypx,D7MycXr44!';

REVOKE ALL PRIVILEGES, GRANT OPTION FROM 'def_user'@'%';

GRANT SELECT ON snip_db.users TO 'def_user'@'%';
GRANT INSERT ON snip_db.users TO 'def_user'@'%';
GRANT UPDATE ON snip_db.users TO 'def_user'@'%';
GRANT GRANT OPTION ON snip_db.users TO 'def_user'@'%';

FLUSH PRIVILEGES;

