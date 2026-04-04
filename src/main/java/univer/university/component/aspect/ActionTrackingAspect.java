package univer.university.component.aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import univer.university.component.annotation.TrackAction;
import univer.university.service.ActionEventService;

@Aspect
@Component
@RequiredArgsConstructor
public class ActionTrackingAspect {

    private final ActionEventService actionEventService;

    @AfterReturning(pointcut = "@annotation(trackAction)", returning = "result")
    public void trackAction(JoinPoint joinPoint, TrackAction trackAction, Object result) {
        String methodName = joinPoint.getSignature().getName();
        String description = "Action " + trackAction.value().name() + " executed via method: " + methodName;
        
        actionEventService.handle(trackAction.value(), description, result);
    }
}
