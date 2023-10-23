package com.abdullahacar.tableviewgadget.dto;


import com.abdullahacar.tableviewgadget.dto.Enums.LoginResultStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResultDTO {

    LoginResultStatus status;
    TokenDTO token;

}
