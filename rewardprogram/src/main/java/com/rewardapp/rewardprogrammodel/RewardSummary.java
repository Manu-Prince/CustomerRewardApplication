package com.rewardapp.rewardprogrammodel;

import java.util.List;
import java.util.Map;

/**
 * Aggregated reward result per customer.
 */
public class RewardSummary {
	private String customerName;
	private int totalPoints;
	private Map<String, Integer> monthlyPoints; 
	private List<TransactionModel> transactions;

	public RewardSummary(String customerName, int totalPoints, Map<String, Integer> monthlyPoints,
			List<TransactionModel> transactions) {
		super();
		this.customerName = customerName;
		this.totalPoints = totalPoints;
		this.monthlyPoints = monthlyPoints;
		this.transactions = transactions;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public int getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(int totalPoints) {
		this.totalPoints = totalPoints;
	}

	public Map<String, Integer> getMonthlyPoints() {
		return monthlyPoints;
	}

	public void setMonthlyPoints(Map<String, Integer> monthlyPoints) {
		this.monthlyPoints = monthlyPoints;
	}

	public List<TransactionModel> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<TransactionModel> transactions) {
		this.transactions = transactions;
	}

}
