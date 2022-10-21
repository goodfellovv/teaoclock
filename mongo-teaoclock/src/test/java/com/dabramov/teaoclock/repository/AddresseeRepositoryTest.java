package com.dabramov.teaoclock.repository;

import com.dabramov.teaoclock.entity.Addressee;
import com.dabramov.teaoclock.entity.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@DataMongoTest
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AddresseeRepositoryTest {
    @Autowired
    private AddresseeRepository addresseeRepository;
    @Autowired
    private MessageRepository messageRepository;

    @Test
    public void injectedComponentsAreNotNull() {
        assertNotNull(addresseeRepository);
        assertNotNull(messageRepository);
    }

    @Test
    public void testSaveAndFindOne() {
        Addressee addressee = new Addressee();
        addressee.setEmail("testemail");
        addressee.setMessage(messageRepository.save(new Message()));
        addresseeRepository.save(addressee);

        assertTrue(addresseeRepository.findById(addressee.getId()).isPresent());
    }

    @Test
    public void testSaveAndFindAll() {
        List<Addressee> listToSave = new ArrayList<>();
        Addressee addressee1 = new Addressee();
        addressee1.setEmail("testemail1");
        addressee1.setMessage(messageRepository.save(new Message()));
        listToSave.add(addressee1);

        Addressee addressee2 = new Addressee();
        addressee2.setEmail("testemail2");
        addressee2.setMessage(messageRepository.save(new Message()));
        listToSave.add(addressee2);

        addresseeRepository.saveAll(listToSave);

        List<Addressee> savedList = addresseeRepository.findAll();
        assertNotNull(savedList);
        assertEquals(2, savedList.size());
    }

    @Test(expected = DuplicateKeyException.class)
    public void testSaveDuplicates() {
        Addressee addressee1 = new Addressee();
        addressee1.setEmail("testemail");
        addressee1.setMessage(messageRepository.save(new Message()));
        addresseeRepository.insert(addressee1);

        Addressee addressee2 = new Addressee();
        addressee2.setEmail("testemail");
        addressee2.setMessage(messageRepository.save(new Message()));
        addresseeRepository.insert(addressee2);
    }

    @Test
    public void testSaveAndFindAllByIds() {
        List<Addressee> listToSave = new ArrayList<>();
        Addressee addressee1 = new Addressee();
        addressee1.setEmail("testemail1");
        addressee1.setMessage(messageRepository.save(new Message()));
        listToSave.add(addressee1);

        Addressee addressee2 = new Addressee();
        addressee2.setEmail("testemail2");
        addressee2.setMessage(messageRepository.save(new Message()));
        listToSave.add(addressee2);

        addresseeRepository.saveAll(listToSave);

        List<Addressee> savedList = (List<Addressee>) addresseeRepository.findAllById(Arrays.asList(addressee1.getId(), addressee2.getId()));
        assertNotNull(savedList);
        assertEquals(2, savedList.size());
    }

    @Test
    public void testDeleteOne() {
        Addressee addressee = new Addressee();
        addressee.setEmail("testemail");
        addressee.setMessage(messageRepository.save(new Message()));
        addresseeRepository.save(addressee);
        addresseeRepository.deleteById(addressee.getId());

        assertEquals(0, addresseeRepository.findAll().size());
    }
}
