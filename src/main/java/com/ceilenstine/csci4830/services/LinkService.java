package com.ceilenstine.csci4830.services;

import com.ceilenstine.csci4830.exceptions.LinkNotFoundException;
import com.ceilenstine.csci4830.models.LinkEntity;
import com.ceilenstine.csci4830.respositories.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import javax.persistence.EntityNotFoundException;

@Service
public class LinkService {

    String alphaNumeric = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    Integer base = alphaNumeric.length();

    private final LinkRepository linkRepository;

    @Autowired
    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public String getShortUrl(String longUrl) {
        Optional<LinkEntity> entity = linkRepository.findByLongUrl(longUrl);
        if (!entity.isPresent()) {
            LinkEntity linkEntity = new LinkEntity(longUrl);
            linkRepository.save(linkEntity);
            linkEntity.setShortUrl(encode(linkEntity.getId()));
            linkRepository.save(linkEntity);
            return linkEntity.getShortUrl();
        } else {
            return entity.get().getShortUrl();
        }
    }

    public String getLongUrl(String shortUrl) throws EntityNotFoundException {
        Optional<LinkEntity> entity = linkRepository.findByShortUrl(shortUrl);
        if (entity.isPresent()) {
            return entity.get().getLongUrl();
        } else {
            throw new LinkNotFoundException(shortUrl);
        }
    }

    public String encode(Integer id) {
        StringBuilder stringBuilder = new StringBuilder();
        while (id != 0) {
            stringBuilder.append(alphaNumeric.charAt(id % base));
            id /= base;
        }
        while (stringBuilder.length() < 6) {
            stringBuilder.append(0);
        }
        return stringBuilder.reverse().toString();
    }

}
