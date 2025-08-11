package com.rubypaper.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rubypaper.domain.Board;
import com.rubypaper.domain.Member;
import com.rubypaper.persistence.MemberRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/nplusone")
public class NPlusOneTestController {
	private final MemberRepository memberRepo;
	
	private void printResultList(List<Member> list) {
		System.out.println("=".repeat(80));
		System.out.println("검색 결과");
		for(Member m : list) {
			System.out.println(m);
			for(Board b: m.getBoardList())
				System.out.println(b);
			System.out.println("-".repeat(80));
		}
	}
	
	@GetMapping
	public List<Member> nPlusOneTest() {
		List<Member> list = memberRepo.findAll();
		printResultList(list);
		return list;
	}
	
	@GetMapping("/fetchjoin")
	public List<Member> fetchJoin() {
		List<Member> list = memberRepo.getMembers();
		printResultList(list);
		return list;
	}
	
	@GetMapping("/entitygraph")
	public List<Member> entitygraph() {
		List<Member> list = memberRepo.getMembers();
		printResultList(list);
		return list;
	}
}
