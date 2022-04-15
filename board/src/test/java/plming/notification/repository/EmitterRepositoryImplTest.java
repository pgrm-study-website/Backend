package plming.notification.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import plming.notification.entity.Notification;
import plming.notification.entity.NotificationType;
import plming.user.entity.User;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmitterRepositoryImplTest {

    private EmitterRepository emitterRepository = new EmitterRepositoryImpl();
    private Long DEFAULT_TIMEOUT = 60L * 1000L * 60L;

    @Test
    @DisplayName("새로운 Emitter 추가")
    void save() throws Exception {

        // given
        Long userId = 1L;
        String emitterId = userId + "_" + System.currentTimeMillis();
        SseEmitter sseEmitter = new SseEmitter(DEFAULT_TIMEOUT);

        // when, then
        assertDoesNotThrow(() -> emitterRepository.save(emitterId, sseEmitter));
    }

    @Test
    @DisplayName("수신한 이벤트를 캐시에 저장")
    void saveEventCache() throws Exception {

        User user = User.builder()
                .nickname("nickname")
                .email("email@gmail.com")
                .role("ROLE_USER")
                .social(0)
                .build();

        // given
        Long userId = 1L;
        String eventCached = userId + "_" + System.currentTimeMillis();
        Notification notification = new Notification(user, NotificationType.APPLY, "게시글에 참여 신청 요청이 왔습니다.", "url", false);

        // when, then
        assertDoesNotThrow(() -> emitterRepository.saveEventCache(eventCached, notification));
    }

    @Test
    @DisplayName("사용자가 접속한 모든 Emitter를 찾는다")
    void findAllEmitterStartWithByUserId() throws Exception {

        // given
        Long userId = 1L;
        String emitterId1 = userId + "_" + System.currentTimeMillis();
        emitterRepository.save(emitterId1, new SseEmitter(DEFAULT_TIMEOUT));

        Thread.sleep(100);
        String emitterId2 = userId + "_" + System.currentTimeMillis();
        emitterRepository.save(emitterId2, new SseEmitter(DEFAULT_TIMEOUT));

        Thread.sleep(100);
        String emitterId3 = userId + "_" + System.currentTimeMillis();
        emitterRepository.save(emitterId3, new SseEmitter(DEFAULT_TIMEOUT));

        // when
        Map<String, SseEmitter> ActualResult = emitterRepository.findAllEmitterStartWithByUserId(String.valueOf(userId));

        // then
        assertEquals(3, ActualResult.size());
    }

    @Test
    @DisplayName("사용자에게 수신된 이벤트를 캐시에서 모두 찾는다")
    void findAllEventCacheStartWithByUserId() throws Exception {

        // given
        User user = User.builder()
                .nickname("nickname")
                .email("email@gmail.com")
                .role("ROLE_USER")
                .social(0)
                .build();
        Long userId = 1L;
        String eventCacheId1 = userId + "_" + System.currentTimeMillis();
        Notification notification1 = new Notification(user, NotificationType.APPLY, "게시글에 참여 신청이 왔습니다", "url", false);
        emitterRepository.saveEventCache(eventCacheId1, notification1);

        Thread.sleep(100);
        String eventCacheId2 = userId + "_" + System.currentTimeMillis();
        Notification notification2 = new Notification(user, NotificationType.ACCEPT, "게시글 참여가 승인되었습니다.", "url", false);
        emitterRepository.saveEventCache(eventCacheId2, notification2);

        Thread.sleep(100);
        String eventCacheId3 = userId + "_" + System.currentTimeMillis();
        Notification notification3 = new Notification(user, NotificationType.REJECT, "게시글 참여가 거절되었습니다..", "url", false);
        emitterRepository.saveEventCache(eventCacheId3, notification3);

        // when
        Map<String, Object> ActualResult = emitterRepository.findAllEventCacheStartWithByUserId(String.valueOf(userId));

        // then
        assertEquals(3, ActualResult.size());
    }

    @Test
    @DisplayName("Id를 사용해 Emitter를 Repository에서 제거")
    void deleteById() throws Exception {

        // given
        Long userId = 1L;
        String emitterId = userId + "_" + System.currentTimeMillis();
        SseEmitter sseEmitter = new SseEmitter(DEFAULT_TIMEOUT);

        // when
        emitterRepository.save(emitterId, sseEmitter);
        emitterRepository.deleteById(emitterId);

        // then
        assertEquals(0, emitterRepository.findAllEmitterStartWithByUserId(String.valueOf(userId)).size());
    }

    @Test
    @DisplayName("저장된 모든 Emitter를 제거한다.")
    void deleteAllEmitterStartWithId() throws Exception {

        // given
        Long userId = 1L;
        String emitterId1 = userId + "_" + System.currentTimeMillis();
        emitterRepository.save(emitterId1, new SseEmitter(DEFAULT_TIMEOUT));

        Thread.sleep(100);
        String emitterId2 = userId + "_" + System.currentTimeMillis();
        emitterRepository.save(emitterId2, new SseEmitter(DEFAULT_TIMEOUT));

        // when
        emitterRepository.deleteAllEmitterStartWithId(String.valueOf(userId));

        // then
        assertEquals(0, emitterRepository.findAllEmitterStartWithByUserId(String.valueOf(userId)).size());
    }

    @Test
    @DisplayName("저장된 모든 캐시를 제거")
    void deleteAllEventCacheStartWithId() throws Exception {

        // given
        User user = User.builder()
                .nickname("nickname")
                .email("email@gmail.com")
                .role("ROLE_USER")
                .social(0)
                .build();
        Long userId = 1L;
        String eventCacheId1 = userId + "_" + System.currentTimeMillis();
        Notification notification1 = new Notification(user, NotificationType.APPLY, "게시글에 참여 신청이 왔습니다.", "url", false);
        emitterRepository.saveEventCache(eventCacheId1, notification1);

        Thread.sleep(100);
        String eventCacheId2 = userId + "_" + System.currentTimeMillis();
        Notification notification2 = new Notification(user, NotificationType.ACCEPT, "게시글 참여가 승인되었습니다.", "url", false);
        emitterRepository.saveEventCache(eventCacheId2, notification2);

        // when
        emitterRepository.deleteAllEventCacheStartWithId(String.valueOf(userId));

        // then
        assertEquals(0, emitterRepository.findAllEventCacheStartWithByUserId(String.valueOf(userId)).size());
    }
}
