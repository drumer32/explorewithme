package com.drumer32.explorewithme.service;

import com.drumer32.explorewithme.model.category.Category;
import com.drumer32.explorewithme.model.category.CategoryDto;
import com.drumer32.explorewithme.model.exception.ObjectNotFoundException;
import com.drumer32.explorewithme.model.tools.ModelMapperUtil;
import com.drumer32.explorewithme.storage.CategoryStorage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private CategoryStorage categoryStorage;
    private ModelMapperUtil mapper;

    @Override
    public Category addCategory(@NotNull Category category) {
        log.info("Запрос на сохранение категории {}", category.getName());
        categoryStorage.save(category);
        return category;
    }

    @Override
    public Category updateCategory(@NotNull Category updateCategory) throws ObjectNotFoundException {
        Category oldCategory = getCategory(updateCategory.getId());
        log.info("Запрос на обновление категории с {} на {}", oldCategory.getName(), updateCategory.getName());
        oldCategory.setName(updateCategory.getName());
        return oldCategory;
    }

    @Override
    public void deleteCategory(Integer id) throws ObjectNotFoundException {
        log.info("Запрос на удаление категории {}", id);
        try {
            categoryStorage.deleteById(id);
        } catch (NullPointerException e) {
            throw new ObjectNotFoundException("Категория не найдена");
        }
    }

    @Override
    public List<CategoryDto> getAll(Integer from, Integer size) {
        log.info("Запрос на получение всех категорий");
        Pageable pageable = PageRequest.of(from, size);
        return categoryStorage.findAll(pageable).getContent().stream()
                .map(category -> mapper.map(category, CategoryDto.class))
                .toList();
    }

    @Override
    public Category getCategory(Integer id) throws ObjectNotFoundException {
        log.info("Запрос на получение категории {}", id);
        try {
            return categoryStorage.findById(id).get();
        }
        catch (NoSuchElementException e) {
            throw new ObjectNotFoundException("Категория не найдена");
        }
    }
}
