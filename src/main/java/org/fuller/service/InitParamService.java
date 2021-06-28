package org.fuller.service;

public class InitParamService {
    private static InitParamService instance;
    private InitParamService(){};
    static {
        instance = new InitParamService();
    }

    public static InitParamService getInstance() {
        return instance;
    }
}
