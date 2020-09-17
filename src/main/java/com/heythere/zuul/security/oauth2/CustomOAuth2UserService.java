package com.heythere.zuul.security.oauth2;

import com.heythere.zuul.security.AuthUser;
import com.heythere.zuul.security.exception.OAuth2AuthenticationProcessingException;
import com.heythere.zuul.security.oauth2.provider.OAuth2UserInfoFactory;
import com.heythere.zuul.security.oauth2.provider.OAuth2UserInformation;
import com.heythere.zuul.user.model.AuthProvider;
import com.heythere.zuul.user.model.User;
import com.heythere.zuul.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(final OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause());
        }
    }

    private OAuth2User processOAuth2User(final OAuth2UserRequest oAuth2UserRequest,
                                         final OAuth2User oAuth2User) {
        OAuth2UserInformation oAuth2UserInformation = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        if(StringUtils.isEmpty(oAuth2UserInformation.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }

        Optional<User> userOptional = userRepository.findByEmail(oAuth2UserInformation.getEmail());
        User user;
        if(userOptional.isPresent()) {
            user = userOptional.get();
            if(!user.getProvider().equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
                throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
                        user.getProvider() + " account. Please use your " + user.getProvider() +
                        " account to login.");
            }
            user = updateExistingUser(user, oAuth2UserInformation);
        } else {
            user = registerNewUser(oAuth2UserRequest, oAuth2UserInformation);
        }

        return AuthUser.create(user, oAuth2User.getAttributes());
    }

    private User registerNewUser(final OAuth2UserRequest oAuth2UserRequest,
                                 final OAuth2UserInformation oAuth2UserInformation) {
        return userRepository.save(
                User.builder()
                .email(oAuth2UserInformation.getEmail())
                .name(oAuth2UserInformation.getName())
                .imageUrl(oAuth2UserInformation.getImageUrl())
                .provider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))
                .providerId(oAuth2UserInformation.getId())
                .build()
        );
    }

    private User updateExistingUser(final User existingUser,
                                    final OAuth2UserInformation oAuth2UserInformation) {
        return userRepository.save(
                User.builder()
                .name(oAuth2UserInformation.getName())
                .imageUrl(oAuth2UserInformation.getImageUrl())
                .build()
        );
    }

}
