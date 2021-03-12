package tests;


import com.github.javafaker.Faker;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.io.File;
import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;


public class StudentRegistrationFormPositive extends TestBase {


    @Test
    @Tag("positive")
    void fieldTheFormWFakeTest() {
        Faker fake = new Faker();

        String firstName = fake.name().firstName(),
                lastName = fake.name().lastName(),
                email = fake.internet().emailAddress(),
                gender = "Male",
                month = "October",
                year = "1989",
                mobileNumber = fake.phoneNumber().subscriberNumber(10),
                subjects = "English",
                address = fake.address().fullAddress(),
                state = "Rajasthan",
                city = "Jaiselmer",
                sports = "Sports",
                reading = "Reading";

        step("Field the form", () -> {
            open("https://demoqa.com/automation-practice-form");
            $("#firstName").setValue(firstName);
            $("#lastName").setValue(lastName);
            $("#userEmail").setValue(email);
            $(byText(gender)).click();
            $("#userNumber").setValue(mobileNumber);
            // date of Birth !!! Calendar pick 15/10/89
            $("#dateOfBirthInput").click();
            $(".react-datepicker__month-select").selectOption(month);
            $(".react-datepicker__year-select").selectOption(year);
            $(".react-datepicker__day--015").click();
            $("#subjectsInput").setValue(subjects).pressEnter();
            // hobbies 2 of 3
            $(byText(sports)).click();
            $(byText(reading)).click();
            $("#uploadPicture").uploadFile(new File("./src/test/resources/img/img1.jpg"));
            $("#currentAddress").setValue(address);
            $("#react-select-3-input").setValue(state).pressEnter();
            $("#react-select-4-input").setValue(city).pressEnter();
            $("#submit").click();
        });

        step("Verify the form", () -> {
            $$(".table-responsive tr").filterBy(text("Student Name")).shouldHave(texts(firstName + " " + lastName));
            $$(".table-responsive tr").filterBy(text("Student Email")).shouldHave(texts(email));
            $$(".table-responsive tr").filterBy(text("Gender")).shouldHave(texts(gender));
            $$(".table-responsive tr").filterBy(text("Mobile")).shouldHave(texts(mobileNumber));
            $$(".table-responsive tr").filterBy(text("Date of Birth")).shouldHave(texts("15 " +  month + "," + year));
            $$(".table-responsive tr").filterBy(text("Subjects")).shouldHave(texts(subjects));
            $$(".table-responsive tr").filterBy(text("Hobbies")).shouldHave(texts(sports + ", " + reading));
            $$(".table-responsive tr").filterBy(text("Picture")).shouldHave(texts("img1.jpg"));
            $$(".table-responsive tr").filterBy(text("Address")).shouldHave(texts(address));
            $$(".table-responsive tr").filterBy(text("State and City")).shouldHave(texts(state + " " + city));
        });
    }
}
