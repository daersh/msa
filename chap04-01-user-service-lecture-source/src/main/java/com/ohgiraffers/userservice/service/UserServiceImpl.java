package com.ohgiraffers.userservice.service;

import com.ohgiraffers.userservice.aggregate.UserEntity;
import com.ohgiraffers.userservice.dto.UserDTO;
import com.ohgiraffers.userservice.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void registUser(UserDTO userDTO) {
        /*유저아이디가 비어있는 상태인데 UUID를 통해 회원의 고유 아이디를 생성한다.*/
        userDTO.setUserId(UUID.randomUUID().toString());
        /*필드값이 정확히 일치할 때만 매칭하기 위해 STRICT 모드 상태로 modelMapper를 설정한다(standard -> strict)*/
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = modelMapper.map(userDTO, UserEntity.class);
//        userEntity.setEncryptedPwd("암호화된비밀번호");
        /*spring security 모듈 추가 후 진행하므로 security 설정도 추가한다.*/
        userEntity.setEncryptedPwd(bCryptPasswordEncoder.encode(userDTO.getPwd()));
        userRepository.save(userEntity);
    }

    /*UserDetailsService 인터페이스 상속 이후 DB 에서 로그인 사용자 정보 조회 후 UserDetails 타입으로 반환하는 기능 구현*/
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity==null){
            throw new UsernameNotFoundException(email+" 아이디 유저는 존재하이 않음");
        }

        return new User(userEntity.getEmail(),userEntity.getEncryptedPwd(),true,true,true,true, new ArrayList<>());  // userDetails를 상속받는 User를 반환해야함
    }
}
