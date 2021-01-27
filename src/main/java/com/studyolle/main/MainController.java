package com.studyolle.main;

import com.studyolle.account.Account;
import com.studyolle.account.AccountController;
import com.studyolle.account.CurrentUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    // 첫 페이지로 가는 요청 처리

    @GetMapping("/")
    public String home(@CurrentUser Account account, Model model) {

        // 익명 사용자 == null / 아닌경우 account 객체로

        if (account != null) {
            model.addAttribute(account);
        }

        return "index";
    }

    @GetMapping("/login")
    public String login() {

        //원래 prefix, suffix가 붙어서 templates/login.html 이렇게 되는데
        // ViewResolver에 의해서 생략된거
        return "login";
    }
}
