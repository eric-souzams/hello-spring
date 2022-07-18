package com.test.springboot.controllers;

import com.test.springboot.models.Account;
import com.test.springboot.utils.Data;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class AccountControllerWebTestClientTest {

    @Autowired
    private WebTestClient client;

    @Test
    @Order(1)
    void testTransfer() {
        client.post()
                .uri("http://localhost:8080/api/accounts/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(Data.transactionRequest)
                .exchange()
                .expectStatus()
                    .isOk()
                .expectBody()
                    .jsonPath("$.date").isEqualTo(LocalDate.now().toString())
                    .jsonPath("$.status").value(is("OK"))
                    .jsonPath("$.message").value(is("Transfer with success"))
                    .jsonPath("$.message").value(value -> assertEquals("Transfer with success", value))
                    .jsonPath("$.message").isEqualTo("Transfer with success");
    }

    @Test
    @Order(2)
    void testGetAccount() {
        client.get()
                .uri("http://localhost:8080/api/accounts/1")
                .exchange()
                .expectStatus()
                    .isOk()
                .expectHeader()
                    .contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                    .jsonPath("$.id").isEqualTo(1)
                    .jsonPath("$.name").isEqualTo("Eric");
    }

    @Test
    @Order(3)
    void testGetAccount2() {
        client.get()
                .uri("http://localhost:8080/api/accounts/1")
                .exchange()
                .expectStatus()
                    .isOk()
                .expectHeader()
                    .contentType(MediaType.APPLICATION_JSON)
                .expectBody(Account.class)
                    .consumeWith(response -> {
                        Account account = response.getResponseBody();
                        assertNotNull(account);
                        assertEquals(1, account.getId());
                        assertEquals("Eric", account.getName());
                    });
    }

    @Test
    @Order(4)
    void testListAllAccounts() {
        client.get()
                .uri("http://localhost:8080/api/accounts")
                .exchange()
                .expectStatus()
                    .isOk()
                .expectHeader()
                    .contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                    .jsonPath("$").isArray()
                    .jsonPath("$[0].id").isEqualTo(1)
                    .jsonPath("$[0].name").isEqualTo("Eric")
                    .jsonPath("$[1].id").isEqualTo(2)
                    .jsonPath("$[1].name").isEqualTo("Lucas");
    }

    @Test
    @Order(5)
    void testListAllAccounts2() {
        client.get()
                .uri("http://localhost:8080/api/accounts")
                .exchange()
                .expectStatus()
                    .isOk()
                .expectHeader()
                    .contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Account.class)
                    .consumeWith(response -> {
                        List<Account> accounts = response.getResponseBody();
                        assertNotNull(accounts);
                        assertEquals(2, accounts.size());
                        assertEquals("Eric", accounts.get(0).getName());
                        assertEquals(1, accounts.get(0).getId());
                    })
                    .hasSize(2);
    }

    @Test
    @Order(6)
    void testSaveNewAccount() {
        client.post()
                .uri("http://localhost:8080/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(Data.accountToCreate)
                .exchange()
                .expectStatus()
                    .isCreated()
                .expectBody()
                    .jsonPath("$.id").isEqualTo(3)
                    .jsonPath("$.name").isEqualTo("Test");
    }

    @Test
    @Order(7)
    void testDeleteAccount() {
        client.delete()
                .uri("http://localhost:8080/api/accounts/2")
                .exchange()
                .expectStatus()
                    .isNoContent()
                .expectBody()
                    .isEmpty();
    }
}