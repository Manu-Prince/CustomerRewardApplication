package com.rewardapp.rewardprogrammodel;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

/**
 * Simplified transaction data with reward points.
 */
public class TransactionModel {
	@NotNull(message = "Customer name is required")
	private String customerName;
	private LocalDate date;
//    @NotNull(message = "Customer amount is required")
	private double amount;
	private int points;

	public TransactionModel(String customerName, LocalDate date, double amount, int points) {
		super();
		this.customerName = customerName;
		this.date = date;
		this.amount = amount;
		this.points = points;
	}

	public String getCustomerName() {
		return customerName;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

}
