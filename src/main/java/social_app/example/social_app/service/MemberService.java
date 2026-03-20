package social_app.example.social_app.service;

import social_app.example.social_app.entity.Members;

import java.util.Optional;

public interface MemberService {
    public boolean isExist(Integer id);
    public Optional<Members> getMemberById(Integer id);
    public Optional<Members> getMemberByFullName(String fullName);
}
