package com.mihir.musiclibrary.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mihir.musiclibrary.Response.ApiResponse;
import com.mihir.musiclibrary.Response.ErrorDetails;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        // Create a response object
        ApiResponse<Object> apiResponse = new ApiResponse<>(
                403,
                null,
                "Unauthorized Access",
                Collections.singletonList(new ErrorDetails("Forbidden", accessDeniedException.getMessage()))
        );

        // Serialize response to JSON
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.getWriter().write(
                new ObjectMapper().writeValueAsString(apiResponse)
        );
    }
}

