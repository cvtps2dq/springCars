package ru.cv2.springcars.components;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.cv2.springcars.mapping.MappingUtility;
import ru.cv2.springcars.models.*;
import ru.cv2.springcars.models.dto.BrandDTO;
import ru.cv2.springcars.models.enums.Category;
import ru.cv2.springcars.models.enums.Engine;
import ru.cv2.springcars.models.enums.Role;
import ru.cv2.springcars.models.enums.Transmission;
import ru.cv2.springcars.services.implementation.BrandService;
import ru.cv2.springcars.services.implementation.OfferService;
import ru.cv2.springcars.services.implementation.UserRoleService;
import ru.cv2.springcars.services.implementation.UserService;

import java.util.List;
import java.util.Random;
@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final Faker faker;
    private final BrandService brandService;
    private final OfferService offerService;
    private final UserRoleService userRoleService;
    private final UserService userService;
    private final MappingUtility mappingUtility;
    @Autowired
    public CommandLineRunnerImpl(Faker faker, BrandService brandService, OfferService offerService, UserRoleService userRoleService, UserService userService, MappingUtility mappingUtility) {
        this.faker = faker;
        this.brandService = brandService;
        this.offerService = offerService;
        this.userRoleService = userRoleService;
        this.userService = userService;
        this.mappingUtility = mappingUtility;
    }

    @Override
    public void run(String... args) throws Exception {

        UserRole userRoleUser = new UserRole();
        userRoleUser.setRole(Role.USER);
        userRoleService.create(userRoleUser);
        UserRole userRoleAdmin = new UserRole();
        userRoleAdmin.setRole(Role.ADMIN);
        userRoleService.create(userRoleAdmin);

       for (int i = 0; i < 100; i++) {
            User user = new User();
            user.setUsername(faker.name().username());
            user.setFirstName(faker.name().firstName());
            user.setLastName(faker.name().lastName());
            user.setImageUrl(faker.internet().url());
            user.setPassword(faker.internet().password());
            user.setIsActive(true);
            Random random = new Random();
            boolean choice = random.nextBoolean();
           if (!(choice)) {
               user.setRole(userRoleAdmin);
           } else{
               user.setRole(userRoleUser);
           }
            userService.create(user);
        }

        for (int i = 0; i < 20; i++) {
            Random random = new Random();
            Brand brand = new Brand();
            brand.setName(faker.company().name());
            for (int j = 0; j < 5; j++) {
                String modelName = faker.rockBand().name();
                Category category = Category.values()[random.nextInt(Category.values().length)];
                String imageUrl = faker.internet().avatar();
                int startYear = 1886 + random.nextInt(135);
                int endYear = startYear + random.nextInt(2023 - startYear);

                Model model = new Model();
                model.setName(modelName);
                model.setCategory(category);
                model.setImageUrl(imageUrl);
                model.setStartYear(startYear);
                model.setEndYear(endYear);
                model.setBrand(brand);
                brand.setModels(List.of(model));

                brandService.create(brand);

                Offer offer = new Offer();
                offer.setDescription(faker.lorem().characters(20, 255));
                offer.setEngine(Engine.values()[random.nextInt(Engine.values().length)]);
                offer.setMileage(random.nextInt(300000));
                offer.setPrice(faker.number().randomDouble(2, 1000, 100000));
                offer.setTransmission(Transmission.values()[random.nextInt(Transmission.values().length)]);
                offer.setYear(faker.number().numberBetween(1886, 2022));
                offer.setModel(model);
                offer.setSeller(mappingUtility.convertToEntity(userService.getAll().get(random.nextInt(userService.getAll().size()))));
                offer.setImageUrl(imageUrl);

                offerService.create(offer);
            }
        }

        User cv2 = new User();
        cv2.setFirstName("cv");
        cv2.setLastName("2");
        cv2.setPassword("$2a$12$CMGSdNekMQqAAjx1JbE44OtvrCMpUo3bxscwDP78n0qzky6gYH73S");
        cv2.setRole(userRoleAdmin);
        cv2.setUsername("cv2");
        cv2.setIsActive(true);
        cv2.setImageUrl("example.org");

        userService.create(cv2);
    }
    }

