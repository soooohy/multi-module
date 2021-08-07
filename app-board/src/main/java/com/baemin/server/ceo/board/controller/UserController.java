package com.baemin.server.ceo.board.controller;

import com.baemin.server.ceo.board.dto.UserDto;
import com.baemin.server.ceo.board.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "/board/users" )
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger( UserController.class );

    @Autowired
    private UserService userService;

    @RequestMapping( method = RequestMethod.POST )
    public ResponseEntity signIn( @RequestBody final UserDto.addReq req ) {

        logger.info( "method: POST, api: /board/users, auth: {}, name: {}, email: {}", req.getAuth(), req.getName(), req.getEmail() );

        return userService.signIn( req );
    }

    @RequestMapping( method = RequestMethod.GET )
    public ResponseEntity getUsers() {

        logger.info( "method: GET, api: /board/users" );

        return userService.getUsers();
    }
}
