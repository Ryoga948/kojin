package jp.co.ctct.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import jp.co.ctct.model.Member;
import jp.co.ctct.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MemberController {

	private final MemberRepository memberRepository;

	//コードの追加
	@GetMapping("/member_list")
	public String showList(Model model) {
		model.addAttribute("members", memberRepository.findAll());
		model.addAttribute("main", "member/member_list::main");
		return "common/layout";
	}

	@GetMapping("/member_add")
	public String showAdd(Model model) {
		model.addAttribute("member", new Member());
		model.addAttribute("main", "member/member_add::main");
		return "common/layout";
	}

	@PostMapping("/member_add")
	public String add(@Valid Member member, BindingResult result, Model model,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			model.addAttribute("main", "member/member_add::main");
			return "common/layout";
		}
		memberRepository.save(member);
		redirectAttributes.addFlashAttribute("message", "会員情報を登録しました。");
		return "redirect:/member_list";
	}

	@GetMapping("/member_edit/{id}")
	public String showEdit(@PathVariable Long id, Model model) {
		Member member = memberRepository.findById(id).orElseThrow();
		model.addAttribute("member", member);
		model.addAttribute("main", "member/member_edit::main");
		return "common/layout";
	}

	@PostMapping("/member_edit")
	public String edit(@Valid Member member, BindingResult result, Model model,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			model.addAttribute("main", "member/member_edit::main");
			return "common/layout";
		}
		memberRepository.save(member);
		redirectAttributes.addFlashAttribute("message", "会員情報を変更しました。");
		return "redirect:/member_list";
	}

}
