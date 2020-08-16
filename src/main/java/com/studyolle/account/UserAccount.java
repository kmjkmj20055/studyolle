package com.studyolle.account;


import com.studyolle.Account;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

@Getter
public class UserAccount extends User {
    // Spring Security에서 다루는 user 정보 / 우리 도메인에서 다루는 user 정보
    // 사이의 어댑터라고 생각하면 될듯 (갭을 매꿔주는)

    private Account account;

    // Spring Security가 갖고있는 user 정보랑 우리가 갖고있는 user 정보랑 연
   public UserAccount(Account account) {
        super(account.getNickname(), account.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
        this.account = account;
    }

}
