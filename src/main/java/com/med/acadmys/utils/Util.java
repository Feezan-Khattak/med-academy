package com.med.acadmys.utils;

import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

@Component
public class Util {

    public String generateQuestionCode() {
        String uuid = UUID.randomUUID().toString().substring(0,6);
        return "QC" + uuid;
    }
}
