package ru.feolex;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.WebApplicationContext;
import ru.feolex.ServiceLayer.Enums.Color;
import ru.feolex.ServiceLayer.Models.CatModel;
import ru.feolex.ServiceLayer.Models.OwnerModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import jakarta.servlet.ServletContext;
import ru.feolex.ServiceLayer.Models.Serialization.CatModelSerializator;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
public class ControllerTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    private static final String CAT_API_ROOT
            = "http://localhost:8081/api/cats";
    private static final String OWNER_API_ROOT
            = "http://localhost:8081/api/owners";
    private static class EntitiesStorage{
        private static HashMap<String, OwnerModel> owners;
        private static HashMap<String, CatModel> cats;

        public static CatModel getCatById(String catName){
            return cats.get(catName);
        }
        public static OwnerModel getOwnerById(String ownerName){
            return owners.get(ownerName);
        }

        public static void putCat(CatModel cat, String catName){
            cats.put(catName, cat);
        }
        public static void putOwner(OwnerModel owner, String ownerName){
            owners.put(ownerName, owner);
        }

        public static void initOwners(){
            owners = new HashMap<>();
            OwnerModel ownerModel = OwnerModel.builder()
                    .id(1L)
                    .name("Ivan")
                    .birthDate("01.01.1980")
                    .cats(new ArrayList<>())
                    .build();
            owners.put(ownerModel.getName(), ownerModel);
        }
        public static void initCats(){
            cats = new HashMap<>();
            CatModel catModel1 = CatModel.builder()
                    .id(1L)
                    .name("Fluffy")
                    .breed("RUSSIAN")
                    .color(Color.WHITE)
                    .birthDate("01.01.2000")
                    .owner(owners.get("Ivan"))
                    .friendsCats(new TreeSet<>())
                    .build();
            cats.put(catModel1.getName(), catModel1);
            owners.get("Ivan").getCats().add(catModel1);

            CatModel catModel2 = CatModel.builder()
                    .id(2L)
                    .name("Gessy")
                    .breed("RUSSIAN")
                    .color(Color.WHITE)
                    .birthDate("01.01.2000")
                    .owner(owners.get("Ivan"))
                    .friendsCats(new TreeSet<>())
                    .build();
            cats.put(catModel2.getName(), catModel2);
            owners.get("Ivan").getCats().add(catModel2);

            CatModel catModel3 = CatModel.builder()
                    .id(3L)
                    .name("Goblin")
                    .breed("RUSSIAN")
                    .color(Color.RED)
                    .birthDate("01.01.2000")
                    .owner(owners.get("Ivan"))
                    .friendsCats(new TreeSet<>())
                    .build();
            cats.put(catModel3.getName(), catModel3);
            owners.get("Ivan").getCats().add(catModel3);
        }

    }
    private static class TestUtils{

        public static <T> Response GetEntityById(String ApiRoot, Long id){
            StringBuilder stringBuilder = new StringBuilder();
            Response response = RestAssured
                    .given()
                    .get(stringBuilder.append(ApiRoot).append("/").append(id).toString());
            System.out.println("GetEntityByIdResponceCode: " + response.getStatusCode());

            return response;
        }
        public static <T> Response PostEntity(String ApiRoot, T Entity){
            Response response = RestAssured
                    .given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(Entity)
                    .post(ApiRoot);
            System.out.println("PostEntityResponceCode: " + response.getStatusCode());

            return response;
        }

        public static <T> Response DeleteEntityById(String ApiRoot, Long id){
            StringBuilder stringBuilder = new StringBuilder();
            Response response = RestAssured
                    .given()
                    .get(stringBuilder.append(ApiRoot).append("/").append(id).toString());
            System.out.println("DeleteEntityByIdResponceCode: " + response.getStatusCode());

            return response;
        }
        public static <T> String GetEntityAsString(String ApiRoot, T Entity){
            Response response = RestAssured
                    .given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(Entity)
                    .post(ApiRoot);

            System.out.println("GetEntityResponceCode: " + response.getStatusCode());

            return ApiRoot + "/" + response.jsonPath().get("id");
        }
    }
    @Test
    public void init(
    ){}

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @BeforeAll
    public static void initCollection(){
        EntitiesStorage.initOwners();
        EntitiesStorage.initCats();
    }
//    @Test
//    public void whenTryingToPostOwnerWithCats_thenOk() throws Exception {
//
//        Response responce = TestUtils.PostEntity(OWNER_API_ROOT, EntitiesStorage.getOwnerById("Ivan"));
//
//        assertEquals(201, responce.getStatusCode());
//    }
}
