package com.example.semanticsearch.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Aspect
@Component
public class LoggingAspect {

    private final InMemoryLogStore logStore;
    private final ObjectMapper objectMapper;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public LoggingAspect(InMemoryLogStore logStore, ObjectMapper objectMapper) {
        this.logStore = logStore;
        this.objectMapper = objectMapper;
    }

    @Around("execution(* com.example.semanticsearch.service.SemanticSearchService.*(..))")
    public Object logExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        LocalDateTime incomingTime = LocalDateTime.now();
        long start = System.currentTimeMillis();

        String methodName = joinPoint.getSignature().getName();
        String inputStr = "";
        try {
            if (joinPoint.getArgs().length > 0) {
                inputStr = objectMapper.writeValueAsString(joinPoint.getArgs()[0]);
            }
        } catch (Exception e) {
            inputStr = "Error serializing input";
        }

        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable t) {
            throw t;
        }

        long end = System.currentTimeMillis();
        LocalDateTime outgoingTime = LocalDateTime.now();
        long duration = end - start;

        String outputStr = "";
        try {
            if (result != null) {
                outputStr = objectMapper.writeValueAsString(result);
            }
        } catch (Exception e) {
            outputStr = "Error serializing output";
        }

        // Format: Method | Input | Output | Incoming Time | Outgoing Time | Duration
        // Truncate input/output if too long for tabular view
        String formattedInput = truncate(inputStr, 50);
        String formattedOutput = truncate(outputStr, 50);

        String logEntry = String.format("%-20s | %-50s | %-50s | %-25s | %-25s | %-15d",
                methodName, formattedInput, formattedOutput,
                incomingTime.format(FORMATTER), outgoingTime.format(FORMATTER), duration);

        logStore.addLog(methodName, logEntry); // Log by method name (save/search)

        return result;
    }

    private String truncate(String str, int maxWidth) {
        if (str == null)
            return "null";
        if (str.length() <= maxWidth)
            return str;
        return str.substring(0, maxWidth - 3) + "...";
    }
}
