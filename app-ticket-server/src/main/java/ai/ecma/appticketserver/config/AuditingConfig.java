package ai.ecma.appticketserver.config;

import ai.ecma.appticketserver.entity.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
import java.util.UUID;

public class AuditingConfig implements AuditorAware<UUID> {
    @Override
    public Optional<UUID> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication!=null
                && authentication.isAuthenticated()
                && !"anonymousUser".equals(authentication.getPrincipal())){
            User user = (User) authentication.getPrincipal();
            return Optional.of(user.getId());
        }
        return Optional.empty();
    }
}
