package jp.co.ctct.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jp.co.ctct.model.Member;
import jp.co.ctct.repository.MemberRepository;

@Service
public class MemberService {

	private static final int MEMBERS_PER_PAGE = 5;

	private final MemberRepository memberRepository;

	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public Page<Member> searchMembers(String name, String phone, String email, int page) {
		Pageable pageable = PageRequest.of(page, MEMBERS_PER_PAGE);
		return memberRepository.findByNameContainingAndPhoneContainingAndEmailContaining(
				name, phone, email, pageable);
	}

	public Member findMember(Long id) {
		return memberRepository.findById(id).orElseThrow();
	}

	public void addMember(Member member) {
		memberRepository.save(member);
	}

	public void updateMember(Member editedMember) {
		Member currentMember = findMember(editedMember.getId());
		currentMember.setName(editedMember.getName());
		currentMember.setEmail(editedMember.getEmail());
		currentMember.setAddress(editedMember.getAddress());
		currentMember.setBirth(editedMember.getBirth());
		currentMember.setDeletedAt(editedMember.getDeletedAt());
		currentMember.setPhone(editedMember.getPhone());
		currentMember.setZipCode(editedMember.getZipCode());
		memberRepository.save(currentMember);
	}
}
