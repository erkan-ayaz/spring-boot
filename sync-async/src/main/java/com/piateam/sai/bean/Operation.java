package com.piateam.sai.bean;

import java.util.List;

public class Operation {
	private char			operand;
	private List<Double>	parameters;

	public Operation() {
		super();
	}

	public Operation(char operand, List<Double> paramters) {
		super();
		this.operand = operand;
		this.parameters = paramters;
	}

	public char getOperand() {
		return operand;
	}

	public void setOperand(char operand) {
		this.operand = operand;
	}

	public List<Double> getParameters() {
		return parameters;
	}

	public void setParameters(List<Double> paramters) {
		this.parameters = paramters;
	}
}
