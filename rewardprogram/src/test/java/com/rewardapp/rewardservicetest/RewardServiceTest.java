package com.rewardapp.rewardservicetest;

import com.rewardapp.rewardentity.Transaction;
import com.rewardapp.rewardexception.CustomerNotFoundException;
import com.rewardapp.rewardexception.RewardCalculationException;
import com.rewardapp.rewardprogrammodel.RewardSummary;
import com.rewardapp.rewardrepository.TransactionRepository;
import com.rewardapp.rewardservice.RewardServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RewardServiceTest {

    @InjectMocks
    private RewardServiceImpl rewardService;

    @Mock
    private TransactionRepository repository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getRewards_ShouldReturnSummary_WhenValidCustomer() {
        LocalDate start = LocalDate.of(2025, 7, 1);
        LocalDate end = LocalDate.of(2025, 7, 6);
        List<Transaction> transactions = List.of(new Transaction("Alice", start, 120.0 ));

        when(repository.findByCustomerNameIgnoreCaseAndDateBetween("Alice", start, end)).thenReturn(transactions);

        RewardSummary summary = rewardService.getRewards("Alice", start, end);

        assertEquals("Alice", summary.getCustomerName());
        assertTrue(summary.getTotalPoints() > 0);
    }

    @Test
    void getRewards_ShouldThrowCustomerNotFound_WhenNoTransactions() {
        when(repository.findByCustomerNameIgnoreCaseAndDateBetween(anyString(), any(), any()))
                .thenReturn(Collections.emptyList());

        assertThrows(CustomerNotFoundException.class, () ->
                rewardService.getRewards("Bob", LocalDate.now(), LocalDate.now()));
    }

    @Test
    void getRewards_ShouldThrowRewardCalculationException_WhenNegativeAmount() {
        List<Transaction> transactions = List.of(new Transaction("Bob", LocalDate.now(),  -100.0));

        when(repository.findByCustomerNameIgnoreCaseAndDateBetween(any(), any(), any()))
                .thenReturn(transactions);

        assertThrows(RewardCalculationException.class, () ->
                rewardService.getRewards("Bob", LocalDate.now(), LocalDate.now()));
    }
}
