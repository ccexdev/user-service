package bg.coincraft.userservice.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultDelegate implements DefaultApiDelegate {

    public ResponseEntity<Void> rootGet() {
        return ResponseEntity.ok().build();
    }
}
