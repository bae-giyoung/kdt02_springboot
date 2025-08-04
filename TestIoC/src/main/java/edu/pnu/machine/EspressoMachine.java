package edu.pnu.machine;

import org.springframework.stereotype.Component;

@Component // espressoMachine이라는 이름으로 인스턴스를 만들어 IoC Container에 저장
public class EspressoMachine implements CoffeeMachine {
	
	@Override
	public String brew() {
		return "Espresso Machine에서 커피를 추출합니다.";
	}
}
