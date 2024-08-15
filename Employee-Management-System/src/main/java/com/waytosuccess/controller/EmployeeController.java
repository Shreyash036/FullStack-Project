package com.waytosuccess.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.waytosuccess.entity.Employee;
import com.waytosuccess.exception.ResourceNotFoundException;
import com.waytosuccess.repository.EmployeeRepository;

@RestController //this annotation tell to the  spring container // this class is request handler class
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@GetMapping("/employees")
	public List<Employee> getAllEmployees()
	{
		return employeeRepository.findAll(); 
		
	}
	
	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee employee)
	{
		return employeeRepository.save(employee);
	}
	
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable int id) {
	    // Retrieve the employee by id
	    Employee employee = employeeRepository.findById(id)
	        .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));

	    // Return the employee details in the response
	    return ResponseEntity.ok(employee);
	}

	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee>updateEmployee(@PathVariable int  id,@RequestBody  Employee employeeDetails)
	{
		Employee employee = employeeRepository.findById(id)
		        .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));
		
		employee.setFirstName(employeeDetails.getFirstName());
		employee.setLastName(employeeDetails.getLastName());
		employee.setSalary(employeeDetails.getSalary());
		
		employeeRepository.save(employee);
		
		return ResponseEntity.ok(employee);
	}
	
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Map<String,Boolean>>  deleteEmployee(@PathVariable int id)
	{
		Employee employee = employeeRepository.findById(id)
		        .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));
		
		employeeRepository.delete(employee);
		Map<String, Boolean>response=new HashMap<String, Boolean>();
		response.put("delete", Boolean.TRUE);
		return ResponseEntity.ok(response);
		}
	
}
