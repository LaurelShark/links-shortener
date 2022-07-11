package com.saveme.go.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class CodecServiceTest {

    @InjectMocks
    CodecService subject;

    @Test
    void shouldGenerateLink() {
        var res = subject.generateLink();
        assertNotNull(res);
        assertEquals(6, res.length());
    }
}
