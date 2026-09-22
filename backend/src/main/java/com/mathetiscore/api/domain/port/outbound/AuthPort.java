package com.mathetiscore.api.domain.port.outbound;

import java.util.UUID;

public interface AuthPort {

    UUID registerUserInAuth(String email, String Password);
}
