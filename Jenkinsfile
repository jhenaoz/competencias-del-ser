#!groovy

node() {
    stage('Checkout') {
        checkout scm
    }

    stage('Test') {
        withMaven(jdk: 'JDK 1.8', maven: 'Maven 3.3.9') {
            withEnv(['ENV=CI', 'SPRING_PROFILES_ACTIVE=stg']) {
                sh "mvn test"
            }
        }
    }
}