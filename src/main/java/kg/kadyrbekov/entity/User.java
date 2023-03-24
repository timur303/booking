package kg.kadyrbekov.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kg.kadyrbekov.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private int age;

    private Long phoneNumber;

    @Email
    private String email;

    private String password;

    private String avatar;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(cascade = ALL, mappedBy = "user")
    @JsonIgnore
    private List<Club> clubs;

    @OneToMany(fetch = FetchType.LAZY, cascade = ALL, mappedBy = "user")
    @JsonIgnore
    private List<Cabin> cabins;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> grantedAuthorities = new LinkedList<>();

        grantedAuthorities.add(new SimpleGrantedAuthority(role.getAuthority()));

        return grantedAuthorities;
    }


    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
