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
import playground.logic.Entities.UserEntity;
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
		this.playgroundService.cleanup();
		
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
		IntStream.range(0, numberOFElements) // int stream
				.mapToObj(value -> new ElementEntity(value+"",new Location(value,value),"animal #" + value,exirationDate,type,attributes,creatorPlayground,creatorEmail)) //  ElementTO stream using constructor reference
				.forEach(playgroundService::addNewElement);
	}

	// A
	@Test
	public void testGetAllElementsUsingPaginationWithDefaultSizeOfFirstPageSuccessfully() throws Exception {

		int DefaultSize = 10;
		String baseUrl =  "http://localhost:" + port ;
		String url = baseUrl + "/playground/elements/2019a.talin/talin@email.com/all";

		/*
		 * Given Server is up And the database contains 10 Elements
		 */
		
		setElementsDatabase(100);
				

		// when
		ElementTO[] actualElement = this.restTemplate.getForObject(url, ElementTO[].class);

		// then
		assertThat(actualElement).isNotNull().hasSize(DefaultSize);

	}

	// A
	@Test
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
	@Test
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
	@Test
	public void TestGetAllElementsUsingPaginationOfSecondPageSuccessfully() throws Exception {

		int size = 6;
		int page = 1;

		String baseUrl =  "http://localhost:" + port ;
		String url = baseUrl +  "/playground/elements/2019a.talin/talin@email.com/all" + "?size=" + size + "&page=" + page;

		/*
		 * Given Server is up And the database contains 20 Elements
		 */
		setElementsDatabase(20);

		// when
		ElementTO[] actualElement = this.restTemplate.getForObject(url, ElementTO[].class);

		// then
		assertThat(actualElement).isNotNull().hasSize(size);
	}

	// A
	@Test(expected=Exception.class)
	public void testGetAllElementsWithIinvalidPageSize() {
		// when

		String baseUrl =  "http://localhost:" + port ;
		String url = baseUrl + "/playground/elements/null/talin@email.com/all";
			
		/*
		 * Given Server is up And the database contains 20 Elements
		 */
		setElementsDatabase(20);

		
		//When I Get  /playground/elements/null/talin@email.com/all?size=-6&page=1
		this.restTemplate.getForObject(url + "?size={size}&page={page}", ElementTO[].class, -6, 1);
		
		
		//Then the response status is <> 2xx 
	}

	// A
	@Test
	public void TesTGetAllTheNearElementsUsingPaginationWithDefaultSizeOfFirstPageSuccessfully() throws Exception {
		int x = 10;
		int y = 10;
		int distance = 10;
		int DefaultSize = 10;

		String baseUrl =  "http://localhost:" + port ;
		String url = baseUrl + "/playground/elements/2019a.talin/Tali@email.com/near/" + x + "/" + y + "/" + distance;

		/*
		 * Given Server is up And the database contains 10 Elements
		 */
		setElementsDatabase(100);

		// when
		ElementTO[] actualElement = this.restTemplate.getForObject(url, ElementTO[].class);

		// how to check near Elements
		// Then the response status is 2xx and body contains 10 near Elements

		// then
		assertThat(actualElement).isNotNull().hasSize(DefaultSize);

	}

	// A
	@Test
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
		setElementsDatabase(100);

		// when
		ElementTO[] actualElement = this.restTemplate.getForObject(url, ElementTO[].class);

		// how to check near Elements
		// Then the response status is 2xx and body contains 3 near Elements

		// then
		assertThat(actualElement).isNotNull().hasSize(size);

	}

	// A
	@Test
	public void TestGetAllNearElementsUsingPaginationOfSecondPageSuccessfully() throws Exception {
		int x = 5;
		int y = 5;
		int distance = 10;
		int size = 5;
		int page = 1;

		String baseUrl =  "http://localhost:" + port ;
		String url = baseUrl + "/playground/elements/2019a.talin/Tali@email.com/near/" + x + "/" + y + "/" + distance + "?size="
				+ size + "&page=" + page;

		/*
		 * Given Server is up And the database contains 10 Elements
		 */
		setElementsDatabase(20);

		// when
		ElementTO[] actualElement = this.restTemplate.getForObject(url, ElementTO[].class);

		// how to check near Elements
		// Then the response status is 2xx and body contains 3 near Elements

		// then
		assertThat(actualElement).isNotNull().hasSize(size);

	}

	// A
	@Test
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
		
		setElementsDatabase(1);
		
		
		ElementTO[] actualElement = this.restTemplate.getForObject(url, ElementTO[].class);

		// then
		assertThat(actualElement).isNotNull().hasSize(0);
	}

	
	//A
	@Test(expected = Exception.class)
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
	@Test
	public void updateAnElementSuccessfully() throws Exception {
		 //Given Server is up
		
		String ID = "0";
		String playground = "2019a.talin";
		
		
		String baseUrl =  "http://localhost:" + port ;
		String url = baseUrl +"/playground/elements/2019a.talin/talin@email.com/"+ playground +"/"+ID;
		

		
		Map<String,Object> attributes = new HashMap<String, Object>();
		attributes.put("Play", "Woof");
		
		ElementEntity elementEntity = new ElementEntity();
		elementEntity.setCreationDate(null);
			
		
		this.playgroundService.addNewElement(elementEntity);
		
		ElementTO updatedElementTO = new ElementTO();
		updatedElementTO.setId(ID);
		updatedElementTO.setPlayground(playground);
		updatedElementTO.setLocation(new Location(10,10));
		updatedElementTO.setName("Rex");
		updatedElementTO.setType("Dog");
		updatedElementTO.setAttributes(attributes);
		updatedElementTO.setCreationDate(null);
		
		this.restTemplate.put(
				url,
				updatedElementTO);
		
		
		ElementEntity actualElement = this.playgroundService.getElement(ID, playground);
		
		
		ElementEntity expectedElement = new ElementEntity();
		expectedElement.setLocation(new Location(10,10));
		expectedElement.setName("Rex");
		expectedElement.setType("Dog");
		Map<String,Object> attributesForexpectedElement = new HashMap<String, Object>();
		attributesForexpectedElement.put("Play", "Woof");
		expectedElement.setAttributes(attributesForexpectedElement);
		expectedElement.setCreationDate(null);
		
		
		//String actualElementJson = this.jackson.writeValueAsString(actualElement);
		//String expectedMessageJson = this.jackson.writeValueAsString(expectedElement);
		
		
		assertThat(actualElement)
			.isNotNull()
			.isEqualTo(expectedElement);		
		
	}
	
	
	//A
	@Test(expected=Exception.class)
	public void testUpdateNonExistingElement() throws Exception{
		String baseUrl =  "http://localhost:" + port ;
		String url = baseUrl +"/playground/elements/2019a.talin/talin@email.com/2019a.talin/{ID}";
		String ID = "0";
		String playground = "2019a.talin";
	
		Map<String,Object> attributesForEntityInDataBase = new HashMap<String, Object>();
		//Given server is up 

		//When I Put 
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
	
	//I miss add new user to database !!!!!!!!!!!!!!!!!!!!!!!!!!
		@Test
		public void TestUpdateUserSuccessfully() throws Exception{
				String email = "talin@email.com";
				String playground ="2019a.Talin";
				String username = "user2";
				String avatar="https://goo.gl/images/WqDt96";
				String role="Player";
				long points = 0;
				//Given server is up 
				//And the database contains And the user database contains {"email": ”talin@email.com”,"playground": "2019a.Talin",
				//														"username": "user2","avatar": "https://goo.gl/images/WqDt96",
				//														"role": "Player","points": 0,"code":"1234"}
				UserEntity userEnti = new UserEntity(email, playground, username, avatar, role, points);
				//this.PlaygroundService.(
				//		this.jackson.readValue("{\"message\":\"hello\", \"x\":1.0, \"y\":1.0}", MessageEntity.class));
						
		}
		
		//I
		@Test
		public void TestElementCreatedSuccessfully() throws Exception{
			//Given Server is up
			
			String baseUrl =  "http://localhost:" + port ;
			String url = baseUrl +"/playground/elements/{userPlayground}/{email}";
			
			String name = "cat";
			double x = 1.0;
			double y = 1.0;
			String type = "animal";
			
			ElementTO newElement = new ElementTO();
			newElement.setName(name);
			newElement.setLocation(new Location(x, y));
			newElement.setType(type);
			
			//Given server is up
			
			//When I POST  http://localhost:8083/playground/elements/2019a.talin/talin@email.com
			//And with body 
			//  {	
			//“name”: “cat”,
			//“type”:”animal”
			//“location”:{“x”:0.0,”y”:0.0},
			//
			//}
			//with headers:
			// 	Accept: application/json
			//	Content-Type:  application/json	
			this.restTemplate.postForObject(
					url, 
					newElement, 
					ElementTO.class);
			//Then the response status is 2xx
			// and the database contains for playground+id:“2019a.talin0”
			
			ElementEntity elementEntityExist = this.playgroundService.getElement("0", "2019a.talin");
			assertThat(elementEntityExist)
				.extracting("name", "x", "y","type")
				.containsExactly(name, x, y, type);

		}
		
}
