package com.rewardapp.rewardcontroller;

import com.rewardapp.rewardexception.InvalidDateFormatException;
import com.rewardapp.rewardexception.InvalidDateRangeException;
import com.rewardapp.rewardprogrammodel.RewardSummary;
import com.rewardapp.rewardprogrammodel.TransactionModel;
import com.rewardapp.rewardservice.RewardServiceImpl;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * REST controller for handling reward-related endpoints.
 */
@RestController
@RequestMapping("/api/rewards")
public class RewardController {

	@Autowired
	private RewardServiceImpl service;

	private static final Logger logger = LoggerFactory.getLogger(RewardController.class);

	@PostMapping("/transaction")
	public ResponseEntity<String> createTransaction(@Valid @RequestBody TransactionModel transaction) {
	    logger.info("Received transaction from customer '{}', amount: {}", 
	                transaction.getCustomerName(), transaction.getAmount());

	    return ResponseEntity.status(HttpStatus.CREATED)
	                         .body("Transaction received successfully");
	}

	@GetMapping
	public List<RewardSummary> getAll(@RequestParam String startDate, @RequestParam String endDate) {

		LocalDate start = parseDate(startDate, "startDate");
		LocalDate end = parseDate(endDate, "endDate");
		validateDateRange(start, end);

		logger.info("Fetching reward data for all customers between {} and {}", start, end);
		return service.getRewards(start, end);
	}

	@GetMapping("/{customer}")
	public RewardSummary getForCustomer(@PathVariable String customer, @RequestParam String startDate,
			@RequestParam String endDate) {

		LocalDate start = parseDate(startDate, "startDate");
		LocalDate end = parseDate(endDate, "endDate");
		validateDateRange(start, end);

		logger.info("Fetching reward data for customer '{}' between {} and {}", customer, start, end);
		return service.getRewards(customer, start, end);
	}

	private LocalDate parseDate(String dateStr, String paramName) {
		try {
			return LocalDate.parse(dateStr);
		} catch (DateTimeParseException e) {
			logger.error("Invalid date format for {}: {}", paramName, dateStr);
			throw new InvalidDateFormatException(
					"Invalid format for '" + paramName + "': " + dateStr + ". Expected format: yyyy-MM-dd");
		}
	}

	private void validateDateRange(LocalDate start, LocalDate end) {
		if (start == null || end == null) {
			throw new InvalidDateRangeException("Start and end dates must be provided.");
		}
		if (start.isAfter(end)) {
			throw new InvalidDateRangeException("Start date must not be after end date.");
		}
	}
}
