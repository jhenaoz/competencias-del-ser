#!groovy

node() {
    stage('Checkout') {
        checkout scm
    }

    stage('Dependency Start') {
        withMaven(maven: 'Maven 3.3.9') {
            sh "mvn generate-resources"
        }
    }

    stage('Compile') {
        withMaven(maven: 'Maven 3.3.9') {
            sh "mvn compile"
        }
    }

    stage('Test') {
        withMaven(maven: 'Maven 3.3.9') {
            sh "mvn test"
        }
    }

    stage('Report') {

    }
}