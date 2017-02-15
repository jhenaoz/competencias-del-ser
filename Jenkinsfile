#!groovy

node() {
    stage('Checkout') {
        checkout scm
    }

    stage('Dependency Start') {
        withMaven(jdk: 'JDK 1.8', maven: 'Maven 3.3.9') {
            sh "mvn generate-resources"
        }
    }

    stage('Compile') {
        withMaven(jdk: 'JDK 1.8', maven: 'Maven 3.3.9') {
            sh "mvn compile"
        }
    }

    stage('Test') {
        withMaven(jdk: 'JDK 1.8', maven: 'Maven 3.3.9') {
            withEnv(["ENV=CI"]) {
                sh "mvn test"
            }
        }
    }

    stage('Report') {

    }
}