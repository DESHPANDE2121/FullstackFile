package com.sdlccorp.hr.controller;

import com.sdlccorp.hr.model.Employee;
import com.sdlccorp.hr.repo.EmployeeRepo;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeRepo repo;

    public EmployeeController(EmployeeRepo repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Employee> list() {
        return repo.findAll();
    }

    @PostMapping
    public Employee create(@Valid @RequestBody Employee e) {
        e.setId(null);
        return repo.save(e);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @Valid @RequestBody Employee e) {
        var existing = repo.findById(id).orElse(null);
        if (existing == null) return ResponseEntity.notFound().build();

        existing.setFullName(e.getFullName());
        existing.setEmail(e.getEmail());
        existing.setDepartment(e.getDepartment());
        existing.setSalary(e.getSalary());

        return ResponseEntity.ok(repo.save(existing));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        // Return a small JSON body so clients never fail JSON parsing on empty responses.
        return ResponseEntity.ok(Map.of("deleted", true, "id", id));
    }
}
