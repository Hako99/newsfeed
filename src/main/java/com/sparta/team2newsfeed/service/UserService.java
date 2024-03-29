package com.sparta.team2newsfeed.service;

import com.sparta.team2newsfeed.dto.UserRequestDto;
import com.sparta.team2newsfeed.dto.UserUpdateRequestDto;
import com.sparta.team2newsfeed.entity.User;
import com.sparta.team2newsfeed.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.RequestEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    //Repository 주입
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    //회원가입
    public void userSignup(UserRequestDto userRequestDto) {
        String username = userRequestDto.getUsername();
        String password = passwordEncoder.encode(userRequestDto.getPassword());

        //db 에 존재하는지 확인하기
        if(userRepository.findByUsername(username).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 유저 입니다.");
        }

        User user = new User(username,userRequestDto.getName(),password,userRequestDto.getEmail(),userRequestDto.getIntro());
        userRepository.save(user);
    }



    //로그인
    public RequestEntity<?> userLogin(UserRequestDto userRequestDto) {
        String username = userRequestDto.getUsername();
        String password = userRequestDto.getPassword();

        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new IllegalArgumentException("등록된 사용자가 없습니다."));

        if(!passwordEncoder.matches(password,user.getPassword()));{
            // 암호화 안된게 앞, 된게 뒤에 들어가야함.
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    //회원수정
    public void userUpdate(UserUpdateRequestDto userUpdateRequestDto,User user) {
        if(user.getId() == null ){
            throw new IllegalArgumentException("로그인 유저 정보가 없음");
        }
        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 유저 입니다.");
        }
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new IllegalArgumentException("이미 가입된 Email 입니다.");
        }

        User userUp = userRepository
                .findById(user.getId())
                .orElseThrow(()->new RuntimeException("로그인 유저 정보가 없음"));

        user.userUpdate(userUpdateRequestDto,passwordEncoder);
        userRepository.save(userUp);
    }
}
