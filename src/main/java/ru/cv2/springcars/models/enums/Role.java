package ru.cv2.springcars.models.enums;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
public enum Role {
    USER("USER", Set.of(Permission.USER_WRITE, Permission.USER_WATCH)),
    SELLER("SELLER", Set.of(Permission.SELLER_WRITE, Permission.SELLER_WATCH,
            Permission.USER_WRITE, Permission.USER_WATCH)),
    ADMIN("ADMIN", Set.of(Permission.ADMIN_WRITE, Permission.ADMIN_WATCH,
            Permission.SELLER_WRITE, Permission.SELLER_WATCH,
            Permission.USER_WRITE, Permission.USER_WATCH));

    final String name;
    final Set<Permission> permissions;

    Role(String name, Set<Permission> permissions) {
        this.name = name;
        this.permissions = permissions;
    }

//    public static Role fromCode(int code) {
//        for (Role role : values()) {
//            if (role.name == code) {
//                return role;
//            }
//        }
//        throw new IllegalArgumentException("No such role with code: " + code);
//    }

    public Set<SimpleGrantedAuthority> getAuthorities(){
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
