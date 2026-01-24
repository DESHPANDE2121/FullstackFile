package com.sdlccorp.hr.repo;

import com.sdlccorp.hr.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepo extends MongoRepository<Employee, String> {}
