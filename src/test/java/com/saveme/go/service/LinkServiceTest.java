package com.saveme.go.service;

import com.saveme.go.converter.LinkDtoEntityConverter;
import com.saveme.go.entity.Link;
import com.saveme.go.repository.LinkRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URISyntaxException;
import java.util.UUID;

import static com.saveme.go.service.LinkService.URI_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LinkServiceTest {

    private static final String HTTPS_SHORT = "https://short";
    private static final String HTTPS_ORIGINAL = "https://www.google.com";
    @Mock
    LinkRepository linkRepository;

    @Mock
    CodecService codecService;

    @Spy
    LinkDtoEntityConverter linkDtoEntityConverter;

    @Captor
    ArgumentCaptor<Link> captor;

    @InjectMocks
    LinkService subject;

    @Test
    void shouldNotRedirect_whenOriginalURIDoesntExist() {
        when(linkRepository.getOriginalByShort(anyString())).thenReturn(null);
        assertThrows(RuntimeException.class, () -> subject.getRedirectURL(HTTPS_SHORT), URI_NOT_FOUND);
    }

    @Test
    void shouldRedirectByOriginalURI() throws URISyntaxException {
        when(linkRepository.getOriginalByShort(HTTPS_SHORT)).thenReturn(
                new Link(UUID.randomUUID(), HTTPS_ORIGINAL, HTTPS_SHORT, 0));

        var redirect = subject.getRedirectURL(HTTPS_SHORT);

        assertEquals("www.google.com", redirect.getHost());
        assertEquals("https", redirect.getScheme());
    }

    @Test
    void shouldGenerateShortLink_whenOriginalURIDoesntExist() {
        var uuid = UUID.randomUUID();
        Link link = new Link(uuid, HTTPS_ORIGINAL, HTTPS_SHORT, 0);
        when(codecService.generateLink()).thenReturn(HTTPS_SHORT);
        when(linkRepository.save(any())).thenReturn(link);
        when(linkRepository.getShortByOriginal(HTTPS_ORIGINAL)).thenReturn(null);

        var res = subject.getShortLink(HTTPS_ORIGINAL);

        assertTrue(res.getShortLink().contains(HTTPS_SHORT));
        assertNotNull(res.getOriginalLink());
        assertEquals(HTTPS_ORIGINAL, res.getOriginalLink());
    }
}
