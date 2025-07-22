USE QuanLyBanHang;

INSERT INTO Customer(cID, cName, cAge)
VALUE (1, "Minh Quan", 10),
 (2, "Ngoc Oanh", 20),
 (3, "Hong Ha", 50);
 
 INSERT INTO `Order`(oID, cID, oDate, oTotalPrice)
 VALUE(1, 1, '2006-03-21', NULL),
 (2, 2, '2006-03-23', NULL),
 (3, 1, '2006-03-16', NULL);
 
 INSERT INTO Product(pID, pName, pPrice)
 VALUE(1, "May Giat", 3),
 (2, "Tu Lanh", 5),
 (3, "Dieu Hoa", 7),
 (4, "QUat", 1),
 (5, "Bep Dien", 2);
 
 INSERT INTO OrderDetail(oID, pID, odQTY)
 VALUE(1, 1, 3),
 (1, 3, 7),
 (1, 4, 2),
 (2, 1, 1),
 (3, 1, 8),
 (2, 5, 4),
 (2, 3, 3);
 
 SELECT o.oID, o.oDate, SUM(od.odQTY * p.pPrice) AS oPrice
 FROM `Order` o
 JOIN Orderdetail od ON o.oID = od.oID
 JOIN Product p ON od.pID = p.pID
 GROUP BY
 o.oID, o.oDate;
 
 SELECT c.cName, p.pName
 FROM Customer c
 JOIN `Order` o ON c.cID = o.cID
 JOIN Orderdetail od ON o.oID = od.oID
 JOIN Product p ON od.pID = p.pID
 ORDER BY
 c.cName;
 
 SELECT c.cName
 FROM Customer c
 LEFT JOIN `Order` o ON c.cID = o.cID
 LEFT JOIN Orderdetail od ON o.oID = od.oID
 WHERE
 od.pID IS NULL;
 
 SELECT o.oID, o.oDate, p.pName, od.odQTY, p.pPrice, od.odQTY * p.pPrice AS TotalPrice
 FROM `Order` o
 JOIN Orderdetail od ON o.oID = od.oID
 JOIN Product p ON od.pID = p.pID
 ORDER BY o.oID;
 