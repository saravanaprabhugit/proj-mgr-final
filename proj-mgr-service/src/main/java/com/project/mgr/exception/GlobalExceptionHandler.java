package com.project.mgr.exception;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(Exception.class)

    public @ResponseBody

    PMException handleCustomException(Exception ex, HttpServletResponse response) {

                response.setHeader("Content-Type", "application/json");

                if (ex instanceof PMException) {

                            response.setStatus(((PMException) ex).getStatus());

                            return ((PMException) ex).transformException();

                }

                else

                {

                            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

                            return returnRestError();

                }

    }



    private PMException returnRestError() {

    	PMException restError = new PMException();

                restError.setErrorCode("500");

                restError.setErrorMessage("Technical Error");

                return restError;

    }

}
