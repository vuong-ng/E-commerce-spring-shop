package com.javaproject.springshop.service.blacklist;

public interface ITokenBlackListService {
    void addToBlackList(String token);

    boolean isBlacklisted(String token);
}
