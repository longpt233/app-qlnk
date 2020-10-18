## Simple app javafx 

## fork 

## conn mysql

## use MVC: Conn- DAO- (Service)- Control- view (fxml)

## use Gradle: 

plugins {
    id 'java'
    id 'application'    //??
    id 'org.openjfx.javafxplugin' version '0.0.8'  // javafx 
}

javafx {
    version = "11.0.2"
    modules = [ 'javafx.controls' , 'javafx.fxml']
    // .control thi chay dc cn javafx.fxml thi no moi nhan dc @FXML
}

group 'org.example'
version '1.0-SNAPSHOT'

repositories {
    mavenCentral()
    // cai nay se dung cho cai dependence duoi
}

dependencies {
    testCompile group: 'junit', name: 'junit', version: '4.12'
    // https://mvnrepository.com/artifact/mysql/mysql-connector-java
    compile group: 'mysql', name: 'mysql-connector-java', version: '8.0.16'

}

mainClassName ='control.Main'    // run + mainApp
