package com.security.session.sevice;

import com.security.session.controller.dto.MemberDto;
import com.security.session.domain.Member;
import com.security.session.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void join(Member member) {
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        memberRepository.save(member);
    }

    public void login(MemberDto memberDto) throws IllegalAccessException {
        Member findMember = memberRepository.findByAccount(memberDto.getAccount());
        validateLogin(memberDto.getPassword(), findMember.getPassword());
    }

    public Member findMember(MemberDto memberDto) throws IllegalAccessException {
        Member findMember = memberRepository.findByAccount(memberDto.getAccount());
        validateLogin(memberDto.getPassword(), findMember.getPassword());
        return findMember;
    }

    public List<Member> inquiryAllMembers() {
        List<Member> findMembers = memberRepository.findAll();
        return findMembers;
    }

    public void deleteMembers() {
        memberRepository.clearMember();
    }

    private void validateLogin(String rawPassword, String encodedPassword) throws IllegalAccessException {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new IllegalAccessException("비밀번호를 확인해 주세요.");
        }
    }
}
