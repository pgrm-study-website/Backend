package plming.notification.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import plming.auth.service.JwtTokenProvider;
import plming.notification.common.ResponseService;
import plming.notification.dto.MultipleResult;
import plming.notification.dto.NotificationDto;
import plming.notification.dto.NotificationResponseDto;
import plming.notification.dto.SingleResult;
import plming.notification.service.NotificationService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final ResponseService responseService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping(value = "/subscribe", produces = "text/event-stream")
    @ResponseStatus(HttpStatus.OK)
    public SseEmitter subscribe(@CookieValue String token,
                                @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {

        return notificationService.subscribe(jwtTokenProvider.getUserId(token), lastEventId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public MultipleResult<NotificationResponseDto> findAllNotification(@CookieValue String token) {

        List<NotificationDto> notifications = notificationService.findAllNotifications(jwtTokenProvider.getUserId(token));
        List<NotificationResponseDto> responseDto = responseService.convertToControllerDto(notifications, NotificationResponseDto::create);
        return responseService.getMultipleResult(responseDto);
    }

    @GetMapping("/count")
    @ResponseStatus(HttpStatus.OK)
    public SingleResult<Long> countUnReadNotifications(@CookieValue String token) {

        Long count = notificationService.countUnReadNotifications(jwtTokenProvider.getUserId(token));
        return responseService.getSingleResult(count);
    }
}
