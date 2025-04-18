package com.tahraoui.mathlizer.controller.data.request;

public record DerivativeRequest(String function, String variable, Integer order) {
	public DerivativeRequest {
		if (order == null) order = 1;
	}
}
