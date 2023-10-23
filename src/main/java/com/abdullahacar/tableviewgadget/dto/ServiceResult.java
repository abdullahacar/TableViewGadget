package com.abdullahacar.tableviewgadget.dto;

import com.abdullahacar.tableviewgadget.dto.Enums.ApiResponseCode;
import com.abdullahacar.tableviewgadget.dto.Enums.ServiceResultCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceResult<T> {

    T entity;
    long count;
    int errorCode;
    byte[] bytes;
    ApiResponseCode serviceResultCode;
    String message;

}
