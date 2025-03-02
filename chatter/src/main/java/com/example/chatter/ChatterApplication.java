package com.example.chatter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.chatter.entity.Messages;
import com.example.chatter.service.MessageService;
import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class ChatterApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatterApplication.class, args).getBean(ChatterApplication.class).exe();
	}
	
	private final MessageService messageService;


	public void exe() {
		System.out.println("全件検索 : メッセージ");
		for(Messages row : messageService.findAllMessage()) {
			System.out.println(row);
		}
		
	}

}