package com.med.acadmys.controllers;

import com.med.acadmys.dtos.Response;
import com.med.acadmys.models.ChildCategory;
import com.med.acadmys.models.ParentCategory;
import com.med.acadmys.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(value = "*")
@RequestMapping("/api/v1/category")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/parent/save")
    public ResponseEntity<Response> saveParentCategory(@RequestBody ParentCategory parentCategory) {
        Response response = categoryService.saveParentCategory(parentCategory);
        return new ResponseEntity<>(response, response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/parent/update")
    public ResponseEntity<Response> updateParentCategory(@RequestBody ParentCategory parentCategory) {
        Response response = categoryService.updateParentCategory(parentCategory);
        return new ResponseEntity<>(response, response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/parent/{id}")
    public ResponseEntity<Response> getParentCategoryById(@PathVariable Long id) {
        Response response = categoryService.getParentCategoryById(id);
        return new ResponseEntity<>(response, response.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping("/parent/name/{name}")
    public ResponseEntity<Response> getParentCategoryByName(@PathVariable String name) {
        Response response = categoryService.getParentCategoryByName(name);
        return new ResponseEntity<>(response, response.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping("/parent/all")
    public ResponseEntity<Response> getAllQuestions() {
        Response response = categoryService.getAllParentCategories();
        return new ResponseEntity<>(response, response.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/parent/{id}")
    public ResponseEntity<Response> deleteQuestion(@PathVariable Long id) {
        Response response = categoryService.deleteParentCategory(id);
        return new ResponseEntity<>(response, response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/child/save")
    public ResponseEntity<Response> saveParentCategory(
            @RequestParam(name = "parentCategoryId") Long parentCategoryId,
            @RequestBody ChildCategory childCategory
    ) {
        Response response = categoryService.saveChildCategory(parentCategoryId, childCategory);
        return new ResponseEntity<>(response, response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/child/update")
    public ResponseEntity<Response> updateParentCategory(
            @RequestBody ChildCategory childCategory
    ) {
        Response response = categoryService.updateChildCategory(childCategory);
        return new ResponseEntity<>(response, response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/child/{id}")
    public ResponseEntity<Response> getChildCategoryById(@PathVariable Long id) {
        Response response = categoryService.getChildCategoryById(id);
        return new ResponseEntity<>(response, response.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping("/child/name/{name}")
    public ResponseEntity<Response> getChildCategoryByName(@PathVariable String name) {
        Response response = categoryService.getChildCategoryByName(name);
        return new ResponseEntity<>(response, response.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/child/{id}")
    public ResponseEntity<Response> deleteChildCategory(@PathVariable Long id) {
        Response response = categoryService.deleteChildCategory(id);
        return new ResponseEntity<>(response, response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

}
