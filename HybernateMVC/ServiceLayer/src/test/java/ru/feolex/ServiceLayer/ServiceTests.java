package ru.feolex.ServiceLayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.feolex.DaoLayer.Entities.Cat;
import ru.feolex.DaoLayer.Entities.CatColor;
import ru.feolex.DaoLayer.Entities.Owner;
import ru.feolex.OperationClasses.OperationEnum;
import ru.feolex.OperationClasses.OperationResult;
import ru.feolex.ServiceLayer.DaoMocks.MockCatDao;
import ru.feolex.ServiceLayer.DaoMocks.MockOwnerDao;
import ru.feolex.ServiceLayer.Enums.Color;
import ru.feolex.ServiceLayer.Models.CatModel;
import ru.feolex.ServiceLayer.Models.OwnerModel;
import ru.feolex.ServiceLayer.Services.CatService;
import ru.feolex.ServiceLayer.Services.CatServiceInterface;
import ru.feolex.ServiceLayer.Services.OwnerService;
import ru.feolex.ServiceLayer.Services.OwnerServiceInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceTests {

    @Nested
    public class CatModelTests {

        @Test
        public void CatModelConstructFromCatTest() {
            Cat cat = new Cat();
            cat.setId(1L);
            cat.setName("Vano");
            cat.setBirthDate("01.01.2004");
            cat.setBreed("DefaultBreed");
            cat.setColor(new CatColor(1L, "WHITE"));
            cat.setOwner(null);
            cat.setFriendsCats(null);

            CatModel catModel = new CatModel(cat);

            assertEquals(cat.getId(), catModel.getId());
            assertEquals(cat.getName(), catModel.getName());
            assertEquals(cat.getBirthDate(), catModel.getBirthDate());
            assertEquals(cat.getBreed(), catModel.getBreed());
            assertEquals(cat.getColor().getColorName(), catModel.getColor().name());

            assertNull(catModel.getOwner());
            assertNotNull(catModel.getFriendsCatsList());
        }

        @Test
        public void CatModelConstructFromCatWithFriendsTest1() {
            Cat miss = new Cat();
            miss.setId(1L);
            miss.setName("Miss");
            miss.setBirthDate("01.01.2004");
            miss.setBreed("Breed");
            miss.setColor(new CatColor(2L, "GREY"));
            miss.setOwner(null);

            Cat vano = new Cat();
            vano.setId(2L);
            vano.setName("Vano");
            vano.setBirthDate("01.01.2004");
            vano.setBreed("DefaultBreed");
            vano.setColor(new CatColor(1L, "WHITE"));
            vano.setOwner(null);

            Set<Cat> missFriends = new TreeSet<>();
            missFriends.add(vano);
            miss.setFriendsCats(missFriends);

            Set<Cat> vanoFriends = new TreeSet<>();
            vanoFriends.add(miss);
            vano.setFriendsCats(vanoFriends);

            CatModel vanoModel = new CatModel(vano);
            CatModel missModel = new CatModel(miss);

            assertTrue(vanoModel.getFriendsCatsList().stream().findFirst().isPresent());
            assertTrue(missModel.getFriendsCatsList().stream().findFirst().isPresent());

            CatModel vanoAsMissFriendId = missModel.getFriendsCatsList().stream().findFirst().get();
            CatModel missAsVanoFriendId = vanoModel.getFriendsCatsList().stream().findFirst().get();

            assertEquals(vanoModel, vanoAsMissFriendId);
            assertEquals(missModel, missAsVanoFriendId);
        }

        @Test
        public void CatModelFriendshipWithItselfTest() {

            Set<Cat> catFriends = new TreeSet<>();

            Long i = 1L;

            Cat cat = new Cat();
            cat.setId(i);
            cat.setName("Cat%d".formatted(i));
            cat.setBirthDate("%d.%d.00%d".formatted(i, i, i));
            cat.setBreed("Breed");
            cat.setColor(new CatColor(1L, "WHITE"));
            cat.setOwner(null);

            catFriends.add(cat);
            cat.setFriendsCats(catFriends);

            CatModel catModel = new CatModel(cat);

            assertNotNull(catModel.getFriendsCatsList());
            assertEquals(0, catModel.getFriendsCatsList().size());
        }

        @Test
        public void CatModelConstructFromCatWithFriendsTest2() {

            Set<Cat> catFriends1 = new TreeSet<>();

            Cat cat = new Cat();
            cat.setId(1L);
            cat.setName("Cat%d".formatted(1L));
            cat.setBirthDate("%d.%d.200%d".formatted(1L, 1L, 1L));
            cat.setBreed("BreedOne");
            cat.setColor(new CatColor(1L, "WHITE"));
            cat.setOwner(null);

            catFriends1.add(cat);
            cat.setFriendsCats(catFriends1);

            CatModel catModel = new CatModel(cat);

            Cat cat2 = new Cat();
            cat2.setId(2L);
            cat2.setName("Cat%d".formatted(2L));
            cat2.setBirthDate("%d.%d.200%d".formatted(2L, 2L, 2L));
            cat2.setBreed("BreedSecond");
            cat2.setColor(new CatColor(1L, "WHITE"));
            cat2.setOwner(null);

            Set<Cat> catFriends2 = new TreeSet<>(catFriends1);
            catFriends2.add(cat2);
            cat2.setFriendsCats(catFriends2);

            CatModel catModel2 = new CatModel(cat2);

            Cat cat3 = new Cat();
            cat3.setId(3L);
            cat3.setName("Cat%d".formatted(3L));
            cat3.setBirthDate("%d.%d.200%d".formatted(3L, 3L, 3L));
            cat3.setBreed("BreedThird");
            cat3.setColor(new CatColor(1L, "WHITE"));
            cat3.setOwner(null);

            Set<Cat> catFriends3 = new TreeSet<>(catFriends2);
            catFriends3.add(cat3);
            cat3.setFriendsCats(catFriends3);

            CatModel catModel3 = new CatModel(cat3);

            Set<CatModel> cat1Friends = new TreeSet<>(catModel.getFriendsCatsList());
            Set<CatModel> cat2Friends = new TreeSet<>(catModel2.getFriendsCatsList());
            Set<CatModel> cat3Friends = new TreeSet<>(catModel3.getFriendsCatsList());

            assertTrue(cat1Friends.isEmpty());
            assertEquals(1, cat2Friends.size());
            assertEquals(2, cat3Friends.size());

            assertTrue(cat2Friends.contains(catModel));
            assertTrue(cat3Friends.contains(catModel));
            assertTrue(cat3Friends.contains(catModel2));
        }

        @Test
        public void CatModelTransformToCatTest() {
            Cat cat = new Cat();
            cat.setId(1L);
            cat.setName("Cat%d".formatted(1L));
            cat.setBirthDate("%d.%d.200%d".formatted(1L, 1L, 1L));
            cat.setBreed("BreedOne");
            cat.setColor(new CatColor(1L, "WHITE"));
            cat.setOwner(null);
            cat.setFriendsCats(null);

            CatModel catModel = new CatModel(cat);

            assertEquals(cat, catModel.transform());
        }
    }

    @Nested
    public class OwnerModelTests {
        @Test
        public void OwnerModelConstructFromOwnerTest() {
            Owner owner = new Owner(1L, "IVAN", "01.01.2000", null);
            OwnerModel ownerModel = new OwnerModel(owner);

            assertEquals(owner.getId(), ownerModel.getId());
            assertEquals(owner.getName(), ownerModel.getName());
            assertEquals(owner.getBirthDate(), ownerModel.getBirthDate());
            assertNotNull(ownerModel.getCatsNames());
            assertEquals(0, ownerModel.getCatsNames().size());
        }

        @Test
        public void OwnerModelTransformToOwnerTest() {
            Owner owner = new Owner(1L, "IVAN", "01.01.2000", null);
            OwnerModel ownerModel = new OwnerModel(owner);

            assertEquals(owner, ownerModel.transform());
        }
    }

    @Nested
    public class CatServiceTests {
        static CatServiceInterface sut;

        @BeforeEach
        public void Init() {
            sut = new CatService(new MockCatDao());
        }

        @Test
        public void AddCatTest() {
            CatModel catModel = new CatModel(1L, "Miss", "01.01.2020", "breed", Color.RED, null, null);

            OperationResult result = sut.addCat(catModel);
            assertEquals(OperationEnum.Success, result.Type());
        }

        @Test
        public void GetCatByIdTest(){
            CatModel catModel = new CatModel(1L, "Miss", "01.01.2020", "breed", Color.RED, null, new TreeSet<>());

            sut.addCat(catModel);

            OperationResult result = sut.getCatById(1L);
            assertEquals(OperationEnum.SuccessWithMeaning, result.Type());

            OperationResult.SuccessWithMeaning<CatModel> meaning = (OperationResult.SuccessWithMeaning<CatModel>) result;

            assertEquals(catModel, meaning.getValue());
        }

        @Test
        public void GetAllCatsTest() {
            CatModel catModel = new CatModel(1L, "Miss1", "01.01.2020", "breed", Color.RED, null, new TreeSet<>());
            CatModel catModel2 = new CatModel(2L, "Miss2", "01.01.2020", "breed", Color.RED, null, new TreeSet<>());
            CatModel catModel3 = new CatModel(3L, "Miss3", "01.01.2020", "breed", Color.RED, null, new TreeSet<>());

            List<CatModel> models = new ArrayList<>(3);
            models.add(catModel);
            models.add(catModel2);
            models.add(catModel3);

            sut.addCat(catModel);
            sut.addCat(catModel2);
            sut.addCat(catModel3);

            OperationResult result = sut.getAllCats();
            assertEquals(OperationEnum.SuccessWithMeaning, result.Type());

            OperationResult.SuccessWithMeaning<List<CatModel>> meaning = (OperationResult.SuccessWithMeaning<List<CatModel>>) result;
            for (int i = 0; i < models.size(); i++) {
                assertEquals(models.get(i), meaning.getValue().get(i));
            }
        }

        @Test
        public void UpdateCatTest() {
            CatModel catModelInserted = new CatModel(1L, "Miss", "01.01.2020", "breed", Color.RED, null, new TreeSet<>());

            CatModel catModelModified = new CatModel(1L, "OldLady", "01.01.2020", "breed", Color.RED, null, new TreeSet<>());

            sut.addCat(catModelInserted);

            sut.updateCat(catModelModified);

            OperationResult result = sut.getCatById(1L);
            OperationResult.SuccessWithMeaning<CatModel> meaning = (OperationResult.SuccessWithMeaning<CatModel>) result;

            assertEquals(OperationEnum.SuccessWithMeaning, result.Type());
            assertEquals(catModelModified, meaning.getValue());
        }
        @Test
        public void DeleteCatTest() {
            CatModel catModel = new CatModel(1L, "Miss", "01.01.2020", "breed", Color.RED, null, null);

            OperationResult result = sut.addCat(catModel);
            assertEquals(OperationEnum.Success, result.Type());

            result = sut.deleteCat(catModel);
            assertEquals(OperationEnum.Success, result.Type());

            assertEquals(new OperationResult.Failure("Cannot find cat with this id!"), sut.getCatById(1L));
        }
    }

    @Nested
    public class OwnerServiceTests {
        static OwnerServiceInterface sut;

        @BeforeEach
        public void Init() {
            sut = new OwnerService(new MockOwnerDao());
        }

        @Test
        public void AddOwnerTest() {
            OwnerModel ownerModel = new OwnerModel(1L, "Ivan", "01.01.2000", new ArrayList<>());

            OperationResult result = sut.addOwner(ownerModel);
            assertEquals(OperationEnum.Success, result.Type());
        }

        @Test
        public void GetAllOwnersTest() {
            OwnerModel ownerModel = new OwnerModel(1L, "Ivan1", "01.01.2000", new ArrayList<>());
            OwnerModel ownerModel2 = new OwnerModel(2L, "Ivan2", "01.01.2000", new ArrayList<>());
            OwnerModel ownerModel3 = new OwnerModel(3L, "Ivan3", "01.01.2000", new ArrayList<>());

            List<OwnerModel> models = new ArrayList<>(3);
            models.add(ownerModel);
            models.add(ownerModel2);
            models.add(ownerModel3);

            sut.addOwner(ownerModel);
            sut.addOwner(ownerModel2);
            sut.addOwner(ownerModel3);

            OperationResult result = sut.getAllOwners();
            assertEquals(OperationEnum.SuccessWithMeaning, result.Type());

            OperationResult.SuccessWithMeaning<List<OwnerModel>> meaning = (OperationResult.SuccessWithMeaning<List<OwnerModel>>) result;
            for (int i = 0; i < models.size(); i++) {
                assertEquals(models.get(i), meaning.getValue().get(i));
            }
        }

        @Test
        public void UpdateOwnerTest() {
            OwnerModel ownerModelInserted = new OwnerModel(1L, "Ivan1", "01.01.2000", new ArrayList<>());

            OwnerModel ownerModelModified = new OwnerModel(1L, "Eugune", "01.01.2000", new ArrayList<>());

            sut.addOwner(ownerModelInserted);

            sut.updateOwner(ownerModelModified);

            OperationResult result = sut.getOwnerById(1L);
            OperationResult.SuccessWithMeaning<OwnerModel> meaning = (OperationResult.SuccessWithMeaning<OwnerModel>) result;

            assertEquals(OperationEnum.SuccessWithMeaning, result.Type());
            assertEquals(ownerModelModified, meaning.getValue());
        }

        @Test
        public void DeleteOwnerTest() {
            OwnerModel ownerModel = new OwnerModel(1L, "Ivan1", "01.01.2000", new ArrayList<>());

            OperationResult result = sut.addOwner(ownerModel);
            assertEquals(OperationEnum.Success, result.Type());

            result = sut.deleteOwner(ownerModel);
            assertEquals(OperationEnum.Success, result.Type());

            assertEquals(new OperationResult.Failure("Cannot find owner with this id!"), sut.getOwnerById(1L));
        }
    }
}
