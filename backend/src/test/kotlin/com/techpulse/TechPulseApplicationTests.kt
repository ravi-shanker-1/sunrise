package com.techpulse

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class TechPulseApplicationTests {

    @Test
    fun contextLoads() {
        // Verifies the Spring application context loads successfully
    }
}
