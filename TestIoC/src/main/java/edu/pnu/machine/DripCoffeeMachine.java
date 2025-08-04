package edu.pnu.machine;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class DripCoffeeMachine implements CoffeeMachine {
	
	@Override
	public String brew() {
		return "Drip Coffee Machine에서 커피를 추출합니다.";
	}
}

/*
***************************
APPLICATION FAILED TO START
***************************

Description:

Field coffeeMachine in edu.pnu.machine.CoffeeMaker required a single bean, but 2 were found:
	- dripCoffeeMachine: defined in file [D:\_workspace_sts4\TestIoC\target\classes\edu\pnu\machine\DripCoffeeMachine.class]
	- espressoMachine: defined in file [D:\_workspace_sts4\TestIoC\target\classes\edu\pnu\machine\EspressoMachine.class]

This may be due to missing parameter name information

Action:

Consider marking one of the beans as @Primary, updating the consumer to accept multiple beans, or using @Qualifier to identify the bean that should be consumed

Ensure that your compiler is configured to use the '-parameters' flag.
You may need to update both your build tool settings as well as your IDE.
(See https://github.com/spring-projects/spring-framework/wiki/Spring-Framework-6.1-Release-Notes#parameter-name-retention)
*/
