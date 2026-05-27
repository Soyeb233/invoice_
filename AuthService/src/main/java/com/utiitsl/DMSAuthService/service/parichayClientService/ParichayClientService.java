package com.utiitsl.DMSAuthService.service.parichayClientService;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;


import com.utiitsl.DMSAuthService.dto.AuthenticationResponseDTO;
import com.utiitsl.DMSAuthService.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.userdetails.UserDetails;

//import com.uti.dipam.ddaservice.bean.AuthUserdetailResponseBean;
//import com.uti.dipam.ddaservice.bean.User;
//



public interface ParichayClientService {

    String generateCodeVerifier(HttpSession session, HttpServletRequest request) throws Exception;

    String generateCodeChallange(String codeVerifier ) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    String redirectUrlToUI(String authorizationCode,String state);

    AuthenticationResponseDTO userAuthenticationFromParichay(String authorizationCode,String state);

    boolean isAccessTokenValid(Long expiredIn, Date createdAt);

}

