package no.kristiania.pgr200.http.client;

import static org.assertj.core.api.Assertions.assertThat;


import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import no.kristiania.pgr200.http.client.Reader;

public class ReaderTest {
	
	@Test
	public void shouldReadFromReader() {
		String inputLine = "insert talk -title my talk -desc desc -topic topic";
		ArrayList<String> expectedArrayList = new ArrayList<>(Arrays.asList("insert", "talk", "-title", "my talk", "-desc", "desc", "-topic", "topic")); 
		Reader reader = new Reader();
		
		assertThat(reader.translateLineToArrayList(inputLine))
			.isEqualTo(expectedArrayList);
	}

}
