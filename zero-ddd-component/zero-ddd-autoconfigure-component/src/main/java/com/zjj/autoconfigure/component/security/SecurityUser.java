package com.zjj.autoconfigure.component.security;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zjj.autoconfigure.component.tenant.TenantDetail;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月10日 21:17
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SecurityUser implements UserDetails, TenantDetail, CredentialsContainer {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private static final Log logger = LogFactory.getLog(SecurityUser.class);

    private String password;

    private final String username;

    private final String tenant;

    @JsonIgnore
    private final Set<GrantedAuthority> authorities;

    //存储权限信息
    @Getter
    private final Set<String> roles;

    private final boolean accountNonExpired;

    private final boolean accountNonLocked;

    private final boolean credentialsNonExpired;

    private final boolean enabled;
    @Getter
    @Setter
    private LocalDateTime expire;

    /**
     * Calls the more complex constructor with all boolean arguments set to {@code true}.
     */
    public SecurityUser(String username, String password, Set<String> roles) {
        this(username, password, null, true, true, true, true, roles, null);
    }

    /**
     * Construct the <code>User</code> with the details required by
     * {@link org.springframework.security.authentication.dao.DaoAuthenticationProvider}.
     * @param username the username presented to the
     * <code>DaoAuthenticationProvider</code>
     * @param password the password that should be presented to the
     * <code>DaoAuthenticationProvider</code>
     * @param enabled set to <code>true</code> if the user is enabled
     * @param accountNonExpired set to <code>true</code> if the account has not expired
     * @param credentialsNonExpired set to <code>true</code> if the credentials have not
     * expired
     * @param accountNonLocked set to <code>true</code> if the account is not locked
     * @param roles the authorities that should be granted to the caller if they
     * presented the correct username and password and the user is enabled. Not null.
     * @throws IllegalArgumentException if a <code>null</code> value was passed either as
     * a parameter or as an element in the <code>GrantedAuthority</code> collection
     */
    @JsonCreator
    public SecurityUser(@JsonProperty("username") String username, @JsonProperty("password") String password, @JsonProperty("tenant") String tenant, @JsonProperty("enabled") boolean enabled, @JsonProperty("accountNonExpired") boolean accountNonExpired,
                        @JsonProperty("credentialsNonExpired") boolean credentialsNonExpired, @JsonProperty("accountNonLocked") boolean accountNonLocked,
                        @JsonProperty("roles") Set<String> roles, @JsonProperty("expire") LocalDateTime expire) {
        Assert.isTrue(username != null && !"".equals(username),
                "Cannot pass null or empty values to constructor");
        this.username = username;
        this.password = password;
        this.tenant = tenant;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.roles = roles;
        this.authorities = Collections.unmodifiableSet(sortAuthorities(roles.stream().map(SimpleGrantedAuthority::new).toList()));
        this.expire = expire;
    }

    @Override
    @JsonIgnore
    public Collection<GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public void eraseCredentials() {
        this.password = null;
    }

    private static SortedSet<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
        // Ensure array iteration order is predictable (as per
        // UserDetails.getAuthorities() contract and SEC-717)
        SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet<>(new SecurityUser.AuthorityComparator());
        for (GrantedAuthority grantedAuthority : authorities) {
            Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");
            sortedAuthorities.add(grantedAuthority);
        }
        return sortedAuthorities;
    }

    /**
     * Returns {@code true} if the supplied object is a {@code User} instance with the
     * same {@code username} value.
     * <p>
     * In other words, the objects are equal if they have the same username, representing
     * the same principal.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SecurityUser user) {
            return this.username.equals(user.getUsername());
        }
        return false;
    }

    /**
     * Returns the hashcode of the {@code username}.
     */
    @Override
    public int hashCode() {
        return this.username.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName()).append(" [");
        sb.append("Username=").append(this.username).append(", ");
        sb.append("Password=[PROTECTED], ");
        sb.append("Enabled=").append(this.enabled).append(", ");
        sb.append("AccountNonExpired=").append(this.accountNonExpired).append(", ");
        sb.append("CredentialsNonExpired=").append(this.credentialsNonExpired).append(", ");
        sb.append("AccountNonLocked=").append(this.accountNonLocked).append(", ");
        sb.append("Granted Authorities=").append(this.authorities).append("]");
        return sb.toString();
    }

    /**
     * Creates a UserBuilder with a specified username
     * @param username the username to use
     * @return the UserBuilder
     */
    public static SecurityUser.UserBuilder withUsername(String username) {

        return builder().username(username);
    }

    /**
     * Creates a UserBuilder
     * @return the UserBuilder
     */
    public static UserBuilder builder() {
        return new UserBuilder();
    }

    /**
     * <p>
     * <b>WARNING:</b> This method is considered unsafe for production and is only
     * intended for sample applications.
     * </p>
     * <p>
     * Creates a user and automatically encodes the provided password using
     * {@code PasswordEncoderFactories.createDelegatingPasswordEncoder()}. For example:
     * </p>
     *
     * <pre>
     * <code>
     * UserDetails user = User.withDefaultPasswordEncoder()
     *     .username("user")
     *     .password("password")
     *     .roles("USER")
     *     .build();
     * // outputs {bcrypt}$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG
     * System.out.println(user.getPassword());
     * </code> </pre>
     *
     * This is not safe for production (it is intended for getting started experience)
     * because the password "password" is compiled into the source code and then is
     * included in memory at the time of creation. This means there are still ways to
     * recover the plain text password making it unsafe. It does provide a slight
     * improvement to using plain text passwords since the UserDetails password is
     * securely hashed. This means if the UserDetails password is accidentally exposed,
     * the password is securely stored.
     *
     * In a production setting, it is recommended to hash the password ahead of time. For
     * example:
     *
     * <pre>
     * <code>
     * PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
     * // outputs {bcrypt}$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG
     * // remember the password that is printed out and use in the next step
     * System.out.println(encoder.encode("password"));
     * </code> </pre>
     *
     * <pre>
     * <code>
     * UserDetails user = User.withUsername("user")
     *     .password("{bcrypt}$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG")
     *     .roles("USER")
     *     .build();
     * </code> </pre>
     * @return a UserBuilder that automatically encodes the password with the default
     * PasswordEncoder
     * @deprecated Using this method is not considered safe for production, but is
     * acceptable for demos and getting started. For production purposes, ensure the
     * password is encoded externally. See the method Javadoc for additional details.
     * There are no plans to remove this support. It is deprecated to indicate that this
     * is considered insecure for production purposes.
     */
    @Deprecated
    public static SecurityUser.UserBuilder withDefaultPasswordEncoder() {
        logger.warn("User.withDefaultPasswordEncoder() is considered unsafe for production "
                + "and is only intended for sample applications.");
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return builder().passwordEncoder(encoder::encode);
    }

    public static SecurityUser.UserBuilder withUserDetails(UserDetails userDetails) {
        // @formatter:off
        return withUsername(userDetails.getUsername())
                .password(userDetails.getPassword())
                .accountExpired(!userDetails.isAccountNonExpired())
                .accountLocked(!userDetails.isAccountNonLocked())
                .authorities(userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .credentialsExpired(!userDetails.isCredentialsNonExpired())
                .disabled(!userDetails.isEnabled());
        // @formatter:on
    }

    @Override
    public String getTenantName() {
        return tenant;
    }

    private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {

        private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

        @Override
        public int compare(GrantedAuthority g1, GrantedAuthority g2) {
            // Neither should ever be null as each entry is checked before adding it to
            // the set. If the authority is null, it is a custom authority and should
            // precede others.
            if (g2.getAuthority() == null) {
                return -1;
            }
            if (g1.getAuthority() == null) {
                return 1;
            }
            return g1.getAuthority().compareTo(g2.getAuthority());
        }

    }

    /**
     * Builds the user to be added. At minimum the username, password, and authorities
     * should provided. The remaining attributes have reasonable defaults.
     */
    public static final class UserBuilder {

        private String username;

        private String password;

        private List<String> roles = new ArrayList<>();

        private boolean accountExpired;

        private boolean accountLocked;

        private boolean credentialsExpired;

        private boolean disabled;

        private String tenant;

        private Function<String, String> passwordEncoder = (password) -> password;

        private LocalDateTime expire;

        /**
         * Creates a new instance
         */
        public UserBuilder() {
        }

        /**
         * Populates the username. This attribute is required.
         * @param username the username. Cannot be null.
         * @return the {@link SecurityUser.UserBuilder} for method chaining (i.e. to populate
         * additional attributes for this user)
         */
        public SecurityUser.UserBuilder username(String username) {
            Assert.notNull(username, "username cannot be null");
            this.username = username;
            return this;
        }

        /**
         * Populates the password. This attribute is required.
         * @param password the password. Cannot be null.
         * @return the {@link SecurityUser.UserBuilder} for method chaining (i.e. to populate
         * additional attributes for this user)
         */
        public SecurityUser.UserBuilder password(String password) {
            Assert.notNull(password, "password cannot be null");
            this.password = password;
            return this;
        }

        public SecurityUser.UserBuilder tenant(String tenant) {
            this.tenant = tenant;
            return this;
        }

        /**
         * Encodes the current password (if non-null) and any future passwords supplied to
         * {@link #password(String)}.
         * @param encoder the encoder to use
         * @return the {@link SecurityUser.UserBuilder} for method chaining (i.e. to populate
         * additional attributes for this user)
         */
        public SecurityUser.UserBuilder passwordEncoder(Function<String, String> encoder) {
            Assert.notNull(encoder, "encoder cannot be null");
            this.passwordEncoder = encoder;
            return this;
        }

        /**
         * Populates the roles. This method is a shortcut for calling
         * "ROLE_". This means the following:
         *
         * <code>
         *     builder.roles("USER","ADMIN");
         * </code>
         *
         * is equivalent to
         *
         * <code>
         *     builder.authorities("ROLE_USER","ROLE_ADMIN");
         * </code>
         *
         * <p>
         * This attribute is required, but can also be populated with
         * </p>
         * @param roles the roles for this user (i.e. USER, ADMIN, etc). Cannot be null,
         * contain null values or start with "ROLE_"
         * @return the {@link SecurityUser.UserBuilder} for method chaining (i.e. to populate
         * additional attributes for this user)
         */
        public SecurityUser.UserBuilder roles(Collection<String> roles) {
            return authorities(roles);
        }

        public SecurityUser.UserBuilder roles(String... roles) {
            return authorities(Arrays.stream(roles).toList());
        }

        /**
         * Populates the authorities. This attribute is required.
         * @param authorities the authorities for this user. Cannot be null, or contain
         * null values
         * @return the {@link SecurityUser.UserBuilder} for method chaining (i.e. to populate
         * additional attributes for this user)
         */
        public SecurityUser.UserBuilder authorities(GrantedAuthority... authorities) {
            Assert.notNull(authorities, "authorities cannot be null");
            return authorities(Arrays.stream(authorities).map(GrantedAuthority::getAuthority).toList());
        }

        /**
         * Populates the authorities. This attribute is required.
         * @param roles the authorities for this user. Cannot be null, or contain
         * null values
         * @return the {@link SecurityUser.UserBuilder} for method chaining (i.e. to populate
         * additional attributes for this user)
         */
        public SecurityUser.UserBuilder authorities(Collection<String> roles) {
            Assert.notNull(roles, "roles cannot be null");
            this.roles = new ArrayList<>(roles);
            return this;
        }

        /**
         * Defines if the account is expired or not. Default is false.
         * @param accountExpired true if the account is expired, false otherwise
         * @return the {@link SecurityUser.UserBuilder} for method chaining (i.e. to populate
         * additional attributes for this user)
         */
        public SecurityUser.UserBuilder accountExpired(boolean accountExpired) {
            this.accountExpired = accountExpired;
            return this;
        }

        /**
         * Defines if the account is locked or not. Default is false.
         * @param accountLocked true if the account is locked, false otherwise
         * @return the {@link SecurityUser.UserBuilder} for method chaining (i.e. to populate
         * additional attributes for this user)
         */
        public SecurityUser.UserBuilder accountLocked(boolean accountLocked) {
            this.accountLocked = accountLocked;
            return this;
        }

        /**
         * Defines if the credentials are expired or not. Default is false.
         * @param credentialsExpired true if the credentials are expired, false otherwise
         * @return the {@link SecurityUser.UserBuilder} for method chaining (i.e. to populate
         * additional attributes for this user)
         */
        public SecurityUser.UserBuilder credentialsExpired(boolean credentialsExpired) {
            this.credentialsExpired = credentialsExpired;
            return this;
        }

        /**
         * Defines if the account is disabled or not. Default is false.
         * @param disabled true if the account is disabled, false otherwise
         * @return the {@link SecurityUser.UserBuilder} for method chaining (i.e. to populate
         * additional attributes for this user)
         */
        public SecurityUser.UserBuilder disabled(boolean disabled) {
            this.disabled = disabled;
            return this;
        }

        public SecurityUser.UserBuilder expire(LocalDateTime expire) {
            this.expire = expire;
            return this;
        }

        public UserDetails build() {
            String encodedPassword = this.passwordEncoder.apply(this.password);
            return new SecurityUser(this.username, encodedPassword, tenant, !this.disabled, !this.accountExpired,
                    !this.credentialsExpired, !this.accountLocked, new HashSet<>(this.roles), expire);
        }

    }
}
