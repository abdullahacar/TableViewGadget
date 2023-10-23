package com.abdullahacar.tableviewgadget.dto;

import com.abdullahacar.tableviewgadget.dto.Enums.ApiResponseCode;
import com.abdullahacar.tableviewgadget.dto.Enums.ResponseResultCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.text.html.Option;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse<T> {

    private T entity;
    private String entityJson;
    private byte[] bytes;
    private ApiResponseCode apiResponseCode;
    private long count;
    private String exception;
    private String message;

    public boolean isSuccessful() {

        if (this.apiResponseCode == null) return false;

        return apiResponseCode.equals(ApiResponseCode.SUCCESSFUL);

    }

}
