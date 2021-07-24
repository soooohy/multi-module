package com.baemin.server.ceo.service;

import com.baemin.server.ceo.util.RestResponse;
import entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import repository.UserRepository;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<UserEntity> get(final UserEntity userEntity, final HttpServletResponse response) {

        final String id = userEntity.getId();
        if (ObjectUtils.isEmpty(id)) {
            logger.error("id is required");
            return RestResponse.fail(HttpStatus.BAD_REQUEST, "아이디를 입력햊세요.");
        }

        final String password = userEntity.getPassword();
        if (ObjectUtils.isEmpty(password)) {
            logger.error("password is required");
            return RestResponse.fail(HttpStatus.BAD_REQUEST, "비밀번를 입력햊세요.");
        }

        Optional<UserEntity> getUser;
        try {
            getUser = userRepository.findUserByIdAndPassword(id, password);
        } catch (Exception e) {
            logger.error("User findUserByIdAndPassword error : {}, id: {}", e.getMessage(), id);
            return RestResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR, e.getCause().getMessage());
        }

        if (getUser == null) {
            logger.error("User not found - id: {}", id);
            return RestResponse.fail(HttpStatus.NO_CONTENT, "사용자를 찾을 수 없습니다.");
        }

        String token = null;
        try {
            token = tokenService.createToken(userEntity);
        } catch (Exception e) {
            System.out.println();
            logger.error("create token - user: {}, error: {}", userEntity, e.getMessage());
            return RestResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR, e.getCause().getMessage());
        }

        if (token == null) {
            logger.error("created token is null - id: {}", id);
            return RestResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR, "토큰 생성이 실패하였습니다.");
        }

        response.setHeader("jwt-auth-token", token);

        return RestResponse.success(getUser);
    }
}