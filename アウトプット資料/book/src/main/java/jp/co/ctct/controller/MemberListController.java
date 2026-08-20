package jp.co.ctct.controller;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.ctct.model.Member;
import jp.co.ctct.service.MemberService;

@Controller
public class MemberListController {

	private final MemberService memberService;

	public MemberListController(MemberService memberService) {
		this.memberService = memberService;
	}

	@GetMapping("/member_list")
	public String showList(@RequestParam(defaultValue = "") String name,
			@RequestParam(defaultValue = "") String phone,
			@RequestParam(defaultValue = "") String email,
			@RequestParam(defaultValue = "0") int page, Model model) {
		Page<Member> members = memberService.searchMembers(name, phone, email, page);
		model.addAttribute("members", members);
		model.addAttribute("name", name);
		model.addAttribute("phone", phone);
		model.addAttribute("email", email);
		model.addAttribute("main", "member/member_list::main");
		return "common/layout";
	}
}
