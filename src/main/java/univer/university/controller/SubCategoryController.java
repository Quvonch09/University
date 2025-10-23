package univer.university.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;
import univer.university.dto.ApiResponse;
import univer.university.dto.SubCategoryDTO;
import univer.university.dto.request.ReqSubCategory;
import univer.university.service.SubCategoryService;

import java.util.List;

@RestController
@RequestMapping("/subCategory")
@RequiredArgsConstructor
public class SubCategoryController {
    private final SubCategoryService subCategoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> addSubCategory(@RequestBody ReqSubCategory reqSubCategory) {
        return ResponseEntity.ok(subCategoryService.saveSubCategory(reqSubCategory));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> updateSubCategory(@PathVariable Long id,
                                                                 @RequestBody SubCategoryDTO subCategoryDTO) {
        return ResponseEntity.ok(subCategoryService.updateSubCategory(id, subCategoryDTO));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteSubCategory(@PathVariable Long id) {
        return ResponseEntity.ok(subCategoryService.deleteSubCategory(id));
    }



    @GetMapping("/getOne/{categoryId}")
    public ResponseEntity<ApiResponse<List<SubCategoryDTO>>> getSubCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(subCategoryService.getSubCategoryByCategory(categoryId));
    }



    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<SubCategoryDTO>>> getAllSubCategory() {
        return ResponseEntity.ok(subCategoryService.getAllSubCategories());
    }
}
