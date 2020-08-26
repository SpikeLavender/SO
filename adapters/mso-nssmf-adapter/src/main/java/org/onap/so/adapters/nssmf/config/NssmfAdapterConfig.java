package org.onap.so.adapters.nssmf.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class NssmfAdapterConfig {

    @Value("${mso.infra.endpoint}")
    private String infraEndpoint;

    @Value("${mso.infra.auth}")
    private String infraAuth;
}
