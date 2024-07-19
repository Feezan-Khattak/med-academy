package com.med.acadmys.utils;

import com.med.acadmys.dtos.Response;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.med.acadmys.utils.ResponseStatus.FAIL;
import static com.med.acadmys.utils.ResponseStatus.OK;

@Service
public class ResponseUtil {

    public Response generateSuccessResponse(Object obj) {
        return Response.builder()
                .status(OK.name())
                .success(true)
                .data(List.of(obj)).build();
    }

    public Response generateFailureResponse(String error){
        return Response.builder()
                .status(FAIL.name())
                .success(false)
                .error(error)
                .build();
    }
}