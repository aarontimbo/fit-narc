package com.atimbo.common.utils

import spock.lang.Specification

class UniqueIDGeneratorSpec extends Specification {

    void 'generate unique id with no dashes'() {
        setup:
        String uuid = UniqueIDGenerator.generateUUId()

        expect:
        uuid
        ! uuid.find { it =~ /-/ }
    }
}
