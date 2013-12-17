package com.wind.quicknote.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wind.quicknote.model.NoteNode;
import com.wind.quicknote.model.NoteUser;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml", 
								  "/dataAccessContext.xml"})
public class NoteServiceTest {
	
	@Autowired
	@Qualifier("noteService")
	private NoteService service;
	
	@BeforeClass
	public static void beforeClass() {
		/*ClassPathXmlApplicationContext ctx = newClassPathXmlApplicationContext("applicationContext_test.xml" );
        service = (NoteService)ctx.getBean("noteService" );*/
	}
	
	@AfterClass
	public static void afterClass() {
		System.out.println("afterClass ends.");
	}

	@Test
	public void testFindAllTopics() {
		List<NoteNode> items = service.findAllTopics();
		assertTrue(items.size() > 0);
	}

	@Test
	public void testFindChildTopics() {
		List<NoteNode> items = service.findChildTopics(3L);
		assertEquals(2 , items.size());
	}

	@Test
	public void testFindRootTopicByUser() {
		NoteNode node = service.findRootTopicByUser(1L);
		assertEquals("ROOT" , node.getName());
	}

	@Test
	public void testFindAllUsers() {
		List<NoteUser> userlist = service.findAllUsers();
		assertTrue(userlist.size() > 0);
	}

	@Test
	public void testUpdateTopicText() {
		long id = 3L;
		String oldValue = "hi there 3";
		String newValue = "hi there 3++";
		assertEquals(oldValue, service.findTopic(id).getText());
		
		service.updateTopicText(id, newValue);
		assertEquals(newValue, service.findTopic(id).getText());
		
		service.updateTopicText(id, oldValue);
		assertEquals(oldValue, service.findTopic(id).getText());
	}

	@Test
	public void testUpdateTopicName() {
		
		long id = 3L;
		String oldValue = "third topic";
		String newValue = "new third topic";
		
		NoteNode note = service.findTopic(id);
		assertEquals(oldValue, note.getName());
		
		service.updateTopicName(id, newValue);
		note = service.findTopic(id);
		assertEquals(newValue, note.getName());
		
		service.updateTopicName(id, oldValue);
		note = service.findTopic(id);
		assertEquals(oldValue, note.getName());
	}

	@Test
	public void testAddAndRemoveTopic() {
		
		NoteNode topic = service.addTopic(1, "Added", "content", "URL");
		Long id = topic.getId();
		assertNotNull(id);
		
		service.removeTopic(id);
		topic = service.findTopic(id);
		assertNull(topic);
	}

	@Test
	public void testChangeParentId() {
		
		long id = 8L;
		long oldParentId = 3L;
		long newParentId = 4L;
		
		NoteNode note = service.findTopic(id);
		assertEquals(oldParentId, note.getParent().getId());
		
		service.changeParentId(id, newParentId);
		note = service.findTopic(id);
		assertEquals(newParentId, note.getParent().getId());
		
		service.changeParentId(id, oldParentId);
		note = service.findTopic(id);
		assertEquals(oldParentId, note.getParent().getId());
		
	}

	@Test
	public void testAddAndRemoveUser() {
		
		NoteUser user = service.addUser("Kevin Abbort", "send@mail.com", "abcdefg");
		assertNotNull(user.getId());
		
		service.removeUser(user);
		user = service.findUserByName("Kevin Abbort");
		assertNull(user);
	}
	
	@Test
	public void testUpdateUser() {

		String userName = "David";
		String oldValue = "meat lover";
		String newValue = "laywer";
		
		NoteUser user = service.findUserByName(userName);
		assertEquals(oldValue, user.getDesc());
		
		user.setDesc(newValue);
		service.updateUser(user);
		assertEquals(newValue, service.findUserByName(userName).getDesc());
		
		user.setDesc(oldValue);
		service.updateUser(user);
		assertEquals(oldValue, service.findUserByName(userName).getDesc());
	}
	
}
