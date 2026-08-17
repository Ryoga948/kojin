package jp.co.ctct.controller;

import org.springframework.stereotype.Controller;

import jp.co.ctct.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MemberController {

	private final MemberRepository memberRepository;

	//コードの追加

}
