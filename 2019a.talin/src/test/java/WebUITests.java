package java;

	import static org.assertj.core.api.Assertions.assertThat;

	import java.util.stream.IntStream;
	import java.util.stream.Stream;

	import javax.annotation.PostConstruct;

	import org.junit.After;
	import org.junit.Before;
	import org.junit.Test;
	import org.junit.runner.RunWith;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.boot.test.context.SpringBootTest;
	import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
	import org.springframework.boot.web.server.LocalServerPort;
	import org.springframework.test.context.junit4.SpringRunner;
	import org.springframework.web.client.RestTemplate;

import playground.logic.Services.PlaygroundService;

	@RunWith(SpringRunner.class)
	@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
	public class WebUITests {
		@Autowired
		private PlaygroundService playgroundService;
		
		private RestTemplate restTemplate; 
		
		@LocalServerPort
		private int port;
		
		private String url;
		
		@PostConstruct
		public void init() {
			this.restTemplate = new RestTemplate();
			
			url = "http://localhost:" + port + "/messages";
			System.err.println(this.url);
		}
		
		@Before
		public void setup() {
			
		}
		
		@After
		public void teardown() {
			// cleanup database
		//	this.messageService.cleanup();
		}
}
