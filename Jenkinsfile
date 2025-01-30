pipeline {

    agent any
    tools {
        maven 'MAVEN_HOME'
        jdk 'JAVA_HOME'
    }
    stages{
        stage("build") {
            steps {
            withMaven {
               sh 'mvn clean install'
               }
            }
        }

        stage("test") {
            steps {
               withMaven {
               sh 'mvn test'
               }
            }
        }

        stage("deploy") {
            steps {
                echo 'later make it merge in master...'
            }
        }
    }
}
