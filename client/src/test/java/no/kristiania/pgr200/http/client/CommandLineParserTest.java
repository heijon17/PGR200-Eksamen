package no.kristiania.pgr200.http.client;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import no.kristiania.pgr200.core.database.Conference;
import no.kristiania.pgr200.core.database.Talk;


public class CommandLineParserTest {

//	INVALID COMMANDS
	@Test
	public void ShouldGetInvalidCommandWithTooFewArgs() {
		String[] args = {"insert", "talk"};
		CommandLineParser parser = new CommandLineParser(args);
		
		assertThat(parser.getParsedPath())
			.isEqualTo("-invalid:invalid command, write 'help' to see valid commands");
	}
	
	@Test
	public void ShouldGetInvalidCommandWithTooManyArgs() {
		String[] args = {"insert", "talk", "insert", "talk", "insert", "talk", "insert", "talk", "insert", "talk", "insert", "talk", "insert", "talk", "insert", "talk"};
		CommandLineParser parser = new CommandLineParser(args);
		
		assertThat(parser.getParsedPath())
			.isEqualTo("-invalid:invalid command, write 'help' to see valid commands");
	}
	
	@Test
	public void ShouldGetInvalidCommandWithOddArgs() {
		String[] args = {"insert", "talk", "-title", "my talk", "-description"};
		CommandLineParser parser = new CommandLineParser(args);
		
		assertThat(parser.getParsedPath())
			.isEqualTo("-invalid:invalid command, write 'help' to see valid commands");
	}
	
	@Test
	public void ShouldGetInvalidCommandWithNotKnownArg() {
		String[] args = {"invalidCommand", "talk", "-title", "my talk", "-description"};
		CommandLineParser parser = new CommandLineParser(args);
		
		assertThat(parser.getParsedPath())
			.isEqualTo("-invalid:invalid command, write 'help' to see valid commands");
	}
	
	@Test
	public void ShouldGetInvalidCommandWithNoArg() {
		String[] args = {};
		CommandLineParser parser = new CommandLineParser(args);
		
		assertThat(parser.getParsedPath())
			.isEqualTo("-invalid:invalid command, write 'help' to see valid commands");
	}
	
	@Test
	public void ShouldGetInvalidCommandWithNotTwoArgListAll() {
		String[] args = {"listall", "talk", "title"};
		CommandLineParser parser = new CommandLineParser(args);
		
		assertThat(parser.getParsedPath())
			.isEqualTo("-invalid:invalid command, write 'help' to see valid commands");
	}
	
	@Test
	public void ShouldGetInvalidCommandWithNotFourArgGetOrDelete() {
		String[] args = {"get", "talk", "-id"};
		CommandLineParser parser = new CommandLineParser(args);
		
		assertThat(parser.getParsedPath())
			.isEqualTo("-invalid:invalid command, write 'help' to see valid commands");
	}
	
	
//	TALK
	@Test
	public void ShouldParseListAllTalk() {
		String[] args = {"listall", "talk"};
		CommandLineParser parser = new CommandLineParser(args);
		
		assertThat(parser.getParsedPath())
			.isEqualTo("/api?method=listall&table=talk");
	}
	
	@Test
	public void ShouldParseAddTalk() {
		String[] args = {"insert", "talk", "-title", "my talk", "-description", "desc", "-topic", "topic"};
		CommandLineParser parser = new CommandLineParser(args);
		
		assertThat(parser.getParsedPath())
			.isEqualTo("/api?method=insert&table=talk&title=my+talk&description=desc&topic=topic");
	}
	
	@Test
	public void ShouldParseGetTalk() {
		Talk talk = new Talk("TestTalk", "TestTalkDesc", "TestTalkTopic");
		String talkId = talk.getId().toString();
		String[] args = {"get", "talk", "-id", talkId};
		CommandLineParser parser = new CommandLineParser(args);
		
		assertThat(parser.getParsedPath())
			.isEqualTo("/api?method=get&table=talk&id=" + talkId);
	}
	
