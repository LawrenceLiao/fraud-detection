package com.lawrence.model;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record AnalysisResult(String accountNum, BigDecimal totalSpend, boolean isFraudulent, LocalDateTime timeStamp) {

}
