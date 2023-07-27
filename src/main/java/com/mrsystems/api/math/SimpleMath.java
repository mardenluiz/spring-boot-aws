package com.mrsystems.api.math;

import org.springframework.stereotype.Component;

@Component
public class SimpleMath {

	public Double sum(Double numberOne, Double numberTwo) {

		return numberOne + numberTwo;
	}

	public Double sub(Double numberOne, Double numberTwo) {

		return numberOne - numberTwo;
	}

	public Double mult(Double numberOne, Double numberTwo) {

		return numberOne * numberTwo;
	}
}
