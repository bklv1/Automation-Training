pipeline {

    agent any

    stages{
        stage("build") {
            steps {
                echo 'mvn clean install'
            }
        }

        stage("test") {
            steps {
                echo 'mvn test'
            }
        }

        stage("deploy") {
            steps {
                echo 'later make it merge in master...'
            }
        }
    }
}
