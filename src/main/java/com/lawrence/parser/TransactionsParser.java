package com.lawrence.parser;

import com.lawrence.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionsParser {
    private static final String SEPARATOR = ", ";

    public List<Transaction> parseList(List<String> inputs) {
        return inputs.stream().map(this::parse).collect(Collectors.toList());

    }

    private Transaction parse(String input) {
        String[] inputArr = input.trim().split(SEPARATOR);
        return Transaction.builder()
                .accountNum(inputArr[0])
                .amount(new BigDecimal(inputArr[2]))
                .timeStamp(LocalDateTime.parse(inputArr[1]))
                .build();

    }
}
