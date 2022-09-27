package com.drumer32.explorewithme.controller.isadmin;


import com.drumer32.explorewithme.model.category.Category;
import com.drumer32.explorewithme.model.exception.ObjectNotFoundException;
import com.drumer32.explorewithme.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/categories")
public class AdminCategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public Category addCategory(@RequestBody Category category) {
        return categoryService.addCategory(category);
    }

    @PatchMapping
    public Category updateCategory(@RequestBody Category category) throws ObjectNotFoundException {
        return categoryService.updateCategory(category);
    }

    @DeleteMapping("/{catId}")
    public void deleteCategory(@PathVariable Integer catId) throws ObjectNotFoundException {
        categoryService.deleteCategory(catId);
    }
}
