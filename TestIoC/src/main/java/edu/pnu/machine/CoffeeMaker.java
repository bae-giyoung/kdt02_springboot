package edu.pnu.machine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component // coffeeMaker라는 이름으로 자동으로 인스턴스가 만들어서 IoC컨테이너에 저장
public class CoffeeMaker {
	@Autowired // 자동으로 할당해라 coffeeMachine 타입 객체를
	private CoffeeMachine coffeeMachine;// 이 타입이거나 이 타입으로 부터 상속된 것의 인스턴스가 있으면 넣어 준다-> 여러개 있다면?? -> APPLICATION FAILED TO START
	
	@PostConstruct // 의존성 주입이 완료되어야 하는 method에 사용, 다른 리소스에서 호출되지 않아도 수행, 생성자보다 늦게 호출
	public void makeCoffee() {
		System.out.println(coffeeMachine.brew());
	}
}

// Spring Boot는 기본적으로 싱글톤 -> 여기에 대해서!!!!

// [호출 순서]
// 1. 생성자 호출
// 2. 의존성 주입 완료 (@Autowired || @RequiredAgsConstructor)
// 3. @PostConstruct
// [사용 이유]
// - 생성자가 호출되었을 때, bean은 초기화 전이다.(DI가 이루어지기 전) @PostConstruct를 사용하면, bean이 초기화 됨과 동시에 의존성을 확인할 수 있다.
// - bean lifeCycle에서 오직 한 번만 수행된다.(여러 번 초기화 방지)
// (출처: https://velog.io/@limsubin/Spring-Boot%EC%97%90%EC%84%9C-PostConstruct-%EC%95%8C%EC%95%84%EB%B3%B4%EC%9E%90)