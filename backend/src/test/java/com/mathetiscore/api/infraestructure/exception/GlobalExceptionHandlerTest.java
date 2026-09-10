package com.mathetiscore.api.infraestructure.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {
        GlobalExceptionHandlerTest.TestExceptionController.class,
        GlobalExceptionHandler.class
})
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @RestController
    @RequestMapping("/api/test-exception")
    public static class TestExceptionController{
        @GetMapping("/error")
        public void throwGenericException(){
            throw new RuntimeException("Error inesperado en el servidor");
        }
    }

    @Test
    @DisplayName("Debe capturar RuntimeException y devolver ApiErrorResponse con estado 500")
    void shouldHandleGenericException() throws Exception{
        mockMvc.perform(get("/api/test-exception/error"))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.error").value("Internal Server Error"))
                .andExpect(jsonPath("$.message").value("Error inesperado en el servidor"))
                .andExpect(jsonPath("$.path").value("/api/test-exception/error"))
                .andExpect(jsonPath("$.timestamp").exists());
    }
}
