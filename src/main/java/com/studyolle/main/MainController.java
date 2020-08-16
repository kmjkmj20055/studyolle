package com.studyolle.main;

import com.studyolle.Account;
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
}
