package univer.university.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import univer.university.dto.ApiResponse;
import univer.university.entity.ActionEvent;
import univer.university.entity.enums.ActionType;
import univer.university.repository.ActionEventRepository;
import univer.university.security.CurrentActor;
import univer.university.security.SecurityUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@RequiredArgsConstructor
public class ActionEventService {
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();


    private final SecurityUtil securityUtil;
    private final ActionEventRepository repository;

    public void handle(ActionType type, String description, Object result) {
        CurrentActor actor = securityUtil.getCurrentActor();

        ActionEvent event = new ActionEvent();
        event.setActionType(type);
        event.setDescription(description);
        event.setUserId(actor.getId());
        event.setUserRole(actor.getRole());
        event.setCreatedAt(LocalDateTime.now());

        // Extract entity info if result is an ApiResponse or a BaseEntity
        Object data = result;
        if (result instanceof ApiResponse<?> apiResponse) {
            data = apiResponse.getData();
        }

        if (data instanceof univer.university.entity.base.BaseEntity be) {
            event.setEntityId(be.getId());
            event.setEntityName(data.getClass().getSimpleName());
        } else if (data != null) {
            event.setEntityName(data.getClass().getSimpleName());
        }

        repository.save(event);
        send(event);
    }



    public SseEmitter subscribe() {
        SseEmitter emitter = new SseEmitter(0L); // cheksiz timeout

        emitters.add(emitter);

        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        emitter.onError(e -> emitters.remove(emitter));

        return emitter;
    }

    // Action bo‘lganda chaqiriladi
    public void send(ActionEvent event) {
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(
                        SseEmitter.event()
                                .name("system-action")
                                .data(event)
                );
            } catch (Exception e) {
                emitters.remove(emitter);
            }
        }
    }
}
