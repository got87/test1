package ru.yandex.gotulx;

// Вспомогательный класс-примитив для описания набора тестов "Логин", "Пароль"

public class TstSampl {
        public String log;       // Значение для поля "Логин"
        public String pas;       // Значение для поля "Пароль"
        public boolean expect;   // Ожидаемая реакция системы
        public TstSampl(String log, String pas, boolean expect) {

            this.log = log;
            this.pas = pas;
            this.expect = expect;
        }
}
