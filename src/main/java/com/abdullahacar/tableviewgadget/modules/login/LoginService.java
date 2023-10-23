package com.abdullahacar.tableviewgadget.modules.login;

import com.abdullahacar.tableviewgadget.config.ApplicationBean;
import com.abdullahacar.tableviewgadget.dto.ApiResponse;
import com.abdullahacar.tableviewgadget.dto.AuthenticationResponseDTO;
import com.abdullahacar.tableviewgadget.dto.Enums.ApiResponseCode;
import com.abdullahacar.tableviewgadget.dto.RegisterRequestDTO;
import com.abdullahacar.tableviewgadget.infrastructure.ApiService;
import com.fasterxml.jackson.databind.JavaType;

public class LoginService extends ApiService {

    public LoginService(ApplicationBean applicationBean) {
        super(applicationBean);
        System.out.println("Login Service Bean initialized...");
    }

    public ApiResponse<AuthenticationResponseDTO> authenticateLogin(RegisterRequestDTO request) {

        try {
            ApiResponse<AuthenticationResponseDTO> response = this.post("/auth/authenticate",
                    request);

            JavaType type = mapper.getTypeFactory().constructParametricType(ApiResponse.class, AuthenticationResponseDTO.class);

            return mapper.readValue(response.getEntityJson(), type);

        } catch (Exception e) {
            return ApiResponse.<AuthenticationResponseDTO>builder()
                    .apiResponseCode(ApiResponseCode.EXCEPTION)
                    .message(e.getMessage())
                    .build();
        }


    }

    public ApiResponse<AuthenticationResponseDTO> createLogin(RegisterRequestDTO request) {
        try {
            ApiResponse<AuthenticationResponseDTO> post = this.post("/auth/register", request);

            JavaType type = mapper.getTypeFactory().constructParametricType(ApiResponse.class, AuthenticationResponseDTO.class);

            return mapper.readValue(post.getEntityJson(), type);

        } catch (Exception e) {
            return ApiResponse.<AuthenticationResponseDTO>builder()
                    .apiResponseCode(ApiResponseCode.EXCEPTION)
                    .exception(e.getMessage())
                    .build();
        }

    }

}
