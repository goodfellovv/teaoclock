package com.dabramov.teaoclock.repository;

import com.dabramov.teaoclock.entity.Addressee;
import com.dabramov.teaoclock.entity.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@DataJpaTest
@RunWith(SpringRunner.class)
public class AddresseeRepositoryTest {
    @Autowired
    private AddresseeRepository repository;

    @Test
    public void injectedComponentsAreNotNull() {
        assertNotNull(repository);
    }

    @Test
    public void testSaveAndFindOne() {
        Addressee addressee = new Addressee();
        addressee.setEmail("testemail");
        addressee.setMessage(new Message());
        repository.save(addressee);

        Addressee referenceById = repository.getReferenceById(addressee.getId());
        assertNotNull(referenceById);
    }

    @Test
    public void testSaveAndFindAll() {
        List<Addressee> listToSave = new ArrayList<>();
        Addressee addressee1 = new Addressee();
        addressee1.setEmail("testemail1");
        addressee1.setMessage(new Message());
        listToSave.add(addressee1);

        Addressee addressee2 = new Addressee();
        addressee2.setEmail("testemail2");
        addressee2.setMessage(new Message());
        listToSave.add(addressee2);

        repository.saveAll(listToSave);

        List<Addressee> savedList = repository.findAll();
        assertNotNull(savedList);
        assertEquals(2, savedList.size());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testSaveDuplicates() {
        Addressee addressee1 = new Addressee();
        addressee1.setEmail("testemail");
        addressee1.setMessage(new Message());
        repository.saveAndFlush(addressee1);

        Addressee addressee2 = new Addressee();
        addressee2.setEmail("testemail");
        addressee2.setMessage(new Message());
        repository.saveAndFlush(addressee2);
    }

    @Test
    public void testSaveAndFindAllByIds() {
        List<Addressee> listToSave = new ArrayList<>();
        Addressee addressee1 = new Addressee();
        addressee1.setEmail("testemail1");
        addressee1.setMessage(new Message());
        listToSave.add(addressee1);

        Addressee addressee2 = new Addressee();
        addressee2.setEmail("testemail2");
        addressee2.setMessage(new Message());
        listToSave.add(addressee2);

        repository.saveAll(listToSave);

        List<Addressee> savedList = repository.findAllById(Arrays.asList(addressee1.getId(), addressee2.getId()));
        assertNotNull(savedList);
        assertEquals(2, savedList.size());
    }

    @Test
    public void testDeleteOne() {
        Addressee addressee = new Addressee();
        addressee.setEmail("testemail");
        addressee.setMessage(new Message());
        repository.save(addressee);
        repository.deleteById(addressee.getId());

        assertEquals(0, repository.findAll().size());
    }
}
