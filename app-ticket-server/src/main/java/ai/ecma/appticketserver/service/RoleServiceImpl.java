package ai.ecma.appticketserver.service;

import ai.ecma.appticketserver.entity.Role;
import ai.ecma.appticketserver.exception.RestException;
import ai.ecma.appticketserver.payload.*;
import ai.ecma.appticketserver.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public ApiResult<CustomPage<RoleResDto>> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Role> all = roleRepository.findAll(pageable);
        CustomPage<RoleResDto> customPage = mapRoleResDtoCustomPage(all);
        return ApiResult.successResponse(customPage);
    }

    @Override
    public ApiResult<RoleResDto> get(UUID id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new RestException("Role not found!", HttpStatus.NOT_FOUND));
        return ApiResult.successResponse(roleResDtoTo(role));
    }

    @Override
    public ApiResult<RoleResDto> add(RoleReqDto roleReqDto) {
        boolean b = roleRepository.existsByName(roleReqDto.getName());
        if (b) throw new RestException("Name already exists!", HttpStatus.CONFLICT);
        Role role = new Role(
                roleReqDto.getName(),
                roleReqDto.getDescription(),
                roleReqDto.getRoleType(),
                roleReqDto.getPermission()
        );
        roleRepository.save(role);
        return ApiResult.successResponse("Success adding!", roleResDtoTo(role));
    }

    @Override
    public ApiResult<RoleResDto> edit(RoleReqDto roleReqDto, UUID id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new RestException("Role not found", HttpStatus.NOT_FOUND));

        boolean b = roleRepository.existsByNameAndIdNot(roleReqDto.getName(), id);

        if (b) throw new RestException("Name already exists!", HttpStatus.CONFLICT);

        role.setName(roleReqDto.getName());
        role.setDescription(roleReqDto.getDescription());
        role.setRoleType(roleReqDto.getRoleType());
        role.setPermission(roleReqDto.getPermission());
        roleRepository.save(role);
        return ApiResult.successResponse("Success editing!", roleResDtoTo(role));

    }

    @Override
    public ApiResult<?> delete(UUID id) {
        try {
            roleRepository.deleteById(id);
            return ApiResult.successResponse("success deleting!");
        } catch (Exception e) {
            throw new RestException("Role not found", HttpStatus.NOT_FOUND);
        }
    }

    public RoleResDto roleResDtoTo(Role role) {
        return new RoleResDto(role.getId(), role.getName(), role.getDescription(), role.getRoleType(), role.getPermission());
    }

    public CustomPage<RoleResDto> mapRoleResDtoCustomPage(Page<Role> rolePage) {
        CustomPage<RoleResDto> customPage = new CustomPage<>(
                rolePage.getContent()
                        .stream()
                        .map(this::roleResDtoTo)
                        .collect(Collectors.toList()),
                rolePage.getNumberOfElements(),
                rolePage.getNumber(),
                rolePage.getTotalElements(),
                rolePage.getTotalPages(),
                rolePage.getSize()
        );
        return customPage;
    }
}
