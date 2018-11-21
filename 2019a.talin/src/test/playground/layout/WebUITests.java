package playground.layout;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import playground.logic.Location;
import playground.logic.Entities.ElementEntity;
import playground.logic.Services.PlaygroundService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class WebUITests {
	
	@Autowired
	private PlaygroundService playgroundService;

	private RestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String Url;
	
	
	private ObjectMapper jackson;

	@PostConstruct
	public void init() {
		this.restTemplate = new RestTemplate();

		Url = "http://localhost:" + port;
		System.err.println(this.Url);
	}

	@Before
	public void setup() {

	}

	@After
	public void teardown() {
		// cleanup database
		// this.messageService.cleanup();
		
		
	}
	
	
	
	private void setElementsDatabase(int numberOFElements) {

		Date exirationDate = null;
		String type = "animal";
		Map<String,Object> attributes = new HashMap<>();
		String creatorPlayground = "2019a.talin";
		String creatorEmail = "2019a.Talin@Gmail.com";
				
		//location,value,exirationDate,type,attributes,creatorPlayground,creatorEmail
		//add specific attribute
		Random rand = new Random();
		if (rand.nextInt(100) < 20) { // 20% of the elements
		    attributes.put("Eat", "meat");
		}
				
		//location,value,exirationDate,type,attributes,creatorPlayground,creatorEmail
		IntStream.range(0, 100) // int stream
				.mapToObj(value -> new ElementEntity(value+"",new Location(value,value),"animal #" + value,exirationDate,type,attributes,creatorPlayground,creatorEmail)) //  ElementTO stream using constructor reference
				.forEach(playgroundService::addNewElement);
	}

	// A
	// @Test
	public void testGetAllMessagesUsingPaginationWithDefaultSizeOfFirstPageSuccessfully() throws Exception {

		int DefaultSize = 10;
		String baseUrl =  "http://localhost:" + port ;
		String url = baseUrl + "/playground/elements/2019a.talin/talin@email.com/all";

		/*
		 * Given Server is up And the database contains 10 Elements
		 */
		
		setElementsDatabase(DefaultSize);
				

		// when
		ElementTO[] actualElement = this.restTemplate.getForObject(url, ElementTO[].class);

		// then
		assertThat(actualElement).isNotNull().hasSize(DefaultSize);

	}

	// A
	// @Test
	public void TestGetSomeElementsUsingPaginationSuccessfully() throws Exception {

		int size = 3;
		String baseUrl =  "http://localhost:" + port ;
		String url = baseUrl + "/playground/elements/2019a.talin/talin@email.com/all" + "?size=" + size;

		/*
		 * Given Server is up And the database contains 10 Elements
		 */
		setElementsDatabase(10);

		// when
		ElementTO[] actualElement = this.restTemplate.getForObject(url, ElementTO[].class);

		// then
		assertThat(actualElement).isNotNull().hasSize(size);

	}

	// A
	// @Test
	public void TestGetNoElementsUsingPaginationOf100PageSuccessfully() throws Exception {

		int size = 3;
		int page = 100;

		String baseUrl =  "http://localhost:" + port ;
		String url = baseUrl +  "/playground/elements/2019a.talin/talin@email.com/all" + "?size=" + size + "&page=" + page;

		/*
		 * Given Server is up And the database contains 10 Elements
		 */
		setElementsDatabase(10);

		// when
		ElementTO[] actualElement = this.restTemplate.getForObject(url, ElementTO[].class);

		// then
		assertThat(actualElement).isNotNull().hasSize(0);
	}

	// A
	// @Test
	public void TestGetAllElementsUsingPaginationOfSecondPageSuccessfully() throws Exception {

		int size = 6;
		int page = 1;

		String baseUrl =  "http://localhost:" + port ;
		String url = baseUrl +  "/playground/elements/2019a.talin/talin@email.com/all" + "?size=" + size + "&page=" + page;

		/*
		 * Given Server is up And the database contains 10 Elements
		 */
		setElementsDatabase(10);

		// when
		ElementTO[] actualElement = this.restTemplate.getForObject(url, ElementTO[].class);

		// then
		assertThat(actualElement).isNotNull().hasSize(size);
	}

	// A
	// @Test(expected=Exception.class)
	public void testGetAllElementsWithIinvalidPageSize() {
		// when

		String baseUrl =  "http://localhost:" + port ;
		String url = baseUrl + "/playground/elements/null/talin@email.com/all";

		this.restTemplate.getForObject(url + "?size={size}&page={page}", ElementTO[].class, -6, 1);
	}

	// A
	// @Test
	public void TesTGetAllTheNearElementsUsingPaginationWithDefaultSizeOfFirstPageSuccessfully() throws Exception {
		int x = 5;
		int y = 5;
		int distance = 10;
		int DefaultSize = 10;

		String baseUrl =  "http://localhost:" + port ;
		String url = baseUrl + "/playground/elements/2019a.talin/Tali@email.com/near/" + x + "/" + y + "/" + distance;

		/*
		 * Given Server is up And the database contains 10 Elements
		 */
		setElementsDatabase(10);

		// when
		ElementTO[] actualElement = this.restTemplate.getForObject(url, ElementTO[].class);

		// how to check near Elements
		// Then the response status is 2xx and body contains 10 near Elements

		// then
		assertThat(actualElement).isNotNull().hasSize(DefaultSize);

	}

	// A
	// @Test
	public void TestGetSomeOfTheNearElementsUsingPaginationSuccessfully() throws Exception {
		int x = 5;
		int y = 5;
		int distance = 10;
		int size = 3;

		String baseUrl =  "http://localhost:" + port ;
		String url = baseUrl + "/playground/elements/2019a.talin/Tali@email.com/near/" + x + "/" + y + "/" + distance + "?size="
				+ size;

		/*
		 * Given Server is up And the database contains 10 Elements
		 */
		setElementsDatabase(10);

		// when
		ElementTO[] actualElement = this.restTemplate.getForObject(url, ElementTO[].class);

		// how to check near Elements
		// Then the response status is 2xx and body contains 3 near Elements

		// then
		assertThat(actualElement).isNotNull().hasSize(size);

	}

	// A
	// @Test
	public void TestGetAllNearElementsUsingPaginationOfSecondPageSuccessfully() throws Exception {
		int x = 5;
		int y = 5;
		int distance = 10;
		int size = 6;
		int page = 1;

		String baseUrl =  "http://localhost:" + port ;
		String url = baseUrl + "/playground/elements/2019a.talin/Tali@email.com/near/" + x + "/" + y + "/" + distance + "?size="
				+ size + "&page=" + page;

		/*
		 * Given Server is up And the database contains 10 Elements
		 */
		setElementsDatabase(10);

		// when
		ElementTO[] actualElement = this.restTemplate.getForObject(url, ElementTO[].class);

		// how to check near Elements
		// Then the response status is 2xx and body contains 3 near Elements

		// then
		assertThat(actualElement).isNotNull().hasSize(size);

	}

	// A
	// @Test
	public void TestGetNoNearElementsUsingPaginationOf100PageSuccessfully() throws Exception {
		int x = 5;
		int y = 5;
		int distance = 10;
		int size = 3;
		int page = 100;

		String baseUrl =  "http://localhost:" + port ;
		String url = baseUrl + "/playground/elements/2019a.talin/Tali@email.com/near/" + x + "/" + y + "/" + distance + "?size="
				+ size + "&page=" + page;

		/*
		 * Given Server is up And the database contains 10 Elements
		 */
		
		setElementsDatabase(10);
		
		
		ElementTO[] actualElement = this.restTemplate.getForObject(url, ElementTO[].class);

		// then
		assertThat(actualElement).isNotNull().hasSize(0);
	}

	
	//A
	//@Test(expected = Exception.class)
	public void TestGetAllTheNearElementsWithInvalidPageSize() throws Exception {
		// when
		int x = 5;
		int y = 4;
		int distance = 10;
		String baseUrl =  "http://localhost:" + port ;

		String url = baseUrl + "/playground/elements/null/Tali@email.com/near/" + x + "/" + y + "/" + distance;

		this.restTemplate.getForObject(url + "?size={size}&page={page}", ElementTO[].class, -6, 1);
	}
	
	//A
	//@Test
	public void updateAnElementSuccessfully() throws Exception {
		 //Given Server is up
		String baseUrl =  "http://localhost:" + port ;
		String url = baseUrl +"/playground/elements/2019a.talin/talin@email.com/2019a.talin/{ID}";
		
		String ID = "0";
		Map<String,Object> attributesForEntityInDataBase = new HashMap<String, Object>();
		
		ElementEntity elementEntity = new ElementEntity();
		
		this.playgroundService.addNewElement(elementEntity);
		
		ElementTO updatedElementTO = new ElementTO();
		updatedElementTO.setId(ID);
		updatedElementTO.setLocation(new Location(10,10));
		updatedElementTO.setName("Rex");
		updatedElementTO.setType("Dog");
		updatedElementTO.setAttributes(attributesForEntityInDataBase);
		updatedElementTO.setCreationDate(null);
		
		this.restTemplate.put(
				url, 
				updatedElementTO, 
				ID);
		
		
		ElementEntity actualElement = this.playgroundService.getElement("0", "2019a.talin");
		
		
		ElementEntity expectedElement = new ElementEntity();
		expectedElement.setLocation(new Location(10,10));
		expectedElement.setName("Rex");
		expectedElement.setType("Dog");
		Map<String,Object> attributesForexpectedElement = new HashMap<String, Object>();
		attributesForexpectedElement.put("Play", "Woof");
		expectedElement.setAttributes(attributesForexpectedElement);
		expectedElement.setCreationDate(null);
		

		String actualElementJson = this.jackson.writeValueAsString(actualElement);
		String expectedMessageJson = this.jackson.writeValueAsString(expectedElement);

		
		assertThat(actualElementJson)
			.isEqualTo(expectedMessageJson);		
		
	}
	
	
	//A
	@Test(expected=Exception.class)
	public void testUpdateNonExistingElement() throws Exception{
		String baseUrl =  "http://localhost:" + port ;
		String url = baseUrl +"/playground/elements/2019a.talin/talin@email.com/2019a.talin/{ID}";
		String ID = "0";
		Map<String,Object> attributesForEntityInDataBase = new HashMap<String, Object>();
		
		//Given server is up 
		ElementTO updatedElementTO = new ElementTO();
		updatedElementTO.setId(ID);
		updatedElementTO.setLocation(new Location(10,10));
		updatedElementTO.setName("Rex");
		updatedElementTO.setType("Dog");
		updatedElementTO.setAttributes(attributesForEntityInDataBase);
		updatedElementTO.setCreationDate(null);
		
		this.restTemplate.put(
				url, 
				updatedElementTO, 
				ID);
		
		// Then the response status <> 2xx 
	}
	

}
