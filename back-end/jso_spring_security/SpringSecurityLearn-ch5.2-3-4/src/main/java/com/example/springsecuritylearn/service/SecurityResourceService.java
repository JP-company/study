package com.example.springsecuritylearn.service;

import com.example.springsecuritylearn.domain.entity.Resources;
import com.example.springsecuritylearn.repository.ResourcesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SecurityResourceService {

    private final ResourcesRepository resourcesRepository;

    public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getResourceList() {
        LinkedHashMap<RequestMatcher, List<ConfigAttribute>> result = new LinkedHashMap<>();
        List<Resources> resourcesList = resourcesRepository.findAllResources();
        resourcesList.forEach(re -> {
            List<ConfigAttribute> configAttributesList = new ArrayList<>();
            re.getRoleSet().forEach(role -> {
                configAttributesList.add(new SecurityConfig(role.getRoleName()));
            });
            result.put(new AntPathRequestMatcher(re.getResourceName()), configAttributesList);
        });
        return result;
    }
}