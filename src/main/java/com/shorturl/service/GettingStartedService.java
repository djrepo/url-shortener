package com.shorturl.service;

import com.shorturl.model.Member;
import com.shorturl.repository.MemberRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

@RequestScoped
public class GettingStartedService {


    @Inject
    private MemberRepository memberRepository;

    public String getAll() {
        List<Member> allOrderedByName = memberRepository.findAllOrderedByName();
        StringBuilder sb = new StringBuilder();
        for (Member member : allOrderedByName) {
            sb.append(member.getId()+" "+member.getName());
        }
        return sb.toString();
    }

    public String hello(long id) {
        Member member = memberRepository.findById(id);

        return String.format("Hello '%s'.", member.getName());
    }
}
