package jp.co.ctct.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.ctct.model.Lend;
import jp.co.ctct.service.LendService;

@Controller
public class LendListController {

	private final LendService lendService;

	public LendListController(LendService lendService) {
		this.lendService = lendService;
	}

	@GetMapping("/lend_list")
	public String showList(
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate returnedDueAt,
			Model model) {
		List<Lend> lends = lendService.findLends(returnedDueAt);
		model.addAttribute("lends", lends);
		model.addAttribute("returnedDueAt", returnedDueAt);
		model.addAttribute("main", "lend/lend_list::main");
		return "common/layout";
	}
}
