package com.drumer32.explorewithme.controller.isadmin;

import com.drumer32.explorewithme.model.compilation.Compilation;
import com.drumer32.explorewithme.model.exception.ObjectNotFoundException;
import com.drumer32.explorewithme.service.CompilationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/compilations")
public class AdminCompilationController {

    private final CompilationService compilationService;

    @PostMapping
    public Compilation createCompilation(@RequestBody Compilation compilation) {
        return compilationService.createCompilation(compilation);
    }

    @DeleteMapping("/{compId}")
    public void deleteCompilation(@PathVariable Integer compId) {
        compilationService.deleteCompilation(compId);
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    public void deleteEventInCompilation(@PathVariable Integer compId,
                                         @PathVariable Integer eventId) {
        compilationService.deleteEventInCompilation(compId, eventId);
    }

    @PatchMapping("/{compId}/events/{eventId}")
    public void addEventToCompilation(@PathVariable Integer compId,
                                      @PathVariable Integer eventId) {
        compilationService.addEventToCompilation(compId, eventId);
    }

    @DeleteMapping("/admin/compilations/{compId}/pin")
    public void unpinFromMainPage(@PathVariable Integer compId) {
        compilationService.unpinFromMainPage(compId);
    }

    @PatchMapping("/admin/compilations/{compId}/pin")
    public void pinToMainPage(@PathVariable Integer compId) throws ObjectNotFoundException {
        compilationService.pinToMainPage(compId);
    }
}
