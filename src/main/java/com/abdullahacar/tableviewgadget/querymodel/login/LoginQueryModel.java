/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abdullahacar.tableviewgadget.querymodel.login;

import com.abdullahacar.tableviewgadget.querymodel.base.QueryModel;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 *
 * @author ABDULLAH ACAR
 */
@Data
@SuperBuilder
public class LoginQueryModel extends QueryModel {

    String userName;
    String password;
    int version;
    boolean requireActive;
    String key;
 
}
