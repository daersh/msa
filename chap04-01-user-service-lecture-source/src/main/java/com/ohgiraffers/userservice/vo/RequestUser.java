package com.ohgiraffers.userservice.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/*회원가입을 위해 넘어온 사용자 정보를 받아 줄 Command 객체용 VO 생성 */
@Data       // 이건 개발때만 하고 실제로 적용할 때는 제외
public class RequestUser {
    private String name;  //이름
    private String email; //이메일
    private String pwd;   //비밀번호

}
