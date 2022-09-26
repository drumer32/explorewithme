package com.drumer32.explorewithme.controller.ispublic;

import com.drumer32.explorewithme.model.category.Category;
import com.drumer32.explorewithme.model.category.CategoryDto;
import com.drumer32.explorewithme.model.exception.ObjectNotFoundException;
import com.drumer32.explorewithme.model.tools.ModelMapperUtil;
import com.drumer32.explorewithme.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping(path = "/categories")
@AllArgsConstructor
public class PublicCategoryController {

    private final CategoryService categoryService;
    private final ModelMapperUtil mapper;

    @GetMapping()
    public List<CategoryDto> getCategories(@RequestParam(defaultValue = "0") Integer from,
                                           @RequestParam(defaultValue = "10") Integer size) {
        return categoryService.getAll(from, size);
    }

    @GetMapping("/{catId}")
    public CategoryDto getCategoryById(@PathVariable Integer catId) throws ObjectNotFoundException {
        return mapper.map(categoryService.getCategory(catId), CategoryDto.class);
    }
}
