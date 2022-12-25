package com.lawrence.parser;

import com.lawrence.model.FraudRecord;

import java.util.List;
import java.util.stream.Collectors;

public class OutputParser {

    public List<String> parse(List<FraudRecord> fraudRecords) {
        return fraudRecords.stream()
                .map(FraudRecord::accountNum)
                .distinct()
                .collect(Collectors.toList());
    }
}
