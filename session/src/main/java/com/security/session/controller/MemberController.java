package com.security.session.controller;

import com.security.session.controller.dto.MemberDto;
import com.security.session.controller.responseMsg.CustomMessage;
import com.security.session.controller.responseMsg.EnumStatus;
import com.security.session.controller.responseMsg.GenerateHttpHeader;
import com.security.session.domain.Member;
import com.security.session.sevice.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1/member")
public class MemberController {

    private final MemberService memberService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping("/join")
    public ResponseEntity<CustomMessage> joinMember(@RequestBody Member member) {
        memberService.join(new Member(member.getAccount(), member.getPassword(), member.getLevel()));

        CustomMessage customMessage = new CustomMessage(EnumStatus.OK, "success", member);
        HttpHeaders headers = GenerateHttpHeader.returnHttpHeader("application", "json");

        return new ResponseEntity<>(customMessage, headers, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<CustomMessage> login(@RequestBody MemberDto memberDto) throws IllegalAccessException {
        memberService.login(memberDto);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberDto.getAccount(), memberDto.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject()
                .authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        CustomMessage customMessage = new CustomMessage(EnumStatus.OK, "success", "로그인 성공");
        HttpHeaders headers = GenerateHttpHeader.returnHttpHeader("application", "json");

        return new ResponseEntity<>(customMessage, headers, HttpStatus.OK);
    }


    @PostMapping("/find/member")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<CustomMessage> findMember(@RequestBody MemberDto memberDto) throws IllegalAccessException {
        Member loginMember = memberService.findMember(memberDto);

        CustomMessage customMessage = new CustomMessage(EnumStatus.OK, "success", loginMember);
        HttpHeaders headers = GenerateHttpHeader.returnHttpHeader("application", "json");

        return new ResponseEntity<>(customMessage, headers, HttpStatus.OK);
    }

    @GetMapping("/inquiry/all")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<CustomMessage> allMembers() {
        List<Member> members = memberService.inquiryAllMembers();

        CustomMessage customMessage = new CustomMessage(EnumStatus.OK, "success", members);
        HttpHeaders headers = GenerateHttpHeader.returnHttpHeader("application", "json");

        return new ResponseEntity<>(customMessage, headers, HttpStatus.OK);
    }

    @GetMapping("/delete/all")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<CustomMessage> clearAll() {
        memberService.deleteMembers();

        CustomMessage customMessage = new CustomMessage(EnumStatus.OK, "success", "Delete All Member!!!");
        HttpHeaders headers = GenerateHttpHeader.returnHttpHeader("application", "json");

        return new ResponseEntity<>(customMessage, headers, HttpStatus.OK);
    }
}
