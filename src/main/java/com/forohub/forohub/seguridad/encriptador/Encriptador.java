package com.forohub.forohub.seguridad.encriptador;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Encriptador implements EncriptadorInterfaz {
    private final BCryptPasswordEncoder BCRYPT_CLAVE;

    public Encriptador() {
        this.BCRYPT_CLAVE = new BCryptPasswordEncoder();
    }

    @Override
    public String bcrypt(String clave) {
        return BCRYPT_CLAVE.encode(clave);
    }
}