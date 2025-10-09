package univer.university.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import univer.university.dto.ApiResponse;
import univer.university.dto.SubCategoryDTO;
import univer.university.dto.request.ReqSubCategory;
import univer.university.service.SubCategoryService;

import java.util.List;

@RestController
@RequestMapping("/sub-category")
@RequiredArgsConstructor
public class SubCategoryController {
    private final SubCategoryService subCategoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> addSubCategory(@RequestBody ReqSubCategory req) {
        return ResponseEntity.ok(subCategoryService.addSubCategory(req));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SubCategoryDTO>>> getListSubCategory() {
        return ResponseEntity.ok(subCategoryService.getAllList());

    }

    @GetMapping("/{subCategoryId}")
    public ResponseEntity<ApiResponse<SubCategoryDTO>> getSubCategory(@PathVariable Long subCategoryId) {
        return ResponseEntity.ok(subCategoryService.getSubCategoryById(subCategoryId));

    }
}
