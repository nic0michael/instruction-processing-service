package com.nicom.processing.dtos;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@XmlRootElement(name = "InstructionResponse")
public class InstructionResponse {

	@Getter
	@Setter
	private String activeProfile;

	@Getter
	@Setter
	private String versionNumber;

	@Getter
	@Setter
	private List<String> outPutLines;

	@Getter
	@Setter
	private long processingTimeInMs;

	@Getter
	@Setter
	private Date responseTime;

	@Getter
	@Setter
	private String responseCode;

	@Getter
	@Setter
	private String responseMessage;

}
