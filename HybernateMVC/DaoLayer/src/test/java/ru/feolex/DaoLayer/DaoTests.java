package ru.feolex.DaoLayer;

import org.h2.Driver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.feolex.DaoLayer.Dao.CatDao;
import ru.feolex.DaoLayer.Dao.OwnerDao;
import ru.feolex.DaoLayer.Dao.UserDao;
import ru.feolex.DaoLayer.Entities.Cat;
import ru.feolex.DaoLayer.Entities.Owner;
import ru.feolex.DaoLayer.Entities.UserExample;
import ru.feolex.DaoLayer.Exceptions.UpdateArgsException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class DaoTests {

    @BeforeAll
    public static void init() throws SQLException {
        DriverManager.registerDriver(new Driver());
        Connection conn = DriverManager.getConnection(
                "jdbc:h2:mem:testDB;INIT=CREATE SCHEMA IF NOT EXISTS dbo",
                "user",
                "123456");
        conn.commit();
    }

    @Test
    public void InitTest() {
    }

    @Nested
    public class DaoBaseTests {
        static UserDao sut;

        @BeforeEach
        public void init() {
            sut = new UserDao(new MockEntityManagerFactoryConfiguration().provide());
        }

        @Test
        public void SaveAndGetUserTest() {
            UserExample user = new UserExample();
            user.setName("Ivan");
            user.setComment("IVANOV");

            sut.save(user);

            Optional<UserExample> userFrombd = sut.get(1L);

            assertTrue(userFrombd.isPresent());
            assertEquals(user.getName(), userFrombd.get().getName());
            assertEquals(user.getComment(), userFrombd.get().getComment());
        }

        @Test
        public void GetAllUsersTest() {

            for (long i = 0L; i < 5; i++) {
                UserExample owner = new UserExample();
                owner.setName("Ivan" + i);

                sut.save(owner);
            }

            List<UserExample> owners = sut.getAll().stream().toList();
            for (Long i = 0L; i < 5; i++) {
                assertEquals("Ivan" + i.toString(), owners.get(Math.toIntExact(i)).getName());
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

            Optional<UserExample> fromBd = sut.get(1L);
            assertNotNull(fromBd.get().getId());
            sut.update(fromBd.get(), Arrays.stream(arr).toList());

            fromBd = sut.get(1L);
            assertEquals("Goga", fromBd.get().getName());
            assertEquals("Goganov", fromBd.get().getComment());
        }

        @Test
        public void DeleteUserTest() {

            UserExample user = new UserExample();
            user.setName("Ivan");
            user.setComment("IVANOV");

            sut.save(user);

            Optional<UserExample> fromBd = sut.get(1L);

            sut.delete(fromBd.get());

            assertTrue(sut.get(1L).isEmpty());
        }
    }

    @Nested
    public class OwnerDaoTests {
        static CatDao catDao;
        static OwnerDao ownerDao;

        @BeforeEach
        public void init() {
            catDao = new CatDao(new MockEntityManagerFactoryConfiguration().provide());
            ownerDao = new OwnerDao(new MockEntityManagerFactoryConfiguration().provide());
        }

        @Test
        public void OwnerAddCatTest() throws UpdateArgsException {
            Owner owner = new Owner();
            owner.setName("Ivan");
            owner.setBirthDate("01.01.1000");

            Cat miss = new Cat();
            miss.setName("Miss");
            miss.setOwner(owner);

            catDao.save(miss);

            ownerDao.save(owner);

            owner.setCats(List.of(new Cat[]{miss}));

            //Owner_name, Birthdate, Cat1Id(Long), Cat2Id(Long), ... CatNId(Long)
            List<String> ownerUpdParams = new ArrayList<>();
            ownerUpdParams.add(owner.getName());
            ownerUpdParams.add("02.02.2002");
            ownerUpdParams.add("1");
//
            ownerDao.update(owner, ownerUpdParams);

            Owner owner1 = ownerDao.get(1L).get();
            owner1.setCats(List.of(new Cat[]{miss}));

            Optional<Owner> ownerFromBd = ownerDao.get(1L);
            assertTrue(ownerFromBd.isPresent());
            assertTrue(ownerFromBd.get().getCats().stream().findAny().isPresent());
        }
    }

    @Nested
    public class CatDaoTests {
        static CatDao catDao;
        static OwnerDao ownerDao;

        @BeforeEach
        public void init() {
            catDao = new CatDao(new MockEntityManagerFactoryConfiguration().provide());
            ownerDao = new OwnerDao(new MockEntityManagerFactoryConfiguration().provide());
        }

        @Test
        public void UpdateCatTest() throws UpdateArgsException {
            Owner owner = new Owner();
            owner.setName("Ivan");

            ownerDao.save(owner);

            Cat miss = new Cat();
            miss.setName("Miss");
            miss.setBreed("EGYPT");

            catDao.save(miss);

            // Cat_name, Birthdate, Breed, Color, OwnerId(Long), CatFriend1Id(Long), CatFriend2id(Long), ... CatFriendNId(Long)
            List<String> missUpdateParams = new ArrayList<>();
            missUpdateParams.add(miss.getName());
            missUpdateParams.add("01.01.2023");
            missUpdateParams.add("RUSSIAN");
            missUpdateParams.add("1");
            missUpdateParams.add("1");

            Optional<Cat> missFromBd = catDao.get(1L);
            catDao.update(missFromBd.get(), missUpdateParams);

            missFromBd = catDao.get(1L);
            assertEquals("RUSSIAN", missFromBd.get().getBreed());
        }

        @Test
        public void CatFriendshipTest() throws UpdateArgsException {
            Owner owner = new Owner();
            owner.setName("Miss");

            Cat miss = new Cat();
            miss.setName("Miss");
            catDao.save(miss);

            Cat jo = new Cat();
            jo.setName("Jo");
            catDao.save(jo);

            ownerDao.save(owner);
            /// Owner_name, Birthdate, Cat1Id(Long), Cat2Id(Long), ... CatNId(Long)
            List<String> ownerUpdParams = new ArrayList<>();
            ownerUpdParams.add(owner.getName());
            ownerUpdParams.add("10.01.2002");
            ownerUpdParams.add("1");
            ownerUpdParams.add("2");

            Optional<Owner> ownerFromBd = ownerDao.get(1L);
            ownerDao.update(ownerFromBd.get(), ownerUpdParams);

            // Cat_name, Birthdate, Breed, Color, OwnerId(Long), CatFriend1Id(Long), CatFriend2id(Long), ... CatFriendNId(Long)
            List<String> missUpdateParams = new ArrayList<>();
            missUpdateParams.add(miss.getName());
            missUpdateParams.add("01.01.2023");
            missUpdateParams.add("RUSSIAN");
            missUpdateParams.add("RED");
            missUpdateParams.add("1");
            missUpdateParams.add("2");

            Optional<Cat> missFromBd = catDao.get(1L);
            catDao.update(missFromBd.get(), missUpdateParams);

            // Cat_name, Birthdate, Breed, Color, OwnerId(Long), CatFriend1Id(Long), CatFriend2id(Long), ... CatFriendNId(Long)
            List<String> joUpdateParams = new ArrayList<>();
            joUpdateParams.add(jo.getName());
            joUpdateParams.add("01.01.2000");
            joUpdateParams.add("EGYPT");
            joUpdateParams.add("RED");
            joUpdateParams.add("1");
            joUpdateParams.add("1");

            Optional<Cat> joFromBd = catDao.get(2L);
            catDao.update(joFromBd.get(), joUpdateParams);

            missFromBd = catDao.get(1L);
            joFromBd = catDao.get(2L);

            assertEquals(1, missFromBd.get().getFriendsCats().size());
            assertEquals(1, joFromBd.get().getFriendsCats().size());

            //Just aliases to short
            Optional<Cat> missFromJoFriends = joFromBd.get().getFriendsCats().stream().findFirst();
            Optional<Cat> joFromMissFriends = missFromBd.get().getFriendsCats().stream().findFirst();

            assertEquals(joFromBd.get().getName(), joFromMissFriends.get().getName());
            assertEquals(missFromBd.get().getName(), missFromJoFriends.get().getName());

            assertEquals(joFromBd, joFromMissFriends);
            assertEquals(missFromBd, missFromJoFriends);
        }

    }
}
