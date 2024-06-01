module com.example.hospitalmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.beans;
    requires spring.core;
    requires java.sql;
    requires org.hibernate.validator;
    requires org.hibernate.orm.core;
    requires spring.data.jpa;
    requires jakarta.persistence;
    requires jakarta.validation;
    requires lombok;
    requires spring.web;
    requires spring.tx;

    // Открытие пакетов для необходимых модулей
    opens com.example.hospitalmanagementsystem to spring.core, spring.beans, spring.context, javafx.fxml;
    opens com.example.hospitalmanagementsystem.entity to org.hibernate.validator, org.hibernate.orm.core, spring.core, spring.beans, spring.context;
    opens com.example.hospitalmanagementsystem.bootstrap to spring.core, spring.beans, spring.context;
    opens com.example.hospitalmanagementsystem.exception to spring.core, spring.beans, spring.context;
    opens com.example.hospitalmanagementsystem.repository to spring.core, spring.beans, spring.context;
    opens com.example.hospitalmanagementsystem.service to spring.core, spring.beans, spring.context;
    opens com.example.hospitalmanagementsystem.config to spring.core, spring.beans, spring.context;


    exports com.example.hospitalmanagementsystem;
}
