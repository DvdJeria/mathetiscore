package com.mathetiscore.api.infraestructure.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WebConfig.class)
@ContextConfiguration(classes = {
        WebConfigTest.TestCorsController.class,
        WebConfig.class
})
class WebConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @RestController
    @RequestMapping("/api/test-cors")
    public static class TestCorsController{
        @GetMapping
        public String ping(){
            return "pong";
        }
    }

    @Test
    @DisplayName("Debe incluir la cabecera Access-Control-Allow-Origin en peticiones GET")
    void shouldIncludeCorsHeaderOnGetRequest() throws Exception {
        mockMvc.perform(get("/api/test-cors")
                .header(HttpHeaders.ORIGIN, "http://localhost:4200"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "http://localhost:4200"));
    }

}
