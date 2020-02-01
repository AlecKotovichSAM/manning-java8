/**
 * 
 */
package com.alec.java8.chapter01;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author okoto
 *
 */
public class Lesson_02_StreamsIntro {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Map<Currency, List<Transaction>> transactionsByCurrencies = new HashMap<>();

		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(new Transaction(300, Currency.EUR));
		transactions.add(new Transaction(400, Currency.USD));
		transactions.add(new Transaction(1050, Currency.BYN));

		// old-fashioned way
		for (Transaction transaction : transactions) {
			if (transaction.getPrice() > 1000) {
				Currency currency = transaction.getCurrency();
				List<Transaction> transactionsForCurrency = transactionsByCurrencies.get(currency);
				if (transactionsForCurrency == null) {
					transactionsForCurrency = new ArrayList<>();
					transactionsByCurrencies.put(currency, transactionsForCurrency);
				}
				transactionsForCurrency.add(transaction);
			}
		}

		BiConsumer<? super Currency, ? super List<Transaction>> action = (k, v) -> System.out
				.println(k + " = " + v.toString());
		transactionsByCurrencies.forEach(action);

		// Stream modern way adds some magic :)
		transactions //
				.stream() //
				.filter(t -> t.getPrice() > 1000) //
				.collect(Collectors.groupingBy(Transaction::getCurrency)) //
				.forEach(action);

		transactions //
				.stream() //
				.filter(t -> t.getPrice() > 1000) //
				.collect(Collectors.toList());

	}

	@Getter
	@AllArgsConstructor
	@ToString
	public static class Transaction {
		private int price;
		private Currency currency;
	}

	public enum Currency {
		EUR, USD, BYN
	}

}
