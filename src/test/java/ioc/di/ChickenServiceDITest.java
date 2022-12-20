package ioc.di;

import ioc.di.domain.Chicken;
import ioc.di.domain.ChickenGender;
import ioc.di.ioc.IOCContainer;
import ioc.di.service.ChickenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ChickenServiceDITest {

    @DisplayName("Service 클래스의 Repository 필드 주입 여부를 확인한다.")
    @Test
     void diTest() {

        final var chicken = new Chicken("chicken", 100000, ChickenGender.MALE);
        final var iocContainer = createDIContainer();
        final var chickenService = iocContainer.getBean(ChickenService.class);

        final var actual = chickenService.addChicken(chicken);

        assertThat(actual.getName()).isEqualTo("chicken");
    }

    private static IOCContainer createDIContainer() {
        final var rootPackageName = ChickenServiceDITest.class.getPackage().getName();
        return IOCContainer.createContainerForPackage(rootPackageName);
    }
}