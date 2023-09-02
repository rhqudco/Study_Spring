package com.security.session;

import com.security.session.repository.MemberRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class SessionApplication {

	public static void main(String[] args) {
		SpringApplication.run(SessionApplication.class, args);
	}

	private final MemberRepository memberRepository;

	public SessionApplication(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	@PostConstruct
	public void init() {
		memberRepository.postConstruct();
	}

}
