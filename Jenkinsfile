pipeline {

    agent any

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
