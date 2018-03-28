package user;


import menu.extras.ScannerUntils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ScannerUntils.class})
public class LoginTest {
    @Test
    public void testLogin() {
        Login login = new Login(); // MyClass is tested
        login.loginToSystem();
        //("Call mockStatic AccountManager.class to enable static mocking");
        PowerMockito.mockStatic(ScannerUntils.class);

        //("Stub static method AccountManager.getSummary");
        PowerMockito.when(ScannerUntils.scanString("username"))
                .thenReturn("10");

        // assert statements
        //login.loginToSystem();
        //Assert.assertEquals("admin","admin");
    }
}

