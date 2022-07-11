package com.saveme.go.controller;

import com.saveme.go.entity.Video;
import com.saveme.go.service.VideoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VideoControllerTest {

    @Mock
    VideoService videoService;

    @InjectMocks
    VideoController subject;

    @Captor
    ArgumentCaptor<String> captor;

    @Test
    void shouldFindById() {
        var mockedResponse = new Video();
        when(videoService.findByUUID(anyLong())).thenReturn(mockedResponse);
        var result = subject.findById(19L);
        assertEquals(result, mockedResponse);
    }

    @Test
    void shouldSaveVideo() {
        var videoName = "video";
        var mockedResponse = new Video();
        when(videoService.save(videoName)).thenReturn(mockedResponse);

        var result = subject.save(videoName);

        verify(videoService).save(captor.capture());
        assertEquals(captor.getValue(), videoName);
        assertEquals(result, mockedResponse);
    }
}
