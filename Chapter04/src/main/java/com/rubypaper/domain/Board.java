package com.rubypaper.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity implementation class for Entity: Board
 *
 */


@Entity // 데이터베이스 테이블과 매핑된 클래스를 Entity Class라고 한다.
//@Table(name="BOARD") // 명시해주지 않으면 클래스 이름이 곧 테이블 이름이 된다.
//@Table(name="E_BOARD", uniqueConstraints = {@UniqueConstraint(columnNames = {"SEQ", "WRITER"})})
@TableGenerator(name = "BOARD_SEQ_GENERATOR",
table = "ALL_SEQUENCES",
pkColumnValue = "BOARD_SEQ",
initialValue = 0,
allocationSize = 1)
public class Board { // @Table을 명시하지 않을때, 주의해야할 것은 "카멜 케이스"인 경우 BoardTable인 경우 board_table로 해석이 됨
	
	@Id // 이게 붙어 있는 필드가 primary key, JPA는 pk가 반드시 필요!
	//@GeneratedValue(strategy=GenerationType.IDENTITY) // Auto Increment(int, Long만 가능)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="BOARD_SEQ_GENERATOR")
	private Long seq;
	@Column(nullable = false)
	private String title;
	private String writer;
	private String content;
	@Temporal(TemporalType.DATE)
	private Date createDate;
	private Long cnt;
	
	public Long getSeq() {
		return seq;
	}
	public void setSeq(Long seq) {
		this.seq = seq;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Long getCnt() {
		return cnt;
	}
	public void setCnt(Long cnt) {
		this.cnt = cnt;
	}
	
	@Override
	public String toString() {
		return "Board [seq=" + seq + ", title=" + title + ", writer=" + writer + ", content=" + content
				+ ", createDate=" + createDate + ", cnt=" + cnt + "]";
	}
}
