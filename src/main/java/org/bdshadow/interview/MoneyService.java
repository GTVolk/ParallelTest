package org.bdshadow.interview;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class MoneyService {

    private static final int HARDCODED_NUMBER_OF_CANDIDATES = 100;
    private final CandidateService candidateService;

    @SuppressWarnings("unchecked")
    public int countMoney() throws ExecutionException, InterruptedException {
        AtomicInteger result = new AtomicInteger();
        CompletableFuture<Void>[] futures = new CompletableFuture[HARDCODED_NUMBER_OF_CANDIDATES];
        for (int i = 1; i <= HARDCODED_NUMBER_OF_CANDIDATES; i++) {
            futures[i - 1] = candidateService.getMoney(i).thenAccept(money -> result.addAndGet(money));
        }
        CompletableFuture.allOf(futures).get();
        return result.get();
    }
}
