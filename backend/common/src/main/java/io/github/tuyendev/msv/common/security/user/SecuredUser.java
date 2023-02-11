package io.github.tuyendev.msv.common.security.user;

import io.github.tuyendev.msv.common.constant.EntityStatus;
import io.github.tuyendev.msv.common.constant.UserEntity;

import java.io.Serializable;
import java.util.Set;

public interface SecuredUser extends Serializable {

    SecuredUser ANONYMOUS_USER = new SecuredUser() {
        private static final String ANONYMOUS_USER = "anonymous";

        @Override
        public Long getId() {
            return UserEntity.DEFAULT_USER_ANONYMOUS_ID;
        }

        @Override
        public String getUsername() {
            return ANONYMOUS_USER;
        }

        @Override
        public String getPreferredUsername() {
            return ANONYMOUS_USER;
        }

        @Override
        public String getPassword() {
            return null;
        }

        @Override
        public String getEmail() {
            return ANONYMOUS_USER;
        }

        @Override
        public String getName() {
            return ANONYMOUS_USER;
        }

        @Override
        public Integer getEnabled() {
            return EntityStatus.ENABLED;
        }

        @Override
        public Integer getLocked() {
            return EntityStatus.UNLOCKED;
        }

        @Override
        public Set<String> getAuthorityNames() {
            return Set.of();
        }
    };

    Long getId();

    String getUsername();

    String getPreferredUsername();

    String getPassword();

    String getEmail();

    String getName();

    Integer getEnabled();

    Integer getLocked();

    Set<String> getAuthorityNames();
}
