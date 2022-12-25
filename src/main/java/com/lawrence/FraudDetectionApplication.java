package com.lawrence;

import com.lawrence.detector.FraudDetector;
import com.lawrence.parser.TransactionsParser;
import com.lawrence.service.FraudDetectionService;

import java.math.BigDecimal;
import java.util.List;

public class FraudDetectionApplication {
    public static void main(String[] args) {
        FraudDetectionService service = new FraudDetectionService(new TransactionsParser(), new FraudDetector());

        List<String> input = List.of(
                "10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:54, 10.00",
                "10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T16:15:54, 70.00",
                "10d7ce2f43e35fp5od1bbf8b1e2, 2014-04-30T11:11:33, 30.00",
                "10d7ce2f43e35fp5od1bbf8b1e2, 2014-04-30T11:15:50, 50.00",
                "10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-30T12:38:56, 80.00",
                "10d7ce2f93e35fm57d1bbf8b1e2, 2014-04-30T14:48:56, 60.00"
        );
        BigDecimal threshold = BigDecimal.valueOf(150);

        List<String> fraudulentAccounts = service.getFraudulentAccounts(input, threshold);
    }
}
