package edu.pnu.machine;

public class Main {

	// DI(Dependency Injection)
	public static void main(String[] args) {
		CoffeeMaker coffeeMaker = new CoffeeMaker();
		coffeeMaker.setCoffeeMachine(new DripCoffeeMachine());
		coffeeMaker.makeCoffee();
	}

}
