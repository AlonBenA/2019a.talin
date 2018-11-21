package playground.layout;

import static org.assertj.core.api.Assertions.assertThat;

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
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
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
		// this.messageService.cleanup();
	}

	// A
	// @Test
	public void testGetAllMessagesUsingPaginationWithDefaultSizeOfFirstPageSuccessfully() throws Exception {

		int DefaultSize = 10;

		String url = "/playground/elements/2019a.talin/talin@email.com/all";

		/*
		 * Given Server is up And the database contains 10 Elements
		 */

		// when
		ElementTO[] actualElement = this.restTemplate.getForObject(url, ElementTO[].class);

		// then
		assertThat(actualElement).isNotNull().hasSize(DefaultSize);

	}

	// A
	// @Test
	public void TestGetSomeElementsUsingPaginationSuccessfully() throws Exception {

		int size = 3;

		String url = "/playground/elements/2019a.talin/talin@email.com/all" + "?size=" + size;

		/*
		 * Given Server is up And the database contains 10 Elements
		 */

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

		String url = "/playground/elements/2019a.talin/talin@email.com/all" + "?size=" + size + "&page=" + page;

		/*
		 * Given Server is up And the database contains 10 Elements
		 */

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

		String url = "/playground/elements/2019a.talin/talin@email.com/all" + "?size=" + size + "&page=" + page;

		/*
		 * Given Server is up And the database contains 10 Elements
		 */

		// when
		ElementTO[] actualElement = this.restTemplate.getForObject(url, ElementTO[].class);

		// then
		assertThat(actualElement).isNotNull().hasSize(size);
	}

	// A
	// @Test(expected=Exception.class)
	public void testGetAllMessagesWithIinvalidPageSize() {

		String url = " /playground/elements/2019a.talin/null/all";

		// when
		this.restTemplate.getForObject(url, ElementTO[].class);
	}

	// A
	// @Test(expected=Exception.class)
	public void TestGetAllTheElementsWithInvalidPlayground() {

		String url = "  /playground/elements/null/talin@email.com/all";

		// when
		this.restTemplate.getForObject(url, ElementTO[].class);
	}

	// A
	// @Test(expected=Exception.class)
	public void testGetAllElementsWithIinvalidPageSize() {
		// when

		String url = "/playground/elements/null/talin@email.com/all";

		this.restTemplate.getForObject(url + "?size={size}&page={page}", ElementTO[].class, -6, 1);
	}

	// A
	// @Test
	public void TesTGetAllTheNearElementsUsingPaginationWithDefaultSizeOfFirstPageSuccessfully() throws Exception {
		int x = 5;
		int y = 5;
		int distance = 10;
		int DefaultSize = 10;

		String url = "/playground/elements/2019a.talin/Tali@email.com/near/" + x + "/" + y + "/" + distance;

		/*
		 * Given Server is up And the database contains 10 Elements
		 */

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

		String url = "/playground/elements/2019a.talin/Tali@email.com/near/" + x + "/" + y + "/" + distance + "?size="
				+ size;

		/*
		 * Given Server is up And the database contains 10 Elements
		 */

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

		String url = "/playground/elements/2019a.talin/Tali@email.com/near/" + x + "/" + y + "/" + distance + "?size="
				+ size + "&page=" + page;

		/*
		 * Given Server is up And the database contains 10 Elements
		 */

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

		String url = "/playground/elements/2019a.talin/Tali@email.com/near/" + x + "/" + y + "/" + distance + "?size="
				+ size + "&page=" + page;

		// when
		ElementTO[] actualElement = this.restTemplate.getForObject(url, ElementTO[].class);

		// then
		assertThat(actualElement).isNotNull().hasSize(0);
	}

	// A
	// @Test(expected=Exception.class)
	public void TestGetAllNearElementsWithInvalidEmail() {
		// when
		int x = 5;
		int y = 4;
		int distance = 10;

		String url = "/playground/elements/2019a.talin/null/near/" + x + "/" + y + "/" + distance;

		this.restTemplate.getForObject(url, ElementTO[].class);
	}

	// A
	// @Test(expected=Exception.class)
	public void TestGetAllNearElementsWithInvalidPlayground() {
		// when
		int x = 5;
		int y = 4;
		int distance = 10;

		String url = "/playground/elements/null/Tali@email.com/near/" + x + "/" + y + "/" + distance;

		this.restTemplate.getForObject(url, ElementTO[].class);
	}

	@Test(expected = Exception.class)
	public void TestGetAllTheNearElementsWithInvalidPageSize() {
		// when
		// when
		int x = 5;
		int y = 4;
		int distance = 10;

		String url = "/playground/elements/null/Tali@email.com/near/" + x + "/" + y + "/" + distance;

		this.restTemplate.getForObject(url + "?size={size}&page={page}", ElementTO[].class, -6, 1);

	}

}
