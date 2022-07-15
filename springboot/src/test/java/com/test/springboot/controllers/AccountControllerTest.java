package com.test.springboot.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.springboot.models.Account;
import com.test.springboot.services.AccountService;
import com.test.springboot.utils.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AccountService accountService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetAccount() throws Exception {
        //given
        when(accountService.findById(anyLong())).thenReturn(Data.account1);

        //when
        ResultActions request = mvc.perform(MockMvcRequestBuilders
                .get("/api/accounts/1")
                .contentType(MediaType.APPLICATION_JSON));

        //then
        request.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Eric"));

        verify(accountService).findById(1L);
    }

    @Test
    void testTransfer() throws Exception {
        //given
        String requestContent = objectMapper.writeValueAsString(Data.transactionRequest);

        //when
        ResultActions request = mvc.perform(MockMvcRequestBuilders
                .post("/api/accounts/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestContent));

        //then
        request.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("Transfer with success"));
    }

    @Test
    void testListAccounts() throws Exception {
        //given
        List<Account> accounts = List.of(Data.account1, Data.account2);
        when(accountService.findAll()).thenReturn(accounts);

        //when
        ResultActions request = mvc.perform(MockMvcRequestBuilders
                .get("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON));

        //then
        request.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(content().json(objectMapper.writeValueAsString(accounts)));

        verify(accountService).findAll();
    }

    @Test
    void testSaveAccount() throws Exception {
        //given
        Account account = new Account(null, "Polo", 10.0);

        when(accountService.save(any(Account.class))).then(invocationOnMock -> {
            Account ac = invocationOnMock.getArgument(0);
            ac.setId(5L);
            return ac;
        });

        //when
        ResultActions request = mvc.perform(MockMvcRequestBuilders
                .post("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(account)));

        //then
        request.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(5)))
                .andExpect(jsonPath("$.name", is("Polo")));

        verify(accountService).save(any(Account.class));
    }
}