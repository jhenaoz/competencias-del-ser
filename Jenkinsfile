#!groovy

node() {
    stage('Checkout') {
        checkout scm
    }

    stage('Compile') {
        withMaven(jdk: 'JDK 1.8', maven: 'Maven 3.3.9') {
            withEnv(['ENV=CI', 'SPRING_PROFILES_ACTIVE=stg']) {
                if (env.BRANCH_NAME == 'master') {
                    sh 'mvn clean'
                }
                sh 'mvn test-compile'
            }
        }
    }

    stage('Test') {
        withMaven(jdk: 'JDK 1.8', maven: 'Maven 3.3.9') {
            withEnv(['ENV=CI', 'SPRING_PROFILES_ACTIVE=stg']) {
                sh 'mvn surefire:test'
                sh 'mvn frontend:npm@npm-test'
            }
        }
    }

    stage('Integration Test') {
        withMaven(jdk: 'JDK 1.8', maven: 'Maven 3.3.9') {
            withEnv(['ENV=CI', 'SPRING_PROFILES_ACTIVE=stg']) {
                sh 'mvn failsafe:integration-test failsafe:verify'
            }
        }
    }
}