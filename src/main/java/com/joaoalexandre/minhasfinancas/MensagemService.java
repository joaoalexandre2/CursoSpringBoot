package com.joaoalexandre.minhasfinancas;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


public class MensagemService {
	@Value("${application.name}")
	private String appName;

}
