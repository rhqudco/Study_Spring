package com.security.session.repository;

import com.security.session.domain.Member;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MemberRepository {
    private static final Map<String, Member> members = new HashMap<>();
    private final PasswordEncoder passwordEncoder;

    public MemberRepository(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 초기 데이터 세팅을 위한 메서드
     */
    public void postConstruct() {
        save(new Member("admin", passwordEncoder.encode("1234"), "ADMIN"));
        save(new Member("obong", passwordEncoder.encode("1234"), "USER"));
    }

    public void save (Member member) {
        members.put(member.getAccount(), member);
    }

    public Member findByAccount(String account) {
        return members.get(account);
    }

    public List<Member> findAll() {
        return new ArrayList<>(members.values());
    }

    public void clearMember() {
        members.clear();
    }

}
