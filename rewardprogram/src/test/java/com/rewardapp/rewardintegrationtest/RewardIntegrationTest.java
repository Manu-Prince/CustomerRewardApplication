package com.rewardapp.rewardintegrationtest;

import com.rewardapp.rewardentity.Transaction;
import com.rewardapp.rewardrepository.TransactionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RewardIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TransactionRepository repository;

    @BeforeEach
    void setup() {
        repository.deleteAll();
        repository.saveAll(List.of(
                new Transaction("Alice", LocalDate.of(2025, 7, 1), 120.0),
                new Transaction("Alice", LocalDate.of(2025, 7, 2), 50.0)
        ));
    }

    @Test
    void shouldReturnRewards_ForValidCustomer() throws Exception {
        mockMvc.perform(get("/api/rewards/Alice")
                        .param("startDate", "2025-07-01")
                        .param("endDate", "2025-07-31"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value("Alice"));
    }

    @Test
    void shouldReturn400_ForInvalidDateFormat() throws Exception {
        mockMvc.perform(get("/api/rewards")
                        .param("startDate", "07-01-2025")
                        .param("endDate", "2025-07-06"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn404_ForUnknownCustomer() throws Exception {
        mockMvc.perform(get("/api/rewards/Bob")
                        .param("startDate", "2025-07-01")
                        .param("endDate", "2025-07-31"))
                .andExpect(status().isNotFound());
    }
}
