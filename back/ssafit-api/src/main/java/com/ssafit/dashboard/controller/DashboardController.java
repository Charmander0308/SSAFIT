package com.ssafit.dashboard.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafit.dashboard.dto.DashboardResponseDto;
import com.ssafit.dashboard.service.DashboardService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/dashboard")
@Tag(name = "대시보드 API", description = "대시보드 조회 API") 
public class DashboardController {

	private final DashboardService dashboardService;

	public DashboardController(DashboardService dashboardService) {
		this.dashboardService = dashboardService;
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "대시보드 조회", description = "회원의 활동정보를 조회한다.")
	public ResponseEntity<DashboardResponseDto> getData(
			@Parameter(description = "조회할 회원의 id 값", example = "1") @PathVariable("id") Long id){
		return ResponseEntity.ok(dashboardService.getDashboardData(id));
	}
	
}
