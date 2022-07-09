package yehor.epam.cinema_final_project_spring.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import yehor.epam.cinema_final_project_spring.entities.User;
import yehor.epam.cinema_final_project_spring.entities.UserRole;

import java.util.Collection;
import java.util.List;

/**
 * User details for Spring Security
 */
public class CustomUserDetails implements UserDetails {
    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        UserRole role = user.getUserRole();
        final SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
        return List.of(authority);
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    // Is not used, so set as true
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // Is not used, so set as true
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // Is not used, so set as true
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // Is not used, so set as true
    @Override
    public boolean isEnabled() {
        return true;
    }
}
