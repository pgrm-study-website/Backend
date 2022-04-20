//package plming.board.controller;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import plming.board.entity.Application;
//import plming.common.ControllerIntegrationTest;
//
//import javax.servlet.http.Cookie;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//public class BoardApiControllerTest extends ControllerIntegrationTest {
//
//    @Test
//    @DisplayName("게시글 작성자는 자동으로 참여 승인")
//    void save() throws Exception {
//
//        // given
//        String token1 = jwtTokenProvider.createToken(studyWriter.getId());
//
//        // when
//        applicationRepository.save(new Application(studyWriter, study, "승인"));
//
//        // then
//        mockMvc.perform(get("/posts/" + study.getId() + "/application")
//                        .cookie(new Cookie("token", token1)))
//                .andExpect(content().string("승인"));
//    }
//
//    @Test
//    @DisplayName("특정 게시글 신청 상태 확인")
//    void findApplicationStatus() throws Exception {
//
//        // given
//        String token1 = jwtTokenProvider.createToken(studyApplyMember.getId());
//        String token2 = jwtTokenProvider.createToken(notStudyMember.getId());
//        applicationRepository.save(new Application(studyApplyMember, study, "대기"));
//
//        // when
//        String appliedstatus = boardService.findApplicationStatus(study.getId(), studyApplyMember.getId());
//
//        // then
//        mockMvc.perform(get("/posts/" + study.getId() + "/application")
//                        .cookie(new Cookie("token", token1)))
//                .andExpect(status().isOk())
//                .andExpect(content().string(appliedstatus));
//        mockMvc.perform(get("/posts/" + study.getId() + "/application")
//                        .cookie(new Cookie("token", token2)))
//                .andExpect(status().isOk())
//                .andExpect(content().string("미신청"));
//    }
//}
