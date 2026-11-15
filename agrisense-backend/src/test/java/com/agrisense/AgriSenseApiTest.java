package com.agrisense;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {"spring.datasource.url=jdbc:h2:mem:agrisense-test;DB_CLOSE_DELAY=-1", "agrisense.demo.password=test-only-password"})
class AgriSenseApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void telemetryRequiresLogin() throws Exception {
        mockMvc.perform(get("/api/telemetry"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void demoUserCanLoginAndReadTelemetry() throws Exception {
        MvcResult login = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("""
                                {
                                  "email": "marcus.vance@highlandridge.ag",
                                  "password": "test-only-password"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.role").value("AGRONOMIST"))
                .andReturn();

        HttpSession session = login.getRequest().getSession(false);
        mockMvc.perform(get("/api/telemetry").session((org.springframework.mock.web.MockHttpSession) session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gateway.gatewayName").value("04-Nordic Gateway"))
                .andExpect(jsonPath("$.probes.length()").value(3));
    }
}

