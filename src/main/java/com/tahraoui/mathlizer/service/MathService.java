package com.tahraoui.mathlizer.service;

import com.tahraoui.mathlizer.controller.data.request.DerivativeRequest;
import com.tahraoui.mathlizer.controller.data.request.LimitRequest;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

@Service
public class MathService {

	private final PythonRunner pythonRunner;

	public MathService(PythonRunner pythonScriptExecutor) {
		this.pythonRunner = pythonScriptExecutor;

	}

	public String calculateDerivative(DerivativeRequest request, boolean isLatex) {
		var isLatexArg = isLatex ? "True" : "False";
		return pythonRunner.executeScript("derive", request.function(), request.variable(), isLatexArg);
	}
	public String calculateLimit(LimitRequest request, boolean isLatex) {
		var isLatexArg = isLatex ? "True" : "False";
		return pythonRunner.executeScript("limit", request.function(), request.point().replace("inf","oo"), isLatexArg);
	}

	public byte[] graphFunction(String function, String start, String end) {
		var outputFileName = "graph-" + UUID.randomUUID();

		pythonRunner.executeScript("graph", function, start, end, outputFileName);

		try {
			var outputFilePath = new File("multimedia/temp/%s.svg".formatted(outputFileName)).toPath();
			var bytes = Files.readAllBytes(outputFilePath);
			Files.delete(outputFilePath);
			return bytes;
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
