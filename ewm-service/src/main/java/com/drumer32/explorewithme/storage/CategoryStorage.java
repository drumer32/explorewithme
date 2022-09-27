package com.drumer32.explorewithme.storage;

import com.drumer32.explorewithme.model.category.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryStorage extends JpaRepository<Category, Integer> {

    Page<Category> findAll(Pageable pageable);
}
