package com.gamecrew.gamecrew_project.domain.user.service;

import com.gamecrew.gamecrew_project.domain.user.dto.request.SignupRequestDto;
import com.gamecrew.gamecrew_project.domain.user.entity.Auth;
import com.gamecrew.gamecrew_project.domain.user.entity.User;
import com.gamecrew.gamecrew_project.domain.user.repository.AuthRepository;
import com.gamecrew.gamecrew_project.domain.user.repository.UserRepository;
import com.gamecrew.gamecrew_project.global.exception.CustomException;
import com.gamecrew.gamecrew_project.global.exception.constant.ErrorMessage;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.Random;


@Service
@RequiredArgsConstructor
@PropertySource("classpath:application.properties")
@Slf4j
public class SignUpService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;
    private final AuthRepository authRepository;

    public void signup(SignupRequestDto requestDto){
        String email = requestDto.getEmail();
        String nickname = requestDto.getNickname();
        String password = passwordEncoder.encode(requestDto.getPassword());

        Optional<User> checkNickname = userRepository.findByNickname(nickname);
        if (checkNickname.isPresent()) {
            throw new CustomException(ErrorMessage.DUPLICATE_NICKNAME_EXISTS, HttpStatus.CONFLICT);
        }

        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {

            throw new CustomException(ErrorMessage.DUPLICATE_EMAIL_EXISTS, HttpStatus.CONFLICT);
        }

        //사용자 등록
        User user = new User(email, nickname, password, null);
        userRepository.save(user);
    }

    public void checkNickname(String nickname) {
        Optional<User> checkNickname = userRepository.findByNickname(nickname);
        if (checkNickname.isPresent()) {
            throw new CustomException(ErrorMessage.DUPLICATE_NICKNAME_EXISTS, HttpStatus.CONFLICT);
        }
    }

    public String checkAndSendEmail(String email) {
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new CustomException(ErrorMessage.DUPLICATE_EMAIL_EXISTS, HttpStatus.CONFLICT);
        }

        // 중복되는 이메일이 없으면 인증 코드 발송
        try{
            return sendSimpleMessage(email);
        }catch(Exception e){
            throw new CustomException(ErrorMessage.FAILED_TO_SEND_EMAIL, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void verifyCode(String email, String code) {
        if (email == null || email.isEmpty() || code == null || code.isEmpty()) {
            throw new  CustomException(ErrorMessage.INVALID_INPUT, HttpStatus.BAD_REQUEST);
        }

        Optional<Auth> checkEmailAndCode = authRepository.findByEmailAndCode(email,code);
        if (!(checkEmailAndCode.isPresent())) {
            throw  new CustomException(ErrorMessage.CODE_MISMATCH, HttpStatus.BAD_REQUEST);
        }
    }



    //이메일 인증 관련 service
    @Value("${spring.mail.username}")
    private String id;

    public MimeMessage createMessage(String to, String ePw)throws MessagingException, UnsupportedEncodingException {
        log.info("보내는 대상 : "+ to);
        log.info("인증 번호 : " + ePw);
        MimeMessage  message = javaMailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, to); // to 보내는 대상
        message.setSubject("Game_Crew 회원가입 인증 코드: "); //메일 제목

        // 메일 내용 메일의 subtype을 html로 지정하여 html문법 사용 가능
        String msg="안녕하세요?";
        msg += "<h1 style=\"font-size: 30px; padding-right: 30px; padding-left: 30px;\">이메일 주소 확인</h1>";
        msg += "<p style=\"font-size: 17px; padding-right: 30px; padding-left: 30px;\">아래 확인 코드를 회원가입 화면에서 입력해주세요.</p>";
        msg += "<div style=\"padding-right: 30px; padding-left: 30px; margin: 32px 0 40px;\"><table style=\"border-collapse: collapse; border: 0; background-color: #F4F4F4; height: 70px; table-layout: fixed; word-wrap: break-word; border-radius: 6px;\"><tbody><tr><td style=\"text-align: center; vertical-align: middle; font-size: 30px;\">";
        msg += ePw;
        msg += "</td></tr></tbody></table></div>";

        message.setText(msg, "utf-8", "html"); //내용, charset타입, subtype
        message.setFrom(new InternetAddress(id,"Game_Crew")); //보내는 사람의 메일 주소, 보내는 사람 이름
        return message;
    }

    // 인증코드 만들기
    public static String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 6; i++) { // 인증코드 6자리
            key.append((rnd.nextInt(10)));
        }
        return key.toString();
    }

    public String sendSimpleMessage(String to)throws Exception {
        String ePw = createKey();
        MimeMessage message = createMessage(to, ePw);
        try{
            javaMailSender.send(message); // 메일 발송
        }catch(MailException es){
            es.printStackTrace();
            throw new CustomException(ErrorMessage.FAILED_TO_SEND_EMAIL, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ePw; // 메일로 보냈던 인증 코드를 서버로 리턴
    }
}
