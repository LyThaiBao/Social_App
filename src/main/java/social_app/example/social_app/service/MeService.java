package social_app.example.social_app.service;

import social_app.example.social_app.dto.MeResponse;

import java.security.Principal;

public interface MeService {
    MeResponse getMe(Principal principal);
}
