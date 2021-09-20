import java.util.Locale;

import io.github.cloudgyb.springsecurity02.SpringSecurity02Application;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;

/**
 * i18n 测试
 *
 * @author cloudgyb
 * 2021/9/19 21:42
 */
@SpringBootTest(classes = SpringSecurity02Application.class)
public class TestI18n {

    @Autowired
    private MessageSource messageSource;

    @Test
    public void test(){
        final String test = messageSource.getMessage("test", null, Locale.getDefault());
        System.out.println(test);
    }

    @Test
    public void test1(){
        final String test = messageSource.getMessage("test1", new Object[]{"111"}, Locale.getDefault());
        System.out.println(test);
    }
}
