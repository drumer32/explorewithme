package com.drumer32.explorewithme.service;

import com.drumer32.explorewithme.model.compilation.Compilation;
import com.drumer32.explorewithme.model.compilation.CompilationDto;
import com.drumer32.explorewithme.model.exception.ObjectNotFoundException;

import java.util.List;

public interface CompilationService {

    Compilation createCompilation(Compilation compilation);

    void deleteCompilation(Integer compId);

    void deleteEventInCompilation(Integer compId, Integer eventId);

    void addEventToCompilation(Integer compId, Integer eventId);

    void unpinFromMainPage(Integer compId);

    void pinToMainPage(Integer compId) throws ObjectNotFoundException;

    List<CompilationDto> getAll(Boolean pinned, Integer from, Integer size);

    CompilationDto getCompilation(Integer compId) throws ObjectNotFoundException;
}
