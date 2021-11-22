package ai.ecma.appticketserver.service;

import ai.ecma.appticketserver.entity.Artist;
import ai.ecma.appticketserver.entity.Role;
import ai.ecma.appticketserver.entity.User;
import ai.ecma.appticketserver.enums.PermissionEnum;
import ai.ecma.appticketserver.enums.RoleTypeEnum;
import ai.ecma.appticketserver.payload.ApiResult;
import ai.ecma.appticketserver.payload.CustomPage;
import ai.ecma.appticketserver.payload.RoleResDto;
import ai.ecma.appticketserver.repository.ArtistRepository;
import ai.ecma.appticketserver.repository.AttachmentRepository;
import ai.ecma.appticketserver.repository.RoleRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.mockito.Mockito.when;

public class RoleServiceTest {
    @InjectMocks
    RoleServiceImpl roleService;

    @Mock
    RoleRepository roleRepository;


    @Before
    public void configur() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getRoleList() {
        List<Role> roles = new ArrayList<>();
        Set<PermissionEnum> permissionEnums = new HashSet<>(Arrays.asList(PermissionEnum.ADD_ARTIST, PermissionEnum.ADD_BRON));
        roles.add(new Role("Bahrom", "Admin", RoleTypeEnum.STAFF, permissionEnums));
        roles.add(new Role("Bahrom", "Admin", RoleTypeEnum.STAFF, permissionEnums));
        roles.add(new Role("Bahrom", "Admin", RoleTypeEnum.STAFF, permissionEnums));
        roles.add(new Role("Bahrom", "Admin", RoleTypeEnum.STAFF, permissionEnums));
        Pageable pageable = PageRequest.of(1, 4);
        Page<Role> all = roleRepository.findAll(pageable);
        CustomPage<RoleResDto> customPage = roleService.mapRoleResDtoCustomPage((Page<Role>) roles);
        when(roleService.mapRoleResDtoCustomPage(all)).thenReturn(customPage);
        ApiResult<CustomPage<RoleResDto>> apiResult = roleService.getAll(1, 4);

        Assertions.assertThat(apiResult.getData()).isNotNull();
        Assertions.assertThat(apiResult.getMessage()).isNull();
    }

    @Test
    public void getById() {
        Set<PermissionEnum> permissionEnums = new HashSet<>(Arrays.asList(PermissionEnum.ADD_ARTIST, PermissionEnum.ADD_BRON));
        Role role = new Role("Bahrom", "Admin", RoleTypeEnum.STAFF, permissionEnums);
        UUID a = UUID.fromString("718dc79-2e48-4da0-a277-3ebfdb62b544");
        role.setId(a);

        when(roleRepository.findById(a)).thenReturn(Optional.of(role));
        ApiResult<RoleResDto> apiResult = roleService.get(a);
        Assertions.assertThat(apiResult.getData()).isNotNull();
        Assertions.assertThat(apiResult.getMessage()).isNull();
        Assertions.assertThat(apiResult.getData().getName()).isEqualTo("Bahrom");
    }
}
