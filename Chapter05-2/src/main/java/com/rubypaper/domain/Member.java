package com.rubypaper.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude="boardList")// 양방향 참조에서 순환 참조 발생하므로 exclude해주기! -> 순환참조 일어나는 엔티티 중에 하나만 해도 된다!
@Entity
public class Member {

	@Id
	@Column(name="MEMBER_ID")
	private String id;
	private String password;
	private String name;
	private String role;
	
	// default lazy, mappedBy는 fk를 가지는 Entity 여기선 board가 주인.
	// cascade 속성은 데이터베이스마다 다르기 때문에 확인 후 사용하는 것이 좋다!
	// cascade 속성은 1:다 에서 1인 곳에만 사용하는 것이 좋다!
	@OneToMany(mappedBy="member", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private List<Board> boardList = new ArrayList<>();
}
