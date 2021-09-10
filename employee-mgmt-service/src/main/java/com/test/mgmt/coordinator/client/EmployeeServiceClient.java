package com.test.mgmt.coordinator.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.test.mgmt.coordinator.dto.EmployeeDto;
import com.test.mgmt.coordinator.dto.Response;

@FeignClient(url = "employee-service:9000/api/v1/employees", name = "employee-service")
public interface EmployeeServiceClient {

    @PostMapping
    public Response add(EmployeeDto employeeDto);

    @PutMapping("/{id}")
    public Response updateState(@PathVariable Long id, String state);

}
