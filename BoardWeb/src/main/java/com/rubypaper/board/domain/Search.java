package com.rubypaper.board.domain;

import lombok.Data;

@Data // @Getter @Setter @ToString 모두 포함
public class Search {
	private String searchCondition;
	private String searchKeyword;
}
