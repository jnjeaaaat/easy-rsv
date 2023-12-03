package shop.jnjeaaaat.easyrsv.domain.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 상점을 등록하고 조회할 때
 * 상점 주인이 누군지 같이 뜨게 하기 위한 Owner information class
 */
@Getter
@Setter
@Builder
public class OwnerInform {
    private Long id;
    private String email;
    private String name;
}
