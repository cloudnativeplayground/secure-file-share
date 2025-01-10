package com.securefileshare;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SecureFileShareApplicationTest {

    @Test
    public void contextLoads() {
        // This test simply verifies that the Spring application context loads successfully.
        // If there are any issues with the context (like missing beans or misconfigurations), the test will fail.
    }

    @Test
    public void applicationStartsSuccessfully() {
        // This test verifies that the application starts up successfully.
        // By running the application and checking for no exceptions, we ensure that the basic application setup is correct.
        SecureFileShareApplication.main(new String[] {});
    }
}

