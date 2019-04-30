package no.kristiania.pgr200.http.server;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import no.kristiania.pgr200.core.http.HttpPath;
import no.kristiania.pgr200.core.http.HttpQuery;


public class HttpPathTest {

	@Test
	public void shouldSeparatePathAndQuery() {
		HttpPath path = new HttpPath("/api?status=200");
		
		assertThat(path.getPath()).isEqualTo("/api");
		assertThat(path.getQuery().getFullQuery()).isEqualTo("status=200");
		assertThat(path.getFullPath()).isEqualTo("/api?status=200");
		assertThat(path.getQuery().getParameter("status")).isEqualTo("200");
	}

	@Test
	public void shouldReturnQueryNullWhenNoQuery() {
		HttpPath path = new HttpPath("/myfile");
		assertThat(path.getFullPath()).isEqualTo("/myfile");
		assertThat(path.getQuery()).isNull();
	}

    @Test
    public void shouldReadParameter() {
    	HttpPath path = new HttpPath("/api?status=200&location=www.hello.com");
    	assertThat(path.getQuery().getParameter("status")).isEqualTo("200");
    	assertThat(path.getQuery().getParameter("location")).isEqualTo("www.hello.com");
    }
    
  @Test
  public void shouldUrlDecodeParameters() {
      String query = "status=307&location=http%3A%2F%2Fwww.kristiania.no";
      HttpPath path = new HttpPath("/api?" + query);

      assertThat(path.getQuery().getParameter("location")).isEqualTo("http://www.kristiania.no");
      assertThat(path.getQuery().toString()).isEqualTo(query);
  }
  
  @Test
  public void shouldFindMoreParameters() {
      HttpPath path = new HttpPath("/api?status=404&body=Hello");
      HttpQuery query = path.getQuery();
      
      assertThat(query.toString()).isEqualTo("status=404&body=Hello");
      assertThat(query.getParameter("status")).isEqualTo("404");
      assertThat(query.getParameter("body")).isEqualTo("Hello");
  }
  
  @Test
  public void shouldSetParameters() {
      HttpPath path = new HttpPath("/api");
      path.setParameter("status", "200");
      assertThat(path.getQuery().toString()).isEqualTo("status=200");
      assertThat(path.toString()).isEqualTo("/api?status=200");
  }



}
