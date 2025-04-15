package com.tahraoui.mathlizer.controller.api.v1;

import com.tahraoui.mathlizer.controller.data.request.DerivativeRequest;
import com.tahraoui.mathlizer.controller.data.request.GraphRequest;
import com.tahraoui.mathlizer.controller.data.request.LimitRequest;
import com.tahraoui.mathlizer.controller.data.response.ResponseBuilder;
import com.tahraoui.mathlizer.service.MathService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/math")
public class MathController {

	//region Dependencies
	private final MathService mathService;
	//endregion

	public MathController(MathService mathService) {
		this.mathService = mathService;

	}

	//region Endpoints
	@PostMapping("/derive")
	public ResponseEntity<String> derive(@RequestBody DerivativeRequest request, @RequestParam(required = false, name = "is-latex", defaultValue = "false") Boolean isLatex) {
		var derivative = mathService.calculateDerivative(request, isLatex);
		return ResponseBuilder.accept(HttpStatus.OK, derivative);
	}

	@PostMapping("/limit")
	public ResponseEntity<String> limit(@RequestBody LimitRequest request, @RequestParam(required = false, name = "is-latex", defaultValue = "false") Boolean isLatex) {
		var limit = mathService.calculateLimit(request, isLatex).replace("oo","∞");
		return ResponseBuilder.accept(HttpStatus.OK, limit);
	}

	@PostMapping("/graph")
	public ResponseEntity<ByteArrayResource> graph(@RequestBody GraphRequest request, HttpServletResponse response) {
		var graph = new ByteArrayResource(mathService.graphFunction(request.function(), request.start(), request.end()));
		response.setHeader(HttpHeaders.CONTENT_TYPE,"image/svg+xml");
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"graph.svg\"");
		return ResponseBuilder.accept(HttpStatus.OK, graph);
	}
	//endregion
}
