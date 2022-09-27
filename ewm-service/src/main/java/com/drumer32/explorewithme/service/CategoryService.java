package com.drumer32.explorewithme.service;

import com.drumer32.explorewithme.model.category.Category;
import com.drumer32.explorewithme.model.category.CategoryDto;
import com.drumer32.explorewithme.model.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    Category addCategory(Category category);
    Category updateCategory(Category updateCategory) throws ObjectNotFoundException;
    void deleteCategory(Integer id) throws ObjectNotFoundException;
    List<CategoryDto> getAll(Integer from, Integer size);
    Category getCategory(Integer id) throws ObjectNotFoundException;
}
