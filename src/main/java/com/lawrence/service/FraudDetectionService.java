package com.lawrence.service;

import com.lawrence.detector.FraudDetector;
import com.lawrence.model.FraudRecord;
import com.lawrence.model.Transaction;
import com.lawrence.parser.OutputParser;
import com.lawrence.parser.TransactionsParser;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class FraudDetectionService {
    private final TransactionsParser transationsParser;
    private final FraudDetector detector;
    private final OutputParser outputParser;


    public List<String> getFraudulentAccounts(List<String> inputs, BigDecimal threshold) {
        List<Transaction> transactions = transationsParser.parseList(inputs);

        List<FraudRecord> results = transactions.stream().map(transaction -> analyse(transaction, threshold)).filter(Objects::nonNull).collect(Collectors.toList());

        return outputParser.parse(results);
    }

    private FraudRecord analyse(Transaction transaction, BigDecimal threshold) {
        return detector.detectByTransaction(transaction, threshold);
    }
}
