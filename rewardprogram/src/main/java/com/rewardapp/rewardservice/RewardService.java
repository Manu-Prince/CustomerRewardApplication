package com.rewardapp.rewardservice;

import java.time.LocalDate;
import java.util.List;

import com.rewardapp.rewardprogrammodel.RewardSummary;

public interface RewardService {
	RewardSummary getRewards(String customer, LocalDate start, LocalDate end);

	List<RewardSummary> getRewards(LocalDate start, LocalDate end);

}
