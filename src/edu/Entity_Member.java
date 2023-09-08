/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mariadb.jdbc.Connection;

/**
 *
 * @author san
 */
public class Entity_Member {

   // private static final String DB_SCHEMA = "bank";

    public Entity_Member()  {
       
        try {
            Class.forName("org.mariadb.jdbc.Driver"); // 동적 클래스 로드 (mariadbDriver이라는 클래스를 로드하는것)
         } catch (ClassNotFoundException ex) {
            Logger.getLogger(Entity_Member.class.getName()).log(Level.SEVERE, null, ex);
        } 

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
    
    private Connection getConnection() throws SQLException {
	return (Connection) DriverManager.getConnection(DbInfo.DB_URL, DbInfo.DB_USER, DbInfo.DB_PASSWORD);
    }

    public boolean check_id(String id) throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String sql;
                
        sql = "SELECT * FROM `member` where id ='" + id +"'";

        try {
            conn = getConnection();
            stmt = conn.createStatement();
            
            rs = stmt.executeQuery(sql);
            
            if(rs.next()){
                // id가 존재하면
                return true;
            } else {
                // 존재하지 않으면
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Entity_Member.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null)
                conn.close();
        }
        
        return true;
    }    

    public int insert(String id, String password, String name, java.time.LocalDate date, char sex, String phone) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql;
        java.sql.Date birth;
        
        //데이터베이스 Date형으로 변경
        birth = java.sql.Date.valueOf(date);
        
        sql = "INSERT INTO member (id, password, name, birth, sex, phone) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, password);
            pstmt.setString(3, name);
            pstmt.setDate(4, birth);
            pstmt.setObject(5, sex, java.sql.Types.CHAR);
            pstmt.setString(6, phone);  
            
            pstmt.executeUpdate();
            
            pstmt.close();
            
            return MessageNo.SUCCESS;
        } catch (SQLException ex) {
            Logger.getLogger(Entity_Member.class.getName()).log(Level.SEVERE, null, ex);
            return MessageNo.SQL_INSERT_ERR_NO;
        }finally {
           closeAll(rs, pstmt, conn);
        }
        
        
    }

    int login(String id, String password) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;        
        String sql;
 
        sql = "select  *  from member where id=? and password=?";
        
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return MessageNo.SUCCESS;
            } else {
                return MessageNo.FAIL;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Entity_Member.class.getName()).log(Level.SEVERE, null, ex);
            return MessageNo.SQL_LOGIN_ERR;
        } finally {
           closeAll(rs, pstmt, conn);
        }
        
    }
             
    
}
