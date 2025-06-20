package com.zjj.domain.component.event.endpoint;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.modulith.events.EventPublication;
import org.springframework.modulith.events.IncompleteEventPublications;
import org.springframework.modulith.events.core.EventPublicationRepository;
import org.springframework.modulith.events.core.TargetEventPublication;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Endpoint(id = "modulith-events")
public class ModulithEventsActuatorEndpoint {

    private final IncompleteEventPublications incompleteEventPublications;
    private final EventPublicationRepository eventPublicationRepository;

    public ModulithEventsActuatorEndpoint(
            IncompleteEventPublications incompleteEventPublications,
            EventPublicationRepository eventPublicationRepository
    ) {
        this.incompleteEventPublications = incompleteEventPublications;
        this.eventPublicationRepository = eventPublicationRepository;
    }

    /**
     * 分页查询未完成的事件
     * GET /actuator/modulith-events/incomplete?page=0&size=10
     */
    @ReadOperation
    public List<TargetEventPublication> getIncompleteEvents() {
        return eventPublicationRepository.findIncompletePublications();
    }

    /**
     * 获取所有信息的根端点
     * GET /actuator/modulith-events
     */
    @ReadOperation
    public Map<String, Object> getModulithEventsInfo() {
        try {
            List<TargetEventPublication> incompleteEvents = eventPublicationRepository.findIncompletePublications();

            return Map.of(
                    "incompleteCount", incompleteEvents.size(),
                    "oldestIncompleteEvent", incompleteEvents.stream()
                            .map(EventPublication::getPublicationDate)
                            .min(Instant::compareTo)
                            .orElse(null),
                    "availableOperations", List.of(
                            "GET /actuator/modulith-events/incomplete - 获取未完成事件列表",
                            "POST /actuator/modulith-events/republish-all - 重新发布所有事件",
                            "POST /actuator/modulith-events/republish/{id} - 重新发布指定事件",
                            "POST /actuator/modulith-events/complete/{id} - 标记事件完成"
                    )
            );
        } catch (Exception e) {
            return Map.of(
                    "error", "Failed to get modulith events info: " + e.getMessage()
            );
        }
    }


    @WriteOperation
    public Map<String, Object> handleWriteOperation(
            @Selector String operation,
            @Selector(match = Selector.Match.ALL_REMAINING) String[] remaining) {

        switch (operation) {
            case "republish-all":
                return republishAllEvents();
            case "republish":
                if (remaining.length == 1) {
                    return republishEvent(remaining[0]);
                } else {
                    return Map.of("error", "Event ID is required for republish operation");
                }
            case "complete":
                if (remaining.length == 1) {
                    return completeEvent(remaining[0]);
                } else {
                    return Map.of("error", "Event ID is required for complete operation");
                }
            default:
                return Map.of("error", "Unknown operation: " + operation);
        }
    }

    /**
     * 根据ID重新发布单个事件
     * POST /actuator/modulith-events/republish/{id}
     */
    @WriteOperation
    public Map<String, Object> republishEvent(@Selector String id) {
        try {
            UUID eventId = UUID.fromString(id);
            incompleteEventPublications.resubmitIncompletePublications(publication -> publication.getIdentifier().equals(eventId));
            return Map.of(
                    "success", true,
                    "message", "Event republished successfully",
                    "eventId", id
            );
        } catch (Exception e) {
            return Map.of(
                    "success", false,
                    "message", "Failed to republish event: " + e.getMessage(),
                    "eventId", id
            );
        }
    }

    /**
     * 写操作处理器 - 处理所有写操作
     * POST /actuator/modulith-events/{operation}
     * POST /actuator/modulith-events/{operation}/{id}
     */
    @WriteOperation
    public Map<String, Object> republishAllEvents() {
        try {
            incompleteEventPublications.resubmitIncompletePublications(
                    publication -> true // 重新发布所有未完成事件
            );

            return Map.of(
                    "success", true,
                    "message", "All incomplete events republished successfully"
            );
        } catch (Exception e) {
            return Map.of(
                    "success", false,
                    "message", "Failed to republish all events: " + e.getMessage(),
                    "republishedCount", 0
            );
        }
    }

    /**
     * 根据ID直接标记事件为完成状态
     * POST /actuator/modulith-events/complete/{id}
     */
    @WriteOperation
    public Map<String, Object> completeEvent(String id) {
        try {
            UUID eventId = UUID.fromString(id);
            eventPublicationRepository.markCompleted(eventId, Instant.now());

            return Map.of(
                    "success", true,
                    "message", "Event marked as completed successfully",
                    "eventId", id
            );
        } catch (IllegalArgumentException e) {
            return Map.of(
                    "success", false,
                    "message", "Invalid event ID format",
                    "eventId", id
            );
        } catch (Exception e) {
            return Map.of(
                    "success", false,
                    "message", "Failed to complete event: " + e.getMessage(),
                    "eventId", id
            );
        }
    }
}
