package com.abdullahacar.tableviewgadget.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenDTO {

    String _id;
    String tokenKey;
    Date issueDate;
    Date lastUpdate;
    String loginId;
    String terminalName;
    String terminalAddress;
    boolean active = true;

}
