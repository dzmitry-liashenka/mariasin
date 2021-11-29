package de.mariasin.shop.control.login;

import java.util.Arrays;
import java.util.HashSet;

import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;

@ApplicationScoped
public class CustomInMemoryIdentityStore implements IdentityStore {

    @Override
    public CredentialValidationResult validate(Credential credential) {

        UsernamePasswordCredential login = (UsernamePasswordCredential) credential;

        if (login.getCaller().equals("mitrybatkovich@gmail.com") 
                       && login.getPasswordAsString().equals("Kbuehbqcrjtvjht_")) {
            return new CredentialValidationResult("admin", new HashSet<>(Arrays.asList("ADMIN")));
        } else if (login.getCaller().equals("sin.999.sin@gmail.com") 
                       && login.getPasswordAsString().equals("mashasin")) {
            return new CredentialValidationResult("user", new HashSet<>(Arrays.asList("USER")));
        } else {
            return CredentialValidationResult.NOT_VALIDATED_RESULT;
        }
    }
}
