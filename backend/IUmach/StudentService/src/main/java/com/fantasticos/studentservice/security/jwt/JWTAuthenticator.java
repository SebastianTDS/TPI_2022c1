package com.fantasticos.studentservice.security.jwt;

import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JWTAuthenticator extends AbstractAuthenticationToken {
    private final Object principal;
    private final JWTClaimsSet claims;

    public JWTAuthenticator(Collection<? extends GrantedAuthority> authorities, Object principal, JWTClaimsSet claims) {
        super(authorities);
        this.principal = principal;
        this.claims = claims;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
