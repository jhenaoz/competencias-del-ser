#!groovy

node() {
    stage('Checkout') {
        checkout scm
        withMaven(jdk: 'JDK 1.8', maven: 'Maven 3.3.9') {
            sh 'mvn clean'
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
                sh 'mvn test-compile'
            }
        }
    }

    stage('Findbugs & TSLint') {
        withMaven(jdk: 'JDK 1.8', maven: 'Maven 3.3.9') {
            withEnv(['ENV=CI', 'SPRING_PROFILES_ACTIVE=stg']) {
                sh 'mvn findbugs:check'
                sh 'mvn frontend:npm@tslint'
            }
        }
    }

    stage('Test') {
        withMaven(jdk: 'JDK 1.8', maven: 'Maven 3.3.9') {
            withEnv(['ENV=CI', 'SPRING_PROFILES_ACTIVE=stg']) {
                sh 'mvn jacoco:prepare-agent surefire:test jacoco:report'
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
        step([$class: 'CheckStylePublisher', defaultEncoding: '', failedTotalHigh: '0', healthy: '', pattern: '**/target/checkstyle-result.xml,**/src/main/resources/static/checkstyle-result.xml', unHealthy: '', unstableTotalNormal: '550'])
        step([$class: 'PmdPublisher', defaultEncoding: '', healthy: '', pattern: '**/target/pmd.xml', unHealthy: ''])
        step([$class: 'AnalysisPublisher', defaultEncoding: '', healthy: '', unHealthy: ''])
        step([$class: 'JacocoPublisher', execPattern: '**/target/jacoco.exec'])
        publishHTML([allowMissing: false, alwaysLinkToLastBuild: false, keepAll: false, reportDir: 'target/site/jacoco/', reportFiles: 'index.html', reportName: 'JaCoCo Report'])
        publishHTML([allowMissing: false, alwaysLinkToLastBuild: false, keepAll: false, reportDir: 'target/site/', reportFiles: 'checkstyle.html,pmd.html', reportName: 'Static Code Analysis'])
    }

    stage('Package & Install') {
        withMaven(jdk: 'JDK 1.8', maven: 'Maven 3.3.9') {
            withEnv(['ENV=CI', 'SPRING_PROFILES_ACTIVE=stg']) {
                sh 'mvn war:war spring-boot:repackage install:install'
            }
        }
    }
}