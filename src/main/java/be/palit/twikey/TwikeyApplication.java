package be.palit.twikey;

import be.palit.twikey.service.ProcessPaymentService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TwikeyApplication {

	public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(TwikeyApplication.class, args);

        ProcessPaymentService processPaymentService = context.getBean(ProcessPaymentService.class);
        processPaymentService.startPaymentProcess(10, 2025);
	}

}
