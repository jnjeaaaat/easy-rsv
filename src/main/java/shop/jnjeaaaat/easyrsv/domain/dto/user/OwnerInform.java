package shop.jnjeaaaat.easyrsv.domain.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OwnerInform {
    private String email;
    private String name;
}
