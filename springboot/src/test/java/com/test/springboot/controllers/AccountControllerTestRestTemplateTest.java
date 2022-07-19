package com.test.springboot.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.springboot.models.Account;
import com.test.springboot.utils.Data;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class AccountControllerTestRestTemplateTest {

    @Autowired
    private TestRestTemplate client;
    private ObjectMapper objectMapper;

    @LocalServerPort
    private String port;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @Order(1)
    void testTransfer() throws JsonProcessingException {
        ResponseEntity<String> response = client
                .postForEntity("/api/accounts/transfer", Data.transactionRequest, String.class);

        String jsonResponse = response.getBody();

        assertNotNull(jsonResponse);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertTrue(jsonResponse.contains("Transfer with success"));

        JsonNode jsonNode = objectMapper.readTree(jsonResponse);
        assertEquals("Transfer with success", jsonNode.path("message").asText());
        assertEquals(LocalDate.now().toString(), jsonNode.path("date").asText());
        assertEquals("OK", jsonNode.path("status").asText());
    }

    @Test
    @Order(2)
    void testGetAccount() {
        ResponseEntity<Account> response = client.getForEntity("/api/accounts/1", Account.class);

        Account account = response.getBody();

        assertNotNull(account);
        assertEquals(1, account.getId());
        assertEquals("Eric", account.getName());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
    }

    @Test
    @Order(3)
    void testListAccounts() {
        ResponseEntity<Account[]> response = client.getForEntity("/api/accounts", Account[].class);

        List<Account> accounts = Arrays.asList(response.getBody());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());

        assertNotNull(accounts);
        assertEquals(2, accounts.size());
        assertEquals(1, accounts.get(0).getId());
        assertEquals("Eric", accounts.get(0).getName());
        assertEquals(2, accounts.get(1).getId());
        assertEquals("Lucas", accounts.get(1).getName());
    }

    @Test
    @Order(4)
    void testSaveAccount() {
        ResponseEntity<Account> response = client.postForEntity("/api/accounts", Data.accountToCreate, Account.class);

        Account account = response.getBody();

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());

        assertNotNull(account);
        assertEquals(3, account.getId());
        assertEquals("Test", account.getName());
    }

    @Test
    @Order(5)
    void testDeleteAccount() {
        // client.delete("/api/accounts");

        ResponseEntity<Void> request = client.exchange("/api/accounts/1", HttpMethod.DELETE, null, Void.class);

        assertEquals(HttpStatus.NO_CONTENT, request.getStatusCode());
        assertFalse(request.hasBody());
    }
}