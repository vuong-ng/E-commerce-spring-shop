package com.javaproject.springshop.service.blacklist;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class TokenBackListService implements ITokenBlackListService {
    private Set<String> blacklist = new HashSet<>();

    @Override
    public void addToBlackList(String token) {
        blacklist.add(token);
    }

    @Override
    public boolean isBlacklisted(String token) {
        return blacklist.contains(token);
    }

}
