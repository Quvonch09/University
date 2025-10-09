package univer.university.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import univer.university.dto.ApiResponse;
import univer.university.dto.CategoryDTO;
import univer.university.dto.request.ReqCategory;
import univer.university.service.CategoryService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    public final CategoryService categoryService;
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<String>> addCategory(@RequestBody ReqCategory req) {
        return ResponseEntity.ok(categoryService.addCategory(req));
    }
    @GetMapping("/get-list")
    public ResponseEntity<ApiResponse<List<CategoryDTO>>> getListCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
    @GetMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryDTO>> getCategoryById(@PathVariable Long categoryId) {
        return ResponseEntity.ok(categoryService.getCategoryById(categoryId));
    }
}
