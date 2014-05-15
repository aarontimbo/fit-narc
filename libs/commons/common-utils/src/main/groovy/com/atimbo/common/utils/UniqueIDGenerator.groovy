package com.atimbo.common.utils

class UniqueIDGenerator {

    static String generateUUId() {
        String randomUUId = UUID.randomUUID()
        return randomUUId.replaceAll('-', '')
    }

}
