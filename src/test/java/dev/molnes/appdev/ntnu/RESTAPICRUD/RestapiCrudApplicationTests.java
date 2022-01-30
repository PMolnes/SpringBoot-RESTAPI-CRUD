package dev.molnes.appdev.ntnu.RESTAPICRUD;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class RestapiCrudApplicationTests {

	@Autowired
	MockMvc mvc;

	@Autowired
	ObjectMapper objectMapper;

	@Test
	void contextLoads() {
	}

	@Test
	void checkBookCount() throws Exception {
		mvc.perform(get("/books/count"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("3")));
	}

	@Test
	void getNonExistingBook() throws Exception {
		mvc.perform(get("/books/883"))
				.andExpect(status().isNotFound());
	}
	
	@Test
	void getBookWithCorrectYearAndTitle() throws Exception {
		String jsonResponse = mvc.perform(get("/books/1"))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		Book book = objectMapper.readValue(jsonResponse, Book.class);

		assertThat(book.getTitle()).isEqualTo("12 Rules For Life");
		assertThat(book.getId()).isEqualTo(1);
	}

	/**
	 * Tries to delete a non-existing book.
	 * @throws Exception
	 */
	@Test
	void deleteNonExistingBook() throws Exception {
		mvc.perform(delete("/883"))
				.andExpect(status().isNotFound());
	}

	/**
	 * Several checks for the delete and add function.
	 * @throws Exception
	 */
	@Test
	void checkDeleteAndAddFunction() throws Exception {
		checkBookCount();
		getBookWithCorrectYearAndTitle();
		mvc.perform(delete("/books/1"))
				.andExpect(status().isOk());
		mvc.perform(get("/books/1"))
				.andExpect(status().isNotFound());
		mvc.perform(get("/books/count"))
				.andExpect(content().string(containsString("2")));

		String jsonString = objectMapper.writeValueAsString(new Book(10, "48 Rules Of Power", 1998, 480));
		mvc.perform(post("/books")
				.content(jsonString)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());

		String jsonResponse = mvc.perform(get("/books/10"))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		Book book = objectMapper.readValue(jsonResponse, Book.class);
		assertThat(book.getTitle()).isEqualTo("48 Rules Of Power");
		assertThat(book.getId()).isEqualTo(10);
	}
}
