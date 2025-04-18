package com.tahraoui.mathlizer.service;

import com.tahraoui.mathlizer.controller.data.request.DerivativeRequest;
import com.tahraoui.mathlizer.controller.data.request.LimitRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

@Service
public class MathService {

	@Value("${multimedia.path.temp}")
	private String multimediaPathTemp;

	private final PythonRunner pythonRunner;

	public MathService(PythonRunner pythonScriptExecutor) {
		this.pythonRunner = pythonScriptExecutor;

	}

	public String calculateDerivative(DerivativeRequest request, boolean isLatex) {
		return pythonRunner.executeScript("derive", request.function(), request.variable(), String.valueOf(request.order()), String.valueOf(isLatex ? '1' : '0'));
	}
	public String evaluateLimit(LimitRequest request, boolean isLatex) {
		return pythonRunner.executeScript("limit", request.function(), request.point().replace("inf","oo"), String.valueOf(isLatex ? '1' : '0'));
	}

	public byte[] graphFunction(String function, String start, String end) {
		var outputFileName = "graph-" + UUID.randomUUID();

		pythonRunner.executeScript("graph", function, start, end, outputFileName, multimediaPathTemp);

		try {
			var outputFilePath = new File("%s/%s.svg".formatted(multimediaPathTemp, outputFileName)).toPath();
			var bytes = Files.readAllBytes(outputFilePath);
			Files.delete(outputFilePath);
			return bytes;
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
