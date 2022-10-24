package com.dabramov.teaoclock.repository;

import com.dabramov.teaoclock.entity.Message;
import com.dabramov.teaoclock.repository.MessageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@DataMongoTest
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MessageRepositoryTest {
    @Autowired
    private MessageRepository messageRepository;

    @Test
    public void injectedComponentsAreNotNull() {
        assertNotNull(messageRepository);
    }

    @Test
    public void testSaveAndFindOne() {
        Message message = new Message();
        message.setContent("Test content");
        message.setSubject("Test subject");
        messageRepository.save(message);

        assertTrue(messageRepository.findById(message.getId()).isPresent());
    }

    @Test
    public void testSaveAndFindAll() {
        List<Message> listToSave = new ArrayList<>();
        Message message1 = new Message();
        message1.setContent("Test content 1");
        message1.setSubject("Test subject 1");
        listToSave.add(message1);

        Message message2 = new Message();
        message2.setContent("Test content 2");
        message2.setSubject("Test subject 2");
        listToSave.add(message2);

        messageRepository.saveAll(listToSave);

        List<Message> savedList = messageRepository.findAll();
        assertNotNull(savedList);
        assertEquals(2, savedList.size());
    }

    @Test
    public void testSaveAndFindAllByIds() {
        List<Message> listToSave = new ArrayList<>();
        Message message1 = new Message();
        message1.setContent("Test content 1");
        message1.setSubject("Test subject 1");
        listToSave.add(message1);

        Message message2 = new Message();
        message2.setContent("Test content 2");
        message2.setSubject("Test subject 2");
        listToSave.add(message2);

        messageRepository.saveAll(listToSave);

        List<Message> savedList = (List<Message>) messageRepository.findAllById(Arrays.asList(message1.getId(), message2.getId()));
        assertNotNull(savedList);
        assertEquals(2, savedList.size());
    }

    @Test
    public void testDeleteOne() {
        Message message = new Message();
        message.setContent("Test content");
        message.setSubject("Test subject");
        messageRepository.save(message);
        messageRepository.deleteById(message.getId());

        assertEquals(0, messageRepository.findAll().size());
    }
}
