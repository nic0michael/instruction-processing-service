package com.nicom.processing.controllers;


import java.util.logging.Level;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nicom.processing.dtos.InstructionRequest;
import com.nicom.processing.dtos.InstructionResponse;
import com.nicom.processing.modules.InstructionModule;
import com.nicom.processing.utils.RequestResponseMaker;

import lombok.extern.java.Log;

@RestController
@Log
public class InstructionController {


	@Value("${spring.profiles.active}")
	private String activeProfile;

	@Value("${version.number}")
	String versionNumber;
	
	@Autowired
	InstructionModule instructionModule;
	

	
	@PostMapping(value = "/process/instruction", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE })
	public InstructionResponse processInstruction(InstructionRequest instructionRequest) {
		return instructionModule.processInstruction(instructionRequest);
	}
	
	/**
	 * http://localhost:8080//instruction-processing/process/test
	 * 
	 * @param instructionRequest
	 * @return
	 */
	@GetMapping("/process/test")
	public InstructionResponse testInstruction() {
		InstructionRequest instructionRequest=RequestResponseMaker.makeTestInstructionRequest();
		return instructionModule.processInstruction(instructionRequest);
	}

}
