/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abdullahacar.tableviewgadget.querymodel.delivery;

import com.abdullahacar.tableviewgadget.querymodel.base.QueryModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author ABDULLAH ACAR
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
public class DeliveryQueryModel extends QueryModel {

    private String id;
    private String deliveryNo;
    private String status;
    private String type;
    private String consignorFirstName;
    private String consignorLastName;
    private String consignorAddress;
    private String consigneeFirstName;
    private String consigneeLastName;
    private String consigneeAddress;
    private Boolean integrated;

}
