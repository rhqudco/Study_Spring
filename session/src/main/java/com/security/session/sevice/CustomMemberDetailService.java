package com.security.session.sevice;

import com.security.session.domain.Member;
import com.security.session.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("memberDetailService")
@RequiredArgsConstructor
@Slf4j
public class CustomMemberDetailService implements UserDetailsService {

  private final MemberRepository memberRepository;

  @Override
  public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
    log.info("loadUserByUsername");
    Member member = memberRepository.findByAccount(account);
    UserDetails userDetails = new User(member.getAccount(), member.getPassword(), AuthorityUtils.createAuthorityList("ROLE_"+ member.getLevel()));

    log.info("userDetails = {}", userDetails);

    return userDetails;
  }
}
