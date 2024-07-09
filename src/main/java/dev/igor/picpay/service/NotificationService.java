package dev.igor.picpay.service;

import dev.igor.picpay.clients.NotificationClient;
import dev.igor.picpay.entity.Transfer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);
    private final NotificationClient notificationClient;

    public NotificationService(NotificationClient notificationClient) {
        this.notificationClient = notificationClient;
    }

    public void sendNotification(Transfer transfer) {
        try {
            log.info("Sending notification...");
            var resp = notificationClient.sendNotification(transfer);
            if (resp.getStatusCode().isError()) {
                log.error("Error sending notification, status code is not OK");
            }
        } catch (Exception e) {
            log.error("Error sending notification", e);
        }
    }
}
