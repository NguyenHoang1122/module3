package models;

import database.DBConnect;

import java.sql.Connection;

public class BaseModel {
    protected Connection conn = null;

    public BaseModel() {
        DBConnect dbConnect = new DBConnect();
        conn = dbConnect.getConnect();
    }
}