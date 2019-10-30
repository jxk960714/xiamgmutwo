package com.jxk.oto;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Hello {
	
	@RequestMapping(value="/hello" ,method=RequestMethod.GET)
	public String Hello() {
		/* 返回hello */
		return "Hello SpringBoot";
	}

}
