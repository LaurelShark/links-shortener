package com.saveme.go.service;

import com.saveme.go.entity.Link;
import com.saveme.go.repository.LinkRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URISyntaxException;
import java.util.UUID;

import static com.saveme.go.service.LinkService.URI_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LinkServiceTest {

    private static final String HTTPS_SHORT = "https://short";
    private static final String HTTPS_ORIGINAL = "https://original";
    @Mock
    LinkRepository linkRepository;

    @InjectMocks
    LinkService subject;

    @Captor
    ArgumentCaptor<String> captor;

    @Test
    void shouldNotRedirect_whenOriginalURIDoesntExist() {
        when(linkRepository.getOriginalByShort(anyString())).thenReturn(null);
        assertThrows(RuntimeException.class, () -> subject.redirect(HTTPS_SHORT), URI_NOT_FOUND);
    }

    @Test
    void shouldRedirectByOriginalURI() throws URISyntaxException {
        when(linkRepository.getOriginalByShort(HTTPS_SHORT)).thenReturn(new Link(UUID.randomUUID(), HTTPS_ORIGINAL, HTTPS_SHORT));

        var redirect = subject.redirect(HTTPS_SHORT);

        assertEquals("original", redirect.getHost());
        assertEquals("https", redirect.getScheme());
    }

    @Test
    void shouldGenerateShortLink_whenOriginalURIDoesntExist() {
        when(linkRepository.getShortByOriginal(HTTPS_ORIGINAL)).thenReturn(null);

        var res = subject.getShortLink(HTTPS_ORIGINAL);

        assertNotNull(res.getOriginalLink());
        assertEquals(HTTPS_ORIGINAL, res.getOriginalLink());
        assertNotNull(res.getShortLink());
    }
}
