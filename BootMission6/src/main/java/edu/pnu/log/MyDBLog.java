package edu.pnu.log;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
@Table(name="dbLog")
public class MyDBLog {
	/* [dblog]
	 * id int auto_increment primary key,
	 * method varchar(10) not null,
	 * sqlstring varchar(256) not null,
	 * regidate date default(curdate()) not null,
	 * success boolean default true
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String method;
	private String namesqlstring;
	private Date regidate;
	private boolean success;
}
