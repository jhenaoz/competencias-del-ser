#!groovy

node() {
    stage('Checkout') {
        checkout scm
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

   stage('SonarQube analysis') {
        withMaven(jdk: 'JDK 1.8', maven: 'Maven 3.3.9') {
            withSonarQubeEnv('SonarQube Test') {
                sh 'mvn sonar:sonar'
            }
        }
    }

    stage('Report') {
        step([$class: 'hudson.plugins.checkstyle.CheckStylePublisher', pattern: '**/target/checkstyle-result.xml', unstableTotalAll:'0'])
        step([$class: 'PmdPublisher', pattern: '**/target/pmd.xml', unstableTotalAll:'0'])
        step([$class: 'FindBugsPublisher', pattern: '**/findbugs.xml', unstableTotalAll:'0'])
        publishHTML([allowMissing: false, alwaysLinkToLastBuild: false, keepAll: false, reportDir: 'target/site/', reportFiles: 'checkstyle.html, pmd.html', reportName: 'Static Code Analysis'])
    }
}