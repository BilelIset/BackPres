package tn.isetsf.presence.webThymeleaf;

import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class Static {
    static String CURRENT_USER;
}
