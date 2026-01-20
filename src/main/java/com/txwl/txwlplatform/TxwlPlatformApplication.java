package com.txwl.txwlplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.sql.init.SqlInitializationAutoConfiguration.class
})
public class TxwlPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(TxwlPlatformApplication.class, args);
	}

}
