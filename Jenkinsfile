#!groovy

node() {
    stage('Checkout') {
        checkout scm
    }

    stage('SonarQube analysis') {
        withMaven(jdk: 'JDK 1.8', maven: 'Maven 3.3.9') {
            withSonarQubeEnv('SonarQube Test') {
                sh 'mvn sonar:sonar'
            }
        }
    }

    stage('Checkstyle') {
        withMaven(jdk: 'JDK 1.8', maven: 'Maven 3.3.9') {
            sh 'mvn checkstyle:checkstyle'
        }
    }

    stage('PMD') {
        withMaven(jdk: 'JDK 1.8', maven: 'Maven 3.3.9') {
            sh 'mvn pmd:pmd'
        }
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

    stage('Findbugs & TSLint') {
        withMaven(jdk: 'JDK 1.8', maven: 'Maven 3.3.9') {
            sh 'mvn findbugs:check'
            sh 'mvn frontend:npm@tslint'
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

    stage('Report') {

    }
}