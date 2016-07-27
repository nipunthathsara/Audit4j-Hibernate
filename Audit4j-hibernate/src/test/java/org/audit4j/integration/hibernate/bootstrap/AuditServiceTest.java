package org.audit4j.integration.hibernate.bootstrap;

import static org.mockito.Mockito.mock;

import java.util.Map;

import org.hibernate.service.spi.Configurable;
import org.junit.Before;
import org.junit.Test;

public class AuditServiceTest {

    private AuditService auditService;

    @Before
    public void setUp() {
        auditService = new AuditServiceImpl();
    }

    @Test
    public void testconfigure() {
        Map mockedConfigurationValues = mock(Map.class);
        Configurable configurable = new AuditServiceImpl();
        configurable.configure(mockedConfigurationValues);
    }
    
    
}
