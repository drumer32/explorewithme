package com.drumer32.explorewithme.controller.isprivate;

import com.drumer32.explorewithme.model.exception.AccessException;
import com.drumer32.explorewithme.model.exception.BadConditionException;
import com.drumer32.explorewithme.model.exception.ConflictException;
import com.drumer32.explorewithme.model.exception.ObjectNotFoundException;
import com.drumer32.explorewithme.model.requests.ParticipationRequest;
import com.drumer32.explorewithme.service.RequestService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/users")
@AllArgsConstructor
public class PrivateRequestController {

    private final RequestService requestService;

    @GetMapping("/{userId}/requests")
    public ParticipationRequest getRequestsOfUser(@PathVariable Integer userId) {
        return requestService.getParticipationRequestByRequester(userId);
    }

    @PostMapping("/{userId}/requests")
    public ParticipationRequest createRequest(@PathVariable Integer userId,
                                              @RequestParam Integer eventId) throws ObjectNotFoundException, BadConditionException, AccessException, ConflictException {
        return requestService.createRequest(userId, eventId);
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public ParticipationRequest cancelRequest(@PathVariable Integer userId,
                                              @PathVariable Integer requestId) throws ObjectNotFoundException {
        return requestService.cancelRequest(userId, requestId);
    }
}
