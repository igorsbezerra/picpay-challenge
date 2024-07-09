package dev.igor.picpay.service;

import dev.igor.picpay.clients.AuthorizationClient;
import dev.igor.picpay.controller.dto.TransferDto;
import dev.igor.picpay.entity.Transfer;
import dev.igor.picpay.exceptions.PicPayException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {
    private final AuthorizationClient authorizationClient;

    public AuthorizationService(AuthorizationClient authorizationClient) {
        this.authorizationClient = authorizationClient;
    }

    public boolean isAuthorized(TransferDto transfer) {
        var resp = authorizationClient.authorize();
        if (resp.getStatusCode().isError()) {
            throw new PicPayException();
        }
        return resp.getBody().authorized();
    }
}
