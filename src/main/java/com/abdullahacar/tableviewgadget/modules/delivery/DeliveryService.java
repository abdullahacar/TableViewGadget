package com.abdullahacar.tableviewgadget.modules.delivery;

import com.abdullahacar.tableviewgadget.config.ApplicationBean;
import com.abdullahacar.tableviewgadget.dto.ApiResponse;
import com.abdullahacar.tableviewgadget.dto.DeliveryDTO;
import com.abdullahacar.tableviewgadget.dto.Enums.ApiResponseCode;
import com.abdullahacar.tableviewgadget.infrastructure.ApiService;
import com.abdullahacar.tableviewgadget.querymodel.delivery.DeliveryQueryModel;
import com.fasterxml.jackson.databind.JavaType;

import java.util.List;

public class DeliveryService extends ApiService {

    public DeliveryService(ApplicationBean applicationBean) {
        super(applicationBean);
        System.out.println("Delivery Service Bean initialized...");
    }

    public ApiResponse<List<DeliveryDTO>> getDeliveryByParameters(DeliveryQueryModel queryModel) {

        try {
            String url = "/delivery/getDeliveryByParameters";

            ApiResponse<List<DeliveryDTO>> response = this.post(url, queryModel);

            JavaType type = mapper.getTypeFactory().constructParametricType(ApiResponse.class, mapper.getTypeFactory().constructCollectionType(List.class, DeliveryDTO.class));

            return mapper.readValue(response.getEntityJson(), type);

        } catch (Exception e) {
            return ApiResponse.<List<DeliveryDTO>>builder()
                    .apiResponseCode(ApiResponseCode.EXCEPTION)
                    .message(e.getMessage())
                    .build();
        }

    }

}
