package univer.university.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import univer.university.dto.ApiResponse;
import univer.university.dto.PublicationDTO;
import univer.university.dto.request.ReqPublication;
import univer.university.dto.response.ResPageable;
import univer.university.service.PublicationService;

@RestController
@RequestMapping("/api/publication")
@RequiredArgsConstructor
public class PublicationController {

    private final PublicationService publicationService;

    @PostMapping
    @Operation(description = "{PublicationTypeEnum -> ARTICLE,BOOK, PROCEEDING, OTHERS} ," +
            " {AuthorEnum ->COAUTHOR,FIRST_AUTHOR,BOTH_AUTHOR} , {DegreeEnum - >INTERNATIONAL,NATIONAL}")
    public ResponseEntity<ApiResponse<String>> addPublication(@RequestBody ReqPublication req){
        return ResponseEntity.ok(publicationService.addPublication(req));
    }

    @GetMapping("/get-page")
    public ResponseEntity<ApiResponse<ResPageable>> getAllPage(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(publicationService.getAllPage(page, size));
    }

    @GetMapping("/{publicationId}")
    public ResponseEntity<ApiResponse<PublicationDTO>> getPublicationById(@PathVariable Long publicationId){
        return ResponseEntity.ok(publicationService.getPublicationById(publicationId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deletePublication(@PathVariable Long id){
        return ResponseEntity.ok(publicationService.delete(id));
    }


    @GetMapping("/byUser/{id}")
    public ResponseEntity<ApiResponse<ResPageable>> getPublicationByUser(@PathVariable Long id,
                                                                            @RequestParam(defaultValue = "0") int page,
                                                                            @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(publicationService.getByUserId(id,page,size));
    }


}
