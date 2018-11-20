package java;

	import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;
	import java.util.stream.Stream;

	import javax.annotation.PostConstruct;
import javax.lang.model.element.Element;

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

import playground.layout.ElementTO;
import playground.logic.Location;
import playground.logic.Entities.ElementEntity;
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
		
		
		//A
		//@Test
		public void testGetAllMessagesUsingPaginationWithDefaultSizeOfFirstPageSuccessfully () throws Exception{
			
			int DefaultSize = 10;
			
			String url = "/playground/elements/2019a.talin/talin@email.com/all";
			
			/*Given Server is up
			And the database contains 10 Elements
			 */
	
			// when
			ElementTO[] actualElement = this.restTemplate.getForObject(
					url, 
					ElementTO[].class);

			// then
			assertThat(actualElement)
				.isNotNull()
				.hasSize(DefaultSize);
				 	
		}
		
		//A
		//@Test
		public void TestGetSomeElementsUsingPaginationSuccessfully()  throws Exception{
			
			int size = 3;
			
			String url = "/playground/elements/2019a.talin/talin@email.com/all" + "?size="+size;
			
			/*Given Server is up
			And the database contains 10 Elements
			 */
	
			// when
			ElementTO[] actualElement = this.restTemplate.getForObject(
					url, 
					ElementTO[].class);

			// then
			assertThat(actualElement)
				.isNotNull()
				.hasSize(size);
				 	
		}
		
		//A
		//@Test
		public void TestGetNoElementsUsingPaginationOf100PageSuccessfully()  throws Exception{
			
			int size = 3;
			int page = 100;
			
			String url = "/playground/elements/2019a.talin/talin@email.com/all" + "?size="+size +
					"page="+page;
			
			/*Given Server is up
			And the database contains 10 Elements
			 */
	
			// when
			ElementTO[] actualElement = this.restTemplate.getForObject(
					url, 
					ElementTO[].class);

			// then
			assertThat(actualElement)
				.isNotNull()
				.hasSize(0);	
		}
		
		//A
		//@Test
		public void TestGetAllElementsUsingPaginationOfSecondPageSuccessfully()  throws Exception{
			
			int size = 6;
			int page = 1;
			
			String url = "/playground/elements/2019a.talin/talin@email.com/all" + "?size="+size +
					"page="+page;
			
			/*Given Server is up
			And the database contains 10 Elements
			 */
	
			// when
			ElementTO[] actualElement = this.restTemplate.getForObject(
					url, 
					ElementTO[].class);

			// then
			assertThat(actualElement)
				.isNotNull()
				.hasSize(size);	
		}
		
		//A
		//@Test(expected=Exception.class)
		public void testGetAllMessagesWithIinvalidPageSize () {
			
			String url = " /playground/elements/2019a.talin/null/all";
			
			// when
			this.restTemplate.getForObject(
					url, 
					ElementTO[].class);
		}
		
		//A
		//@Test(expected=Exception.class)
		public void  TestGetAllTheElementsWithInvalidPlayground () {
			
			String url = "  /playground/elements/null/talin@email.com/all";
			
			// when
			this.restTemplate.getForObject(
					url,
					ElementTO[].class);
		}
		
		//A
		//@Test(expected=Exception.class)
		public void testGetAllElementsWithIinvalidPageSize () {
			// when
			
			String url = "/playground/elements/null/talin@email.com/all";
			
			this.restTemplate.getForObject(
					url + "?size={size}&page={page}", 
					ElementTO[].class,
					-6, 1);
		}
		
		
		//A
		//@Test
		public void TesTGetAllTheNearElementsUsingPaginationWithDefaultSizeOfFirstPageSuccessfully()
		{
			int x = 5;
			int y = 5;
			int distance = 10;
			
			String url = "/playground/elements/2019a.talin/Tali@email.com/near/" + x + "/" + y + "/" + distance;
			
			/*Given Server is up
			And the database contains 10 Elements
			 */
	
			// when
			ElementTO[] actualElement = this.restTemplate.getForObject(
					url, 
					ElementTO[].class);
			
			
			assertThat(actualElement)
			.isNotNull()
			.extracting("location")
			.extracting("x")
			.contains(5);
			
		}
		
		
		
		
		
}
