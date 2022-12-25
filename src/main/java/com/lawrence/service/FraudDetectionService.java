package com.lawrence.service;

import com.lawrence.detector.FraudDetector;
import com.lawrence.model.AnalysisResult;
import com.lawrence.model.Transaction;
import com.lawrence.parser.TransactionsParser;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class FraudDetectionService {
    private final TransactionsParser parser;
    private final FraudDetector detector;

    private Set<AnalysisResult> analysisResults = new HashSet<>();

    public List<String> getFraudulentAccounts(List<String> inputs, BigDecimal threshold) {
        List<Transaction> transactions = parser.parseList(inputs);

        Set<AnalysisResult> results = transactions.stream().map(transaction -> analyse(transaction, threshold)).collect(Collectors.toSet());
        analysisResults.addAll(results);

        return getFraudList();
    }

    private AnalysisResult analyse(Transaction transaction, BigDecimal threshold) {
        return detector.detectByTransaction(transaction, threshold);
    }

    private List<String> getFraudList() {
        return analysisResults.stream().filter(result -> result.isFraudulent()).map(AnalysisResult::accountNum).distinct().toList();
    }
}
