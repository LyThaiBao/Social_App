package social_app.example.social_app.service.me;

import social_app.example.social_app.dto.usrAndMember.MeResponse;

import java.security.Principal;

public interface MeService {
    MeResponse getMe(Principal principal);
}
