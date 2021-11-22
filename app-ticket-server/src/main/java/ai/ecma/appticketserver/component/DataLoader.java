package ai.ecma.appticketserver.component;

import ai.ecma.appticketserver.entity.Role;
import ai.ecma.appticketserver.entity.User;
import ai.ecma.appticketserver.enums.PermissionEnum;
import ai.ecma.appticketserver.enums.RoleTypeEnum;
import ai.ecma.appticketserver.exception.RestException;
import ai.ecma.appticketserver.repository.RoleRepository;
import ai.ecma.appticketserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class DataLoader implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;


    @Value("${spring.sql.init.mode}")
    private String mode;

    public DataLoader(RoleRepository roleRepository, @Lazy PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (mode.equals("always")) {
            List<Role> roles = new ArrayList<>();
            for (RoleTypeEnum roleTypeEnum : RoleTypeEnum.values()) {
                Role role = new Role(
                        roleTypeEnum.equals(RoleTypeEnum.STAFF) ? "yordamchi" : "mijoz",
                        "",
                        roleTypeEnum,
                        getPermissionByRoleTypeEnum(roleTypeEnum)
                );
                roles.add(role);
            }
            Role adminRole = new Role(
                    "admin",
                    "adminjon",
                    RoleTypeEnum.STAFF,
                    new HashSet<>(Arrays.asList(PermissionEnum.values()))
            );
            roleRepository.save(adminRole);
            roleRepository.saveAll(roles);
            User adminUser = new User(
                    "admin",
                    "admin",
                    "998991234567",
                    passwordEncoder.encode("admin"),
                    true,
                    adminRole
            );

            userRepository.save(adminUser);


        }
//        Role role = new Role(
//                "PAYME",
//                "vghvg",
//                RoleTypeEnum.STAFF,
//                Arrays.stream(PermissionEnum.values()).collect(Collectors.toSet())
//        );
//        roleRepository.save(role);
//
//        User user = new User(
//                "payme",
//                "payme",
//                "1234567",
//                true,
//                role
//        );
//        userRepository.save(user);
    }

    private Set<PermissionEnum> getPermissionByRoleTypeEnum(RoleTypeEnum roleTypeEnum) {
        return Arrays.stream(PermissionEnum.values())
                .filter(permissionEnum -> permissionEnum.getRoleType().equals(roleTypeEnum))
                .collect(Collectors.toSet());
    }
}
