package com.tahraoui.mathlizer.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Component
public class PythonRunner {

	@Value("${python.scripts.path}")
	private String scriptsPath;

	String executeScript(String scriptName, String... args) {
		var pb = new ProcessBuilder("python", scriptsPath.formatted(scriptName));
		pb.directory(new File("."));
		pb.command().addAll(List.of(args));

		try {
			var process = pb.start();
			var reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			var output = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) output.append(line);

			var errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			var errorOutput = new StringBuilder();
			while ((line = errorReader.readLine()) != null) errorOutput.append(line);

			if (process.waitFor() != 0) throw new RuntimeException("Python script error: " + errorOutput);
			return output.toString();
		}
		catch (InterruptedException | IOException e) {
			throw new RuntimeException("Error executing Python script: " + e.getMessage(), e);
		}
	}
}
