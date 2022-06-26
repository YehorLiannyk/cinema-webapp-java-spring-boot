package yehor.epam.cinema_final_project_spring.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import yehor.epam.cinema_final_project_spring.entities.User;
import yehor.epam.cinema_final_project_spring.entities.UserRole;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static yehor.epam.cinema_final_project_spring.utils.constants.Constants.USER_ROLE;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsTest {

    @Mock
    private User user;

    @Mock
    private CustomUserDetails customUserDetails;

    @BeforeEach
    public void setup() {
        customUserDetails = new CustomUserDetails(user);
    }

    @Test
    void getAuthorities() {
        UserRole userRole = mock(UserRole.class);
        given(user.getUserRole()).willReturn(userRole);
        given(userRole.getName()).willReturn(USER_ROLE);
        final Collection<? extends GrantedAuthority> authorities = customUserDetails.getAuthorities();
        assertThat(authorities).isNotNull().isNotEmpty();
    }

    @Test
    void getPassword() {
        assertThat(customUserDetails.getPassword()).isEqualTo(user.getPassword());
    }

    @Test
    void getUsername() {
        assertThat(customUserDetails.getUsername()).isEqualTo(user.getEmail());
    }

    @Test
    void isAccountNonExpired() {
        assertThat(customUserDetails.isAccountNonExpired()).isTrue();
    }

    @Test
    void isAccountNonLocked() {
        assertThat(customUserDetails.isAccountNonLocked()).isTrue();

    }

    @Test
    void isCredentialsNonExpired() {
        assertThat(customUserDetails.isCredentialsNonExpired()).isTrue();

    }

    @Test
    void isEnabled() {
        assertThat(customUserDetails.isEnabled()).isTrue();

    }
}