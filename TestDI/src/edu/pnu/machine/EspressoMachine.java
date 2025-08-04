package edu.pnu.machine;

public class EspressoMachine implements CoffeeMachine {
	
	@Override
	public String brew() {
		return "Espresso Machine에서 커피를 추출합니다.";
	}
}
