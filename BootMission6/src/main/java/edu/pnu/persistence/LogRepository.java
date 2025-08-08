package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.log.MyDBLog;

//@Repository
public interface LogRepository extends JpaRepository<MyDBLog, Integer> {
}
