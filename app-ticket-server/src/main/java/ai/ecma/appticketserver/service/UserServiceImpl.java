package ai.ecma.appticketserver.service;

import ai.ecma.appticketserver.entity.Role;
import ai.ecma.appticketserver.entity.User;
import ai.ecma.appticketserver.enums.RoleTypeEnum;
import ai.ecma.appticketserver.exception.RestException;
import ai.ecma.appticketserver.payload.*;
import ai.ecma.appticketserver.repository.RoleRepository;
import ai.ecma.appticketserver.repository.UserRepository;
import ai.ecma.appticketserver.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


/*
 * 1. user get all.
 * 2. filter user by role
 * 3. sarch user by columns
 * */

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public ApiResult<?> changePassword(User currentUser, ChangePasswordDto changePasswordDto) {
        if (!changePasswordDto.getNewPassword().equals(changePasswordDto.getPrePassword()))
            throw new RestException("Pre password none match", HttpStatus.CONFLICT);

        boolean matches = passwordEncoder.matches(changePasswordDto.getOldPassword(), currentUser.getPassword());
        if (!matches)
            throw new RestException("Old password wrong", HttpStatus.CONFLICT);

        String encode = passwordEncoder.encode(changePasswordDto.getNewPassword());
        currentUser.setPassword(encode);
        userRepository.save(currentUser);
        return ApiResult.successResponse();
    }

    @Override
    public ApiResult<UserResDto> get(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RestException("User not found!", HttpStatus.NOT_FOUND));
        return ApiResult.successResponse(Mapper.userResDto(user));
    }

    @Override
    public ApiResult<CustomPage<UserResDto>> search(String some, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> byFirstNameOrLastNameOrPhoneNumber = userRepository.getByFirstNameOrLastNameOrPhoneNumber("%" + some + "%", pageable);
        CustomPage<UserResDto> userResDtoCustomPage = mapUserResDto(byFirstNameOrLastNameOrPhoneNumber);
        return ApiResult.successResponse(userResDtoCustomPage);
    }

    @Override
    public ApiResult<CustomPage<UserResDto>> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> all = userRepository.findAll(pageable);
        CustomPage<UserResDto> userResDtoCustomPage = mapUserResDto(all);
        return ApiResult.successResponse(userResDtoCustomPage);
    }

    @Override
    public ApiResult<CustomPage<UserResDto>> getFilterRole(RoleTypeEnum roleTypeEnum, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> allByRole_roleType = userRepository.findAllByRole_RoleType(roleTypeEnum, pageable);
        CustomPage<UserResDto> userResDtoCustomPage = mapUserResDto(allByRole_roleType);
        return ApiResult.successResponse(userResDtoCustomPage);
    }

    @Override
    public ApiResult<UserResDto> add(UserReqDto userReqDto) {
        Optional<User> byPhoneNumber = userRepository.findByPhoneNumber(userReqDto.getPhoneNumber());
        if (byPhoneNumber.isPresent())
            throw new RestException("This phone number is already exists!", HttpStatus.CONFLICT);
        Role role = roleRepository.findById(userReqDto.getRoleId()).orElseThrow(() -> new RestException("Role not found", HttpStatus.NOT_FOUND));
        User user = new User(
                userReqDto.getFirstName(),
                userReqDto.getLastName(),
                userReqDto.getPhoneNumber(),
                userReqDto.isEnabled(),
                role
        );
        userRepository.save(user);
        return ApiResult.successResponse(Mapper.userResDto(user));
    }

    @Override
    public ApiResult<UserResDto> blockOrUnblock(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RestException("User not found!", HttpStatus.NOT_FOUND));
        user.setEnabled(!user.isEnabled());
        userRepository.save(user);
        return ApiResult.successResponse("User blocked!");
    }

    @Override
    public ApiResult<?> delete(UUID id) {
        try {
            userRepository.deleteById(id);
            return ApiResult.successResponse("User deleted!");
        } catch (Exception e) {
            throw new RestException("User not found", HttpStatus.NOT_FOUND);
        }
    }


    private CustomPage<UserResDto> mapUserResDto(Page<User> userPage) {
        CustomPage<UserResDto> customPage = new CustomPage<>(
                userPage.getContent()
                        .stream()
                        .map(Mapper::userResDto)
                        .collect(Collectors.toList()),
                userPage.getNumberOfElements(),
                userPage.getNumber(),
                userPage.getTotalElements(),
                userPage.getTotalPages(),
                userPage.getSize()
        );
        return customPage;
    }

}
