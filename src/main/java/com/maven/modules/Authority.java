package com.maven.modules;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
@Data
public class Authority implements GrantedAuthority
{
    private String authority;

    @Override
    public String getAuthority() {
        return null;
    }

}
