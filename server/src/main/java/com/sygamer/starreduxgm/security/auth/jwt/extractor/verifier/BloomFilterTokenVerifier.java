package com.sygamer.starreduxgm.security.auth.jwt.extractor.verifier;

import org.springframework.stereotype.Component;

/**
 * BloomFilterTokenVerifier
 * 
 * @author vladimir.stankovic
 *
 * Aug 17, 2016
 */
@Component
public class BloomFilterTokenVerifier implements TokenVerifier {
    @Override
    public boolean verify(String jti) {
        return true;
    }
}