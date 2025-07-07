package com.rewardapp.rewardcontrollertest;


import com.rewardapp.rewardcontroller.RewardController;
import com.rewardapp.rewardprogrammodel.RewardSummary;
import com.rewardapp.rewardservice.RewardServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RewardController.class)
class RewardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RewardServiceImpl service;

    @Test
    void getAll_ShouldReturn200WithRewards() throws Exception {
        Mockito.when(service.getRewards(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(List.of(new RewardSummary("Alice", 100, null, null)));

        mockMvc.perform(get("/api/rewards")
                .param("startDate", "2025-07-01")
                .param("endDate", "2025-07-06"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customerName").value("Alice"));
    }

    @Test
    void getAll_ShouldReturn400_ForInvalidDateFormat() throws Exception {
        mockMvc.perform(get("/api/rewards")
                .param("startDate", "07-01-2025")
                .param("endDate", "2025-07-06"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Invalid format")));
    }

    @Test
    void getForCustomer_ShouldReturn400_IfStartDateAfterEndDate() throws Exception {
        mockMvc.perform(get("/api/rewards/Alice")
                .param("startDate", "2025-07-10")
                .param("endDate", "2025-07-01"))
                .andExpect(status().isBadRequest());
    }
}

