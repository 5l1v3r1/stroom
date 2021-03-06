/*
 * Copyright 2017 Crown Copyright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package stroom.authentication;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import org.jooq.Configuration;
import stroom.authentication.exceptions.mappers.BadRequestExceptionMapper;
import stroom.authentication.exceptions.mappers.ConflictExceptionMapper;
import stroom.authentication.exceptions.mappers.NoSuchUserExceptionMapper;
import stroom.authentication.exceptions.mappers.TokenCreationExceptionMapper;
import stroom.authentication.exceptions.mappers.UnsupportedFilterExceptionMapper;
import stroom.authentication.resources.authentication.v1.AuthenticationResource;
import stroom.authentication.resources.authentication.v1.AuthenticationService;
import stroom.authentication.resources.authentication.v1.AuthenticationServiceImpl;
import stroom.authentication.resources.token.v1.TokenResource;
import stroom.authentication.resources.token.v1.TokenService;
import stroom.authentication.resources.token.v1.TokenServiceImpl;
import stroom.authentication.resources.user.v1.UserResource;
import stroom.authentication.service.eventlogging.StroomEventLoggingService;
import stroom.util.guice.GuiceUtil;
import stroom.util.shared.RestResource;

public final class AuthModule extends AbstractModule {
    private Configuration jooqConfig;

    @Override
    protected void configure() {
        bind(UserResource.class);
        bind(TokenVerifier.class);
        bind(EmailSender.class);
        bind(CertificateManager.class);
        bind(TokenBuilderFactory.class);
        bind(StroomEventLoggingService.class);

        bind(ConflictExceptionMapper.class);
        bind(BadRequestExceptionMapper.class);
        bind(TokenCreationExceptionMapper.class);
        bind(UnsupportedFilterExceptionMapper.class);
        bind(NoSuchUserExceptionMapper.class);
        bind(AuthenticationService.class).to(AuthenticationServiceImpl.class);
        bind(TokenService.class).to(TokenServiceImpl.class);
        GuiceUtil.buildMultiBinder(binder(), RestResource.class)
                .addBinding(UserResource.class)
                .addBinding(AuthenticationResource.class)
                .addBinding(TokenResource.class);
    }

    @Provides
    public Configuration getJooqConfig() {
        return jooqConfig;
    }
}
