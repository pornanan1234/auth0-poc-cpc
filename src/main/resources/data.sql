DROP TABLE IF EXISTS CPC;

CREATE TABLE CPC (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  customergroup varchar(50),
  plant varchar(4),
  product varchar(20),
  price varchar(20)
);

insert into CPC(id,customergroup,plant,product) VALUES
(1001,'F0','0Z01','123456'),
(1002,'F0','0Z01','123457'),
(1003,'F0','0Z01','123458'),
(1004,'F0','0Z01','123459'),
(1005,'F0','0Z01','123460'),
(1006,'F0','0Z01','123461'),
(1007,'F0','0Z02','123462'),
(1008,'F0','0Z02','123463'),
(1009,'F0','0Z02','123464'),
(10010,'F1','0Z02','123465'),
(10011,'F1','0Z02','123466'),
(10012,'F2','0Z02','123467'),
(10013,'F2','0Z03','123468'),
(10014,'F2','0Z03','123469'),
(10015,'F2','0Z03','123470'),
(10016,'D0','0Z01','123471'),
(10017,'D0','0Z01','123472'),
(10018,'D0','0Z01','123473'),
(10019,'D0','0Z01','123474'),
(10020,'D0','0Z01','123475'),
(10021,'D0','0Z01','123476'),
(10022,'D0','0Z02','123477'),
(10023,'D1','0Z02','123478'),
(10024,'D1','0Z02','123479'),
(10025,'D2','0Z02','123480'),
(10026,'D2','0Z02','123481'),
(10027,'D3','0Z02','123482'),
(10028,'D3','0Z03','123483'),
(10029,'D4','0Z03','123484'),
(10030,'D0','0Z03','123485'),
(10031,'E0','0Z01','123486'),
(10032,'E0','0Z01','123487'),
(10033,'E0','0Z01','123488'),
(10034,'E0','0Z01','123489'),
(10035,'E0','0Z01','123490'),
(10036,'E0','0Z01','123491'),
(10037,'E0','0Z02','123492'),
(10038,'E0','0Z02','123493'),
(10039,'E0','0Z02','123494'),
(10040,'FW','0Z02','123495'),
(10041,'FW','0Z02','123496'),
(10042,'FW','0Z02','123497'),
(10043,'FW','0Z03','123498'),
(10044,'FW','0Z03','123499'),
(10045,'F2','0Z03','123500')
;