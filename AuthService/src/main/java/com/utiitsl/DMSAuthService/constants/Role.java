package com.utiitsl.DMSAuthService.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.utiitsl.DMSAuthService.constants.Permission.*;


@RequiredArgsConstructor
public enum Role {

//     SUPREMEADMIN,
//     SUPERADMIN,
//     ADMIN,
//     USER,

     APPLICATION_ADMIN(
             Set.of(
                     APPLICATION_ADMIN_READ,
                     APPLICATION_ADMIN_CREATE,
                     APPLICATION_ADMIN_UPDATE,
                     APPLICATION_ADMIN_DELETE,

                     SUPREMEADMIN_READ,
                     SUPREMEADMIN_CREATE,
                     SUPREMEADMIN_UPDATE,
                     SUPREMEADMIN_DELETE,

                     SUPERADMIN_READ,
                     SUPERADMIN_CREATE,
                     SUPERADMIN_UPDATE,
                     SUPERADMIN_DELETE,

                     ADMIN_READ,
                     ADMIN_CREATE,
                     ADMIN_UPDATE,
                     ADMIN_DELETE,

                     USER_READ,
                     USER_CREATE,
                     USER_UPDATE,
                     USER_DELETE
             )
     ),


     SUPREMEADMIN(
             Set.of(
                     SUPREMEADMIN_READ,
                     SUPREMEADMIN_CREATE,
                     SUPREMEADMIN_UPDATE,
                     SUPREMEADMIN_DELETE,

                     SUPERADMIN_READ,
                     SUPERADMIN_CREATE,
                     SUPERADMIN_UPDATE,
                     SUPERADMIN_DELETE,

                     ADMIN_READ,
                     ADMIN_CREATE,
                     ADMIN_UPDATE,
                     ADMIN_DELETE
             )
     ),

     SUPERADMIN(
             Set.of(
                     SUPERADMIN_READ,
                     SUPERADMIN_CREATE,
                     SUPERADMIN_UPDATE,
                     SUPERADMIN_DELETE,

                     ADMIN_READ,
                     ADMIN_CREATE,
                     ADMIN_UPDATE,
                     ADMIN_DELETE
             )
     ),


     ADMIN(
             Set.of(
                     ADMIN_READ,
                     ADMIN_CREATE, 
                     ADMIN_UPDATE,
                     ADMIN_DELETE
             )
     ),

    DATAHANDLER(
            Set.of(
                    DATAHANDLER_READ

            )
    ),


    APPROVER(
            Set.of(
                    APPROVER_READ,
                    APPROVER_UPDATE

            )
    ),


     USER(
             Set.of(
                     USER_READ
//                     USER_CREATE
             )
     );

     @Getter
     private final Set<Permission> permissions;

     public List<SimpleGrantedAuthority> getAuthorities() {
          var authorities = getPermissions()
                  .stream()
                  .map(authority -> new SimpleGrantedAuthority(authority.getPermission()))
                  .collect(Collectors.toList());
          authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));


          getPermissions()
                  .stream().forEach(e->System.out.println(e.getPermission()));

          return authorities;
     }
}
