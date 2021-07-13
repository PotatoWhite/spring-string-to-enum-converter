/*
 * TestController.java 2021. 07. 12
 *
 * Copyright 2021 Naver Cloud Corp. All rights Reserved.
 * Naver Business Platform PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package me.potato.springstringtoenumconverter.expose;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.potato.springstringtoenumconverter.enums.IntegerCodeEnum;
import me.potato.springstringtoenumconverter.enums.StringCodeEnum;

/**
 * @author dongju.paek
 */
@RestController
public class TestController {

	@GetMapping("/string")
	public StringCodeEnum getString(@RequestParam StringCodeEnum code) {
		return code;
	}

	@GetMapping("/integer")
	public IntegerCodeEnum getInteger(@RequestParam IntegerCodeEnum code) {
		return code;
	}
}