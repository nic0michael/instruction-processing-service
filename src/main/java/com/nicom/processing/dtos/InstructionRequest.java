package com.nicom.processing.dtos;


import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@XmlRootElement(name = "InstructionRequest")
public class InstructionRequest {
	
	@Getter
	@Setter
	private String prosessId;

	@Getter
	@Setter
	private String processName;
	
	@Getter
	@Setter
	private Map<String,String> ioMap;

	@Getter
	@Setter
	private List<String> ioVariables;

	@Getter
	@Setter
	private String aesEncryptionKey;
	
	@Getter
	@Setter
	private String aesSalt;

	@Getter
	@Setter
	private List<String> availableBiosSelections;

	@Getter
	@Setter
	private List<String> instructionScripts;
}
