/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu;


import java.sql.SQLException;
import java.time.LocalDate;


/**
 *
 * @author san
 */
public class Ctrl_Member {
    private Entity_Member entity_Obj;

    public Ctrl_Member() {
        entity_Obj = new Entity_Member();
    }

    
    
    int join(String id, String password, String name,LocalDate birth, char sex, String phone) throws SQLException {
        boolean chk_id;
        int db_insert_chk;
        
        // 가입 중복 유무 검사
        chk_id  = entity_Obj.check_id(id);
        if( chk_id == true ){
            // 아이디가 중복됨
            return MessageNo.ID_DUPLICATE;
        }
        
        
        
        db_insert_chk = entity_Obj.insert(id, password, name, birth, sex, phone);
        
        // 계좌 개설
        
        if( db_insert_chk == MessageNo.SUCCESS){
            return MessageNo.SUCCESS;
        } else if(db_insert_chk == MessageNo.SQL_INSERT_ERR_NO) {
            return MessageNo.SQL_INSERT_ERR_NO;
        }

        return MessageNo.FAIL;
    }

    int login(String id, String password) throws SQLException {
        int db_login_chk;
        
        db_login_chk = entity_Obj.login(id, password);
        
        return db_login_chk;
    }
    
    public int getAccountNo(String id) throws SQLException{
        AccountDAO accountObj = new AccountDAO();
        return accountObj.getAccount(id);
    }
 
    
}
