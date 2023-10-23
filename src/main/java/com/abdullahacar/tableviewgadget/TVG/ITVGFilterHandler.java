/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.abdullahacar.tableviewgadget.TVG;


import com.abdullahacar.tableviewgadget.querymodel.base.QueryModel;

/**
 * @author abdullahacar
 */
@FunctionalInterface
public interface ITVGFilterHandler {

    void filter(QueryModel queryModel);

}
