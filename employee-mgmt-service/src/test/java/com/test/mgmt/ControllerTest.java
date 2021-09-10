package com.test.mgmt;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.mgmt.coordinator.client.EmployeeServiceClient;
import com.test.mgmt.coordinator.dto.EmployeeDto;
import com.test.mgmt.coordinator.dto.Response;

@SpringBootTest(classes = EmployeeMgmtServiceApplication.class)
@AutoConfigureMockMvc
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmployeeServiceClient employeeServiceClient;

    @Test
    public void testAddOperationSuccess() throws Exception, JsonProcessingException, UnsupportedEncodingException {
        when(employeeServiceClient.add(ArgumentMatchers.any()))
                .thenReturn(Response.of(0, ""));

        EmployeeDto employee = new EmployeeDto();
        employee.setName("Name");
        employee.setAge(33);

        byte[] bytesBody = objectMapper.writeValueAsString(employee).getBytes();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/employees")
                .content(bytesBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Map<String, Object> response = objectMapper.readValue(result.getResponse().getContentAsString(), Map.class);
        assertEquals(response.get("code"), 0);
    }

}
