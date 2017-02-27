#!groovy

node() {
    stage('Checkout') {
        checkout scm
    }

    stage('Compile') {
        withMaven(jdk: 'JDK 1.8', maven: 'Maven 3.3.9') {
            withEnv(['ENV=CI', 'SPRING_PROFILES_ACTIVE=stg']) {
                sh 'mvn clean test-compile'
            }
        }
    }

    stage('SonarQube analysis') {
        withSonarQubeEnv('SonarQube Test') {
            sh 'mvn sonar:sonar'
        }
    }

    stage('Test') {
        withMaven(jdk: 'JDK 1.8', maven: 'Maven 3.3.9') {
            withEnv(['ENV=CI', 'SPRING_PROFILES_ACTIVE=stg']) {
                sh 'mvn surefire:test'
                sh 'vn frontend:npm@npm-test'
            }
        }
    }
}