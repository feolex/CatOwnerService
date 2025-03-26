package ru.feolex.DaoLayer;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import ru.feolex.DaoLayer.Dao.CatColorRepository;
import ru.feolex.DaoLayer.Dao.CatRepository;
import ru.feolex.DaoLayer.Dao.OwnerRepository;
import ru.feolex.DaoLayer.Dao.UserExampleRepository;
import ru.feolex.DaoLayer.Entities.Cat;
import ru.feolex.DaoLayer.Entities.Owner;
import ru.feolex.DaoLayer.Entities.UserExample;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@EntityScan("ru.feolex.Daolayer.Entities")
@SpringBootApplication
@EnableTransactionManagement
@ComponentScan("ru.feolex.DaoLayer")
@EnableJpaRepositories("ru.feolex.DaoLayer.Dao")
@PropertySource(value = {"classpath:application.properties"})
@Disabled //TODO - turn on, commit and push, test on github
public class NewDaoTests {

    @Test
    public void init(){}

    @Nested
    @SpringBootTest
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    class UserRepoTest {

        @Autowired
        private UserExampleRepository sut;
        @Test
        public void CustomRepoTest(){
            UserExample userExample1 = UserExample.builder().name("Ivan").comment("Ivanov").build();

            UserExample userExample2 = UserExample.builder().name("Ivan").comment("SurnameNotFound").build();

            sut.save(userExample1);
            sut.save(userExample2);

            assertTrue(sut.summirizeAllComments());
        }

        @Test
        @Transactional
        public void SaveAndGetUserTest() {
            UserExample user = new UserExample();
            user.setName("Ivan");
            user.setComment("IVANOV");

            sut.save(user);

            Optional<UserExample> userFrombd = sut.findByName(user.getName());

            assertTrue(userFrombd.isPresent());
            Assertions.assertEquals(user.getName(), userFrombd.get().getName());
            Assertions.assertEquals(user.getComment(), userFrombd.get().getComment());
        }
        @Test
        public void GetAllUsersTest() {

            for (long i = 0L; i < 5; i++) {
                UserExample owner = new UserExample();
                owner.setName("Ivan" + i);

                sut.save(owner);
            }

            List<UserExample> owners = sut.findAll();

            for (Long i = 0L; i < 5; i++) {
                Assertions.assertEquals("Ivan" + i.toString(), owners.get(Math.toIntExact(i)).getName());
            }
        }
        @Test
        public void UpdateUserTest() {
            UserExample insertedUser = new UserExample();

            insertedUser.setName("Ivan");
            insertedUser.setComment("Ivanov");

            sut.save(insertedUser);

            String[] arr = new String[2];
            arr[0] = "Goga";
            arr[1] = "Goganov";

            Optional<UserExample> fromBd = sut.findById(1L);
            assertNotNull(fromBd.get().getId());

            sut.updateNameAndCommentById(arr[0], arr[1], fromBd.get().getId());

            fromBd = sut.findById(1L);
            Assertions.assertEquals("Goga", fromBd.get().getName());
            Assertions.assertEquals("Goganov", fromBd.get().getComment());
        }

        @Test
        public void DeleteUserTest() {

            UserExample user = new UserExample();
            user.setName("Ivan");
            user.setComment("IVANOV");

            sut.save(user);

            Optional<UserExample> fromBd = sut.findById(1L);

            sut.delete(fromBd.get());

            assertTrue(sut.findById(1L).isEmpty());
        }
    }

    @Nested
    @SpringBootTest
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    class OwnerRepoTest{

        @Autowired
        private OwnerRepository ownerRepo;

        @Autowired
        private CatRepository catRepo;
        @Test
        @Transactional
        public void OwnerAddCatTest() {
            Owner owner = new Owner();
            owner.setName("Ivan");
            owner.setBirthDate("01.01.1000");
            owner.setCats(new ArrayList<>());

            Cat miss = new Cat();
            miss.setName("Miss");
            miss.setOwner(owner);

            catRepo.save(miss);
            ownerRepo.save(owner);

            owner.setBirthDate("02.02.2002");
            owner.getCats().add(miss);

            ownerRepo.save(owner);

            Optional<Owner> ownerFromBd = ownerRepo.findById(1L);
            assertTrue(ownerFromBd.isPresent());
            assertTrue(ownerFromBd.get().getCats().stream().findAny().isPresent());
        }
    }

    @Nested
    @SpringBootTest
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    class CatRepoTest{
        @Autowired
        private OwnerRepository ownerRepo;

        @Autowired
        private CatRepository catRepo;

        @Test
        @Transactional
        public void GetLastAddedCatTest(){
            Cat miss = new Cat();
            miss.setName("Miss");

            catRepo.save(miss);

            assertTrue(catRepo.findById(1L).isPresent());
            assertTrue(catRepo.findTopByOrderByIdDesc().isPresent());
            assertEquals(catRepo.findById(1L).get(), catRepo.findTopByOrderByIdDesc().get());
        }

        @Test
        @Transactional
        public void AddIdInEntityAfterSavingInDbTest(){
            Cat miss = new Cat();
            miss.setName("Miss");

            Cat missFromBd = catRepo.save(miss);
            assertEquals(1L, missFromBd.getId());
        }

        @Test
        @Transactional
        public void UpdateCatTest(){
            Owner owner = new Owner();
            owner.setName("Ivan");

            ownerRepo.save(owner);

            Cat miss = new Cat();
            miss.setName("Miss");
            miss.setBreed("EGYPT");
            miss.setFriendsCats(new HashSet<>());

            catRepo.save(miss);

            miss.setBirthDate("01.01.2023");
            miss.setBreed("RUSSIAN");
            miss.setOwner(owner);

            catRepo.save(miss);

            Optional<Cat> missFromBd = catRepo.findById(1L);

            Assertions.assertEquals("RUSSIAN", missFromBd.get().getBreed());
        }

        @Test
        @Transactional
        public void CatFriendshipTest() {
            Owner owner = new Owner();
            owner.setName("Miss");

            Cat miss = new Cat();
            miss.setName("Miss");
            catRepo.save(miss);

            Cat jo = new Cat();
            jo.setName("Jo");
            catRepo.save(jo);

            ownerRepo.save(owner);

            owner.setBirthDate("10.01.2002");
            owner.getCats().add(miss);
            owner.getCats().add(jo);

            ownerRepo.save(owner);

            miss.setBirthDate("01.01.2023");
            miss.setBreed("RUSSIAN");
            miss.getFriendsCats().add(jo);

            catRepo.save(miss);

            jo.setBirthDate("01.01.2000");
            jo.setBreed("EGYPT");
            jo.setOwner(owner);
            jo.getFriendsCats().add(miss);

            catRepo.save(jo);

            Optional<Cat> missFromBd = catRepo.findById(1L);
            Optional<Cat> joFromBd = catRepo.findById(2L);

            Assertions.assertEquals(1, missFromBd.get().getFriendsCats().size());
            Assertions.assertEquals(1, joFromBd.get().getFriendsCats().size());

            //Just aliases to short
            Optional<Cat> missFromJoFriends = joFromBd.get().getFriendsCats().stream().findFirst();
            Optional<Cat> joFromMissFriends = missFromBd.get().getFriendsCats().stream().findFirst();

            Assertions.assertEquals(joFromBd.get().getName(), joFromMissFriends.get().getName());
            Assertions.assertEquals(missFromBd.get().getName(), missFromJoFriends.get().getName());

            Assertions.assertEquals(joFromBd, joFromMissFriends);
            Assertions.assertEquals(missFromBd, missFromJoFriends);
        }
    }

}

