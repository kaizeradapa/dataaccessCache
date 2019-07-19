package com.dataaccessCache.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dataaccessCache.service.Service;



@RestController
public class Controler {

	@Autowired
	Service service;

	@GetMapping("/getCache/{id}")
	Object get(@PathVariable String key) {
		return service.get(key);

	}
	@PostMapping("/putCache/")
	void put(@PathVariable String key, @RequestBody Object value) {
		service.put(key, value);

	}
}