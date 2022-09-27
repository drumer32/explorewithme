package com.drumer32.explorewithme.service;

import com.drumer32.explorewithme.model.compilation.Compilation;
import com.drumer32.explorewithme.model.compilation.CompilationDto;
import com.drumer32.explorewithme.model.exception.ObjectNotFoundException;
import com.drumer32.explorewithme.tools.ModelMapperConfig;
import com.drumer32.explorewithme.storage.CompilationStorage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
@Slf4j
public class CompilationServiceImpl implements CompilationService {

    private CompilationStorage compilationStorage;

    private ModelMapperConfig mapper;
    @Override
    public Compilation createCompilation(@NotNull Compilation compilation) {
        log.info("Запрос на создание подборки {}", compilation.getTitle());
        return compilationStorage.save(compilation);
    }

    @Override
    public void deleteCompilation(Integer compId) {
        log.info("Запрос на удаление подборки {}", compilationStorage.getReferenceById(compId));
        compilationStorage.deleteById(compId);
    }

    @Override
    public void deleteEventInCompilation(Integer compId, Integer eventId) {
        log.info("Запрос на удаление события {} в подборке {}",
                compilationStorage.getReferenceById(eventId), compilationStorage.getReferenceById(compId));
        compilationStorage.getReferenceById(compId).getEvents().remove(eventId);
    }

    @Override
    public void addEventToCompilation(Integer compId, Integer eventId) {
        log.info("Запрос на добавление события {} в подборке {}",
                compilationStorage.getReferenceById(eventId), compilationStorage.getReferenceById(compId));
        compilationStorage.getReferenceById(compId).getEvents().add(eventId);
    }

    @Override
    public void unpinFromMainPage(Integer compId) {
        log.info("Запрос на прикрепление подборки {} на главную страницу",
                compilationStorage.getReferenceById(compId));
        compilationStorage.getReferenceById(compId).setPinned(true);
    }

    @Override
    public void pinToMainPage(Integer compId) throws ObjectNotFoundException {
        log.info("Запрос на открепление подборки {} с главной страницы",
                compilationStorage.getReferenceById(compId));
        try {
            compilationStorage.findById(compId).get().setPinned(false);
        } catch (NoSuchElementException e) {
            throw new ObjectNotFoundException("Подборка не найдена");
        }
    }

    @Override
    public List<CompilationDto> getAll(Boolean pinned, Integer from, Integer size) {
        log.info("Запрос на получение всех подборок");
        Pageable pageable = PageRequest.of(from, size);
        Page<Compilation> compilationPage = compilationStorage.findAllByPinnedIsTrue(pageable);
        return compilationPage.getContent()
                .stream()
                .map(compilation -> mapper.map().map(compilation, CompilationDto.class))
                .toList();
    }

    @Override
    public CompilationDto getCompilation(Integer compId) throws ObjectNotFoundException {
        log.info("Запрос на получение подборки {}", compId);
        Compilation compilation;
        try {
            compilation = compilationStorage.findById(compId).get();
        } catch (NoSuchElementException e) {
            throw new ObjectNotFoundException("Подборка не найдена");
        }
        return mapper.map().map(compilation, CompilationDto.class);
    }
}