	@Test
	public void ShouldParseDeleteTalk() {
		Talk talk = new Talk("TestTalk", "TestTalkDesc", "TestTalkTopic");
		String talkId = talk.getId().toString();
		String[] args = {"delete", "talk", "-id", talkId};
		CommandLineParser parser = new CommandLineParser(args);
		
		assertThat(parser.getParsedPath())
			.isEqualTo("/api?method=delete&table=talk&id=" + talkId);
	}
	
	@Test
	public void ShouldParseUpdateTalk() {
		Talk talk = new Talk("TestTalk", "TestTalkDesc", "TestTalkTopic");
		String talkId = talk.getId().toString();
		String[] args = {"update", "talk", "-id", talkId, "-title", "new title", "-description", "new desc", "-topic", "new topic"};
		CommandLineParser parser = new CommandLineParser(args);
		
		assertThat(parser.getParsedPath())
			.isEqualTo("/api?method=update&table=talk&id=" + talkId + 
					"&title=new+title&description=new+desc&topic=new+topic");
	}
	
//	CONFERENCE
	@Test
	public void ShouldParseListAllConference() {
		String[] args = {"listall", "conference"};
		CommandLineParser parser = new CommandLineParser(args);
		
		assertThat(parser.getParsedPath())
			.isEqualTo("/api?method=listall&table=conference");
	}
	
	@Test
	public void ShouldParseAddConference() {
		String[] args = {"insert", "conference", "-title", "my conference"};
		CommandLineParser parser = new CommandLineParser(args);
		
		assertThat(parser.getParsedPath())
			.isEqualTo("/api?method=insert&table=conference&title=my+conference");
	}
	
	@Test
	public void ShouldParseGetConference() {
		Conference conf = new Conference("TestConference");
		String confId = conf.getId().toString();
		String[] args = {"get", "conference", "-id", confId};
		CommandLineParser parser = new CommandLineParser(args);
		
		assertThat(parser.getParsedPath())
			.isEqualTo("/api?method=get&table=conference&id=" + confId);
	}
	
	@Test
	public void ShouldParseDeleteConference() {
		Conference conf = new Conference("TestConference");
		String confId = conf.getId().toString();
		String[] args = {"delete", "conference", "-id", confId};
		CommandLineParser parser = new CommandLineParser(args);
		
		assertThat(parser.getParsedPath())
			.isEqualTo("/api?method=delete&table=conference&id=" + confId);
	}
	
	@Test
	public void ShouldParseUpdateConference() {
		Conference conf = new Conference("TestConference");
		String confId = conf.getId().toString();
		String[] args = {"update", "conference", "-id", confId, "-name", "new conference name"};
		CommandLineParser parser = new CommandLineParser(args);
		
		assertThat(parser.getParsedPath())
			.isEqualTo("/api?method=update&table=conference&id=" + confId +
					"&name=new+conference+name");
	}
	
//	DATE
	@Test
	public void ShouldParseAddDate() {
		String[] args = {"insert", "date", "-date", "16/08/2018"};
		CommandLineParser parser = new CommandLineParser(args);
		
		assertThat(parser.getParsedPath())
			.isEqualTo("/api?method=insert&table=date&date=2018-08-16");
	}
	
//	TIMESLOT
	@Test
	public void ShouldParseAddTimeslot() {
		String[] args = {"insert", "timeslot", "-time_from", "10:00", "-time_to", "11:00"};
		CommandLineParser parser = new CommandLineParser(args);
		
		assertThat(parser.getParsedPath())
			.isEqualTo("/api?method=insert&table=timeslot&time_from=10%3A00&time_to=11%3A00");
	}
	
	@Test
    public void ShouldParseAddTalkOnlyMethodAndTable() {
        String[] args = {"insert", "talk", "-title", "my talk", "-description", "desc", "-topic", "topic"};
        CommandLineParser parser = new CommandLineParser(args);
        
        assertThat(parser.getPathWithOnlyMethodAndTable())
            .isEqualTo("/api?method=insert&table=talk");
    }
	
}
