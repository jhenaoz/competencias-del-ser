#!groovy

node() {
    stage('Checkout') {
        checkout scm
    }

    stage('Dependency Start') {
        withMaven(maven: 'M3') {
            sh "mvn generate-resources"
        }
    }

    stage('Compile') {
        withMaven(maven: 'M3') {
            sh "mvn compile"
        }
    }

    stage('Test') {
        withMaven(maven: 'M3') {
            sh "mvn test"
        }
    }

    stage('Report') {

    }
}