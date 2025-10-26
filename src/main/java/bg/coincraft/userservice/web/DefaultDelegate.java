package bg.coincraft.userservice.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultDelegate implements DefaultApiDelegate {

    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> rootGet() {
        return ResponseEntity.ok().build();
    }
}
