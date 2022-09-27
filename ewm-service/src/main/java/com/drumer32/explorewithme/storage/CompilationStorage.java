package com.drumer32.explorewithme.storage;

import com.drumer32.explorewithme.model.compilation.Compilation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompilationStorage extends JpaRepository<Compilation, Integer> {

    Page<Compilation> findAllByPinnedIsTrue(Pageable pageable);
}
