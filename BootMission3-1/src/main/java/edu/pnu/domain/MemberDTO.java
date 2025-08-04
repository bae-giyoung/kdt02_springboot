package edu.pnu.domain;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDTO {
	/*
	 * id int auto_increment primary key
	 * pass varchar(10)
	 * name varchar(20)
	 * regidate date default (curdate()) not null
	 */
	
	private Integer id;
	private String pass;
	private String name;
	private Date regidate;
}
