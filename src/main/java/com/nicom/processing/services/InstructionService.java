package com.nicom.processing.services;

import com.nicom.processing.dtos.InstructionRequest;
import com.nicom.processing.dtos.InstructionResponse;

public interface InstructionService {
	InstructionResponse processInstruction(InstructionRequest instructionRequest);

}
