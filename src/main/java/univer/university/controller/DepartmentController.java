package univer.university.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import univer.university.dto.ApiResponse;
import univer.university.dto.request.ReqDepartment;
import univer.university.service.DepartmentService;

import java.util.List;

@RestController
@RequestMapping("/department")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;


    @PostMapping
    public ResponseEntity<ApiResponse<String>> addDepartment(@RequestBody ReqDepartment reqDepartment) {
        return ResponseEntity.ok(departmentService.addDepartment(reqDepartment));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> updateDepartment(@PathVariable Long id, @RequestBody ReqDepartment reqDepartment) {
        return ResponseEntity.ok(departmentService.updateDepartment(id, reqDepartment));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteDepartment(@PathVariable Long id) {
        return ResponseEntity.ok(departmentService.deleteDepartment(id));
    }


    @GetMapping("/{collegeId}")
    public ResponseEntity<ApiResponse<List<ReqDepartment>>> getAllDepartments(@PathVariable Long collegeId) {
        return ResponseEntity.ok(departmentService.getDepartmentByCollege(collegeId));
    }
}
