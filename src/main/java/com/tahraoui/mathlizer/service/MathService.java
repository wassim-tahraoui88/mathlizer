package com.tahraoui.mathlizer.service;

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

	public String calculateDerivative(String function, String variable) {
		return pythonRunner.executeScript("derive", function, variable);
	}
	public String calculateLimit(String function, String point) {
		return pythonRunner.executeScript("limit", function, point);
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
