package com.eCommerce.exception;


import com.eCommerce.payload.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class MyGlobalExceptionHandler
{
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> mymethodargumentNotValidException(MethodArgumentNotValidException e)
    {
        Map<String,String> response = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(objectError -> {
            String Fieldname = ((FieldError)(objectError)).getField();
            String message = objectError.getDefaultMessage();
            response.put(Fieldname,message);
        });
        return new ResponseEntity<Map<String,String>>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<APIResponse> myAPIException(APIException e)
    {
        APIResponse apiResponse = new APIResponse(e.getMessage(), false);
        //String message = e.getMessage();
        return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
    }

}
