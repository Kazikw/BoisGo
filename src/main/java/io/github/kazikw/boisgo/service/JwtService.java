package io.github.kazikw.boisgo.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.xml.crypto.Data;
import java.nio.charset.StandardCharsets;
import java.util.Date;
@Service
public class JwtService {
    int EXP_TIME = 15*60 * 1000;
    @Value("${security.jwt.secret-key}")
    private String secretKey = "dsafasasdfsdfvergsdgdfbdfgasdgsdfgsdfgsdfgsdf";
    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public String evilGenerateToken(String userName){
        Date now = new Date();
        Date exp = new Date(now.getTime() + EXP_TIME);//1000 bo liczone w ms!
        String key = "gbndsfugisdfbngviusdbnvuidsxnuvsdxchnuivsdnnuidsvc";
        SecretKey temp = Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
        return Jwts.builder().setSubject(userName).setExpiration(new Date())
                .signWith(temp, SignatureAlgorithm.HS256)
                .setIssuedAt(now)
                .setExpiration(exp)
                .compact();
    }

    public String generateToken(String userName){
        Date now = new Date();
        Date exp = new Date(now.getTime() + EXP_TIME);

        return Jwts.builder().setSubject(userName).setExpiration(new Date())
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .setIssuedAt(now)
                .setExpiration(exp)
                .compact();
     }
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    public static void main(String[] args) {

        JwtService service = new JwtService();
        System.out.printf(service.generateToken("fred"));
        String token = service.generateToken("fred");
        System.out.println();
        System.out.printf(service.extractUsername(token));
        System.out.println("Próba podania fakowego tokena:");
        token = service.evilGenerateToken("fred");
        System.out.printf(service.extractUsername(token));

    }
}
