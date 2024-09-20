package com.sboard.service;

import com.sboard.dto.UserDTO;
import com.sboard.entity.User;
import com.sboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

@Log4j2
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void insertUser(UserDTO userDTO) {
        // 회원가입

        String encoded = passwordEncoder.encode(userDTO.getPass());
        userDTO.setPass(encoded);

        User user = userDTO.toEntity();

        userRepository.save(user);
    }

    public UserDTO selectUser(String uid, String pass) {

        Optional<User> optUser = userRepository.findById(uid);

        if(optUser.isPresent()) {
            User user = optUser.get();
            return user.toDTO();
        }

        return null;

    }

    public User findByUsernameAndPassword(String uid, String pass) {
        return userRepository.findByUidAndPass(uid, pass)
                .orElse(null);
    }


    public String sendEmailCode(String email) {

        // 인증코드 생성
        int code = ThreadLocalRandom.current().nextInt(100000, 1000000);

        // 이메일 기본 정보
        String title = "jboard 인증번호 입니다.";
        String content = "<h1>인증코드는 " + code + "입니다.</h1>";
        String sender = "gkdntn@gmail.com";
        String appPass = "cztl pprm haxl eupx"; // google 앱 비밀번호

        // gmail SMTP 설정
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        // gmail session 생성
        Session gmailSession = Session.getInstance(props, new Authenticator(){

            @Override
            protected javax.mail.PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(sender, appPass);
            }
        });

        // 메일 발송
        Message message = new MimeMessage(gmailSession);

        try{
            message.setFrom(new InternetAddress(sender, "보내는사람", "UTF-8"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject(title);
            message.setContent(content, "text/html;charset=utf-8");

            Transport.send(message);

        }catch(Exception e){
            e.printStackTrace();
        }
        return ""+code;
    }


    public int selectCountUser(String type, String value) {
        int result = 0;

        if(type.equals("uid")){
            result = userRepository.countByUid(value);
        }else if(type.equals("nick")){
            result = userRepository.countByNick(value);
        }else if(type.equals("email")){
            result = userRepository.countByEmail(value);
        }else if(type.equals("hp")){
            result = userRepository.countByHp(value);
        }

        return result;
    }


}
