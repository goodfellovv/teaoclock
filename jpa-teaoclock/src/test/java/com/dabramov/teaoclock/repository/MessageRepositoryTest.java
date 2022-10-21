package com.dabramov.teaoclock.repository;

import com.dabramov.teaoclock.entity.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@DataJpaTest
@RunWith(SpringRunner.class)
public class MessageRepositoryTest {
    @Autowired
    private MessageRepository repository;

    @Test
    public void injectedComponentsAreNotNull() {
        assertNotNull(repository);
    }

    @Test
    public void testSaveAndFindOne() {
        Message message = new Message();
        message.setContent("Test content");
        message.setSubject("Test subject");
        repository.save(message);

        Message referenceById = repository.getReferenceById(message.getId());
        assertNotNull(referenceById);
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

        repository.saveAll(listToSave);

        List<Message> savedList = repository.findAll();
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

        repository.saveAll(listToSave);

        List<Message> savedList = repository.findAllById(Arrays.asList(message1.getId(), message2.getId()));
        assertNotNull(savedList);
        assertEquals(2, savedList.size());
    }

    @Test
    public void testDeleteOne() {
        Message message = new Message();
        message.setContent("Test content");
        message.setSubject("Test subject");
        repository.save(message);
        repository.deleteById(message.getId());

        assertEquals(0, repository.findAll().size());
    }
}
