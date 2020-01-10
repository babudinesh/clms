DROP TABLE IF EXISTS `clms`.`plant_details`;
CREATE TABLE  `clms`.`plant_details` (
  `Plant_id` int(11) NOT NULL AUTO_INCREMENT,
  `Customer_id` int(11) NOT NULL,
  `Company_id` int(11) NOT NULL,
  `Location_id` int(11) NOT NULL,
  `Plant_code` varchar(10) NOT NULL,
  `Created_By` int(11) NOT NULL,
  `Created_Date` datetime NOT NULL,
  `Modified_By` int(11) NOT NULL,
  `Modified_Date` datetime NOT NULL,
  PRIMARY KEY (`Plant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `clms`.`plant_detail_info`;
CREATE TABLE  `clms`.`plant_detail_info` (
  `Plant_Info_id` int(11) NOT NULL AUTO_INCREMENT,
  `Plant_id` int(11) NOT NULL,
  `Transaction_Date` datetime NOT NULL,
  `Plant_Sequence_id` int(11) NOT NULL,
  `Is_Active` enum('Y','N') NOT NULL,
  `Plant_name` varchar(50) NOT NULL,
  `Short_name` varchar(50) DEFAULT NULL,
  `Contact_Person` varchar(50) DEFAULT NULL,
  `Phone_Number` varchar(20) DEFAULT NULL,
  `Fax` varchar(20) DEFAULT NULL,
  `Email` varchar(40) DEFAULT NULL,
  `Address_line1` varchar(50) DEFAULT NULL,
  `Address_line2` varchar(50) DEFAULT NULL,
  `Address_line3` varchar(50) DEFAULT NULL,
  `Country_Id` int(11) DEFAULT NULL,
  `State_id` int(11) DEFAULT NULL,
  `City` varchar(50) DEFAULT NULL,
  `Created_By` int(11) NOT NULL,
  `Created_Date` datetime NOT NULL,
  `Modified_By` int(11) NOT NULL,
  `Modified_Date` datetime NOT NULL,
  PRIMARY KEY (`Plant_Info_id`),
  KEY `FK_plant_detail_info_1` (`Plant_id`),
  CONSTRAINT `FK_plant_detail_info_1` FOREIGN KEY (`Plant_id`) REFERENCES `plant_details` (`Plant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `clms`.`workrorder_detail`;
CREATE TABLE  `clms`.`workrorder_detail` (
  `WorkOrder_id` int(11) NOT NULL,
  `Customer_id` int(11) NOT NULL,
  `Company_id` int(11) NOT NULL,
  `Country_id` int(11) NOT NULL,
  `Location_id` int(11) NOT NULL,
  `Plant_id` int(11) NOT NULL,
  `Department_id` int(11) NOT NULL,
  `WorkOrder_Code` varchar(20) NOT NULL,
  `Created_By` int(11) NOT NULL,
  `Created_Date` datetime NOT NULL,
  `Modified_By` int(11) NOT NULL,
  `Modified_Date` datetime NOT NULL,
  PRIMARY KEY (`WorkOrder_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `clms`.`workorder_detail_info`;
CREATE TABLE  `clms`.`workorder_detail_info` (
  `WorkOrder_Info_id` int(11) NOT NULL AUTO_INCREMENT,
  `WorkOrder_id` int(11) DEFAULT NULL,
  `Transaction_Date` datetime DEFAULT NULL,
  `WorkOrder_Sequence_id` int(11) NOT NULL,
  `Is_Active` enum('Y','N') NOT NULL,
  `Work_Order_Name` varchar(100) DEFAULT NULL,
  `Created_By` int(11) NOT NULL,
  `Created_Date` datetime NOT NULL,
  `Modified_By` int(11) NOT NULL,
  `Modified_Date` datetime NOT NULL,
  PRIMARY KEY (`WorkOrder_Info_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `clms`.`WorkOrder_Manpower_Req`;
CREATE TABLE  `clms`.`WorkOrder_Manpower_Req` (
  `WorkOrder_Manpower_Req_id` int(11) NOT NULL AUTO_INCREMENT,
  `WorkOrder_id` int(11) NOT NULL,
  `WorkOrder_Info_id` int(11) NOT NULL,
  `Work_Skill_id` int(11) NOT NULL,
  `Job_Code_id` int(11) NOT NULL,
  `Head_Count` int(11) NOT NULL,
  `From_Date` datetime NOT NULL,
  `To_Date` datetime NOT NULL,
  `Transaction_Date` datetime NOT NULL,
  `WorkOrder_Req_Sequence_id` int(11) NOT NULL,
  `Created_By` int(11) NOT NULL,
  `Created_Date` datetime NOT NULL,
  `Modified_By` int(11) NOT NULL,
  `Modified_Date` datetime NOT NULL,
  PRIMARY KEY (`WorkOrder_Manpower_Req_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;