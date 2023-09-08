/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mariadb.jdbc.Connection;

/**
 *
 * @author san
 */
public class AccountDAO {

    public AccountDAO() {
        try {
            Class.forName("org.mariadb.jdbc.Driver"); // 동적 클래스 로드 (mariadbDriver이라는 클래스를 로드하는것)
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Entity_Member.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private Connection getConnection() throws SQLException {
	return (Connection) DriverManager.getConnection(DbInfo.DB_URL, DbInfo.DB_USER, DbInfo.DB_PASSWORD);
    }
       
    
   public void closeAll(ResultSet rs, PreparedStatement pstmt, Connection con) throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (pstmt != null) {
            pstmt.close();
        }
        if (con != null) {
            con.close();
        }
    }    
    
    public int createAccount(String id) throws SQLException{
       Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql, sql2;
        java.sql.Date sdate;
        int balance = 0;
        int accountNo = 0;
        
        Date today = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        //데이터베이스 Date형으로 변경
        String result = df.format(today);
        sdate = java.sql.Date.valueOf(result);

        sql = "INSERT INTO account (id, balance, opendate) VALUES (?, ?, ?)";
        
        sql2 = "Select * from account where id=?";
        
       try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setInt(2, balance);
            pstmt.setDate(3, sdate);
            pstmt.execute();
            pstmt.close();
            
            pstmt = conn.prepareStatement(sql2);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            while(rs.next()){
                // 여러개 있는 경우를 생각해야 함
                accountNo = rs.getInt("accountno");
                System.out.println("accountNo : " + accountNo);                
            }

       } finally {
           closeAll(rs, pstmt, conn);
        }
        
       return accountNo;
    }
    
    public int getAccount(String id) throws SQLException{
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql;
        java.sql.Date sdate;
        int balance = 0;
        int accountNo = 0;
        
        
        sql = "Select * from account where id=?";
        
       try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            while(rs.next()){
                // 여러개 있는 경우를 생각해야 함
                accountNo = rs.getInt("accountno");
                System.out.println("accountNo : " + accountNo);                
            }

       } finally {
           closeAll(rs, pstmt, conn);
        }
        
       return accountNo;
    }
            
    
}
