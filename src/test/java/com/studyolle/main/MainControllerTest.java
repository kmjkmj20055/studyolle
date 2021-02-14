package com.studyolle.main;

import com.studyolle.account.AccountRepository;
import com.studyolle.account.AccountService;
import com.studyolle.account.SignUpForm;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MainControllerTest {

//    Autowired로 주입받는 이유
//    JUnit5가 dependencyInjection을 지원하는데, 지원하는 타입이 정해져있음
//    ex) private final MockMvc 로 하면 JUnit이 먼저 생성자에 다른 인스턴스를 넣으려고 시도하기 때문에
    @Autowired MockMvc mockMvc;
    @Autowired AccountService accountService;
    @Autowired AccountRepository accountRepository;

    @BeforeEach
    void beforeEach() {
//      아래꺼 수행되면 여기에 해당되는 계정이 만들어질거임
        SignUpForm signUpForm = new SignUpForm();
        signUpForm.setNickname("minjeong");
        signUpForm.setEmail("kmjkmj20055@gmail.com");
        signUpForm.setPassword("12345678");
        accountService.processNewAccount(signUpForm);
    }

    @AfterEach
    void afterEach() {
        accountRepository.deleteAll();
    }


    @DisplayName("이메일로 로그인 성공")
    @Test
    void login_with_email() throws Exception {
        mockMvc.perform(post("/login") // SpringSecurity가 로그인 처리해줄거임 / 로그인되면 리다이렉트 발생
                .param("username","kmjkmj20055@gmail.com")  // SpringSecurity에 username, password 정해져있는 파라미터 / 값은 커스터마이징 가능 ㅇㅇ
                .param("password","12345678")
                .with(csrf()))//SpringSecurity를 쓰면 기본적으로 CSRF가 활성화 되어있음 -> 그래서 CSRF 토큰이 같이 전성되어야
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))  // 성공할 경우 루트로 리다이렉트
                .andExpect(authenticated().withUsername("minjeong"));  //"minjeong"로 인증되는 이유 : UserAccount에서 username부분을 nickname으로 리턴
    }

    @DisplayName("이메일로 로그인 성공")
    @Test
    void login_with_nickname() throws Exception {
        mockMvc.perform(post("/login") // SpringSecurity가 로그인 처리해줄거임 / 로그인되면 리다이렉트 발생
                .param("username","minjeong")  // SpringSecurity에 username, password 정해져있는 파라미터 / 값은 커스터마이징 가능 ㅇㅇ
                .param("password","12345678")
                .with(csrf()))//SpringSecurity를 쓰면 기본적으로 CSRF가 활성화 되어있음 -> 그래서 CSRF 토큰이 같이 전성되어야
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))  // 성공할 경우 루트로 리다이렉트
                .andExpect(authenticated().withUsername("minjeong"));  //"minjeong"로 인증되는 이유 : UserAccount에서 username부분을 nickname으로 리턴
    }

    @DisplayName("로그인 실패")
    @Test
    void login_fail() throws Exception {
        mockMvc.perform(post("/login") // SpringSecurity가 로그인 처리해줄거임 / 로그인되면 리다이렉트 발생
                .param("username","111111")  // SpringSecurity에 username, password 정해져있는 파라미터 / 값은 커스터마이징 가능 ㅇㅇ
                .param("password","000000")
                .with(csrf()))//SpringSecurity를 쓰면 기본적으로 CSRF가 활성화 되어있음 -> 그래서 CSRF 토큰이 같이 전성되어야
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"))  // 실패할 경우 error로 리다이렉션
                .andExpect(unauthenticated());
    }

    @DisplayName("로그아웃")
    @Test
    void logout() throws Exception {
        mockMvc.perform(post("/logout") // SpringSecurity가 로그인 처리해줄거임 / 로그인되면 리다이렉트 발생
                .with(csrf()))//SpringSecurity를 쓰면 기본적으로 CSRF가 활성화 되어있음 -> 그래서 CSRF 토큰이 같이 전성되어야
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))  // 실패할 경우 error로 리다이렉션
                .andExpect(unauthenticated());
    }
}
